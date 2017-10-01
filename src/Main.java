import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sx.blah.discord.api.IDiscordClient;
import twitter4j.TwitterException;

public class Main extends JFrame implements ActionListener{

	private JPanel contentPane;

	JButton btnLoginTwitter = new JButton("Login to Twitter");
	JButton btnOpenDiscord = new JButton("Login to Discord");

	JCheckBox chkNotifyVC = new JCheckBox("Notify Voice Channel Joining / Leaving.");
	JCheckBox chkNotifyGame = new JCheckBox("Notify Member Starting Game.");
	JCheckBox chkNotifyInvite = new JCheckBox("Notify Invitation for Game.");

	JButton btnLaunchBot = new JButton("Launch Bot");

	static Tweet twitter = new Tweet();
	static Discord discord;
	static IDiscordClient client;
	static Config config = new Config();

	File f = new File("config.cfg");

	private boolean botLaunch = false;

	public static void main(String[] args) {
		System.setProperty("sun.java2d.noddraw", "true");
	    System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Discord Tweet Bot");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 290);
		ImageIcon icon = new ImageIcon("./icon.png");
	    setIconImage(icon.getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTwitterLabel = new JLabel("1. Login to your Twitter account.");
		lblTwitterLabel.setBounds(12, 10, 290, 13);
		contentPane.add(lblTwitterLabel);

		btnLoginTwitter.setBounds(12, 33, 290, 21);
		contentPane.add(btnLoginTwitter);
		btnLoginTwitter.addActionListener(this);
		btnLoginTwitter.setActionCommand("Twitter");

		JLabel lblDiscord = new JLabel("2. Login to your Discord account.");
		lblDiscord.setBounds(12, 64, 290, 13);
		contentPane.add(lblDiscord);

		btnOpenDiscord.addActionListener(this);
		btnOpenDiscord.setActionCommand("Discord");
		btnOpenDiscord.setBounds(12, 87, 290, 21);
		contentPane.add(btnOpenDiscord);

		JLabel lblSelectFew = new JLabel("3. Choose options for notification.");
		lblSelectFew.setBounds(12, 118, 290, 13);
		contentPane.add(lblSelectFew);

		chkNotifyVC.setBounds(8, 137, 294, 21);
		contentPane.add(chkNotifyVC);
		chkNotifyVC.addActionListener(this);
		chkNotifyVC.setActionCommand("chkNotifyVC");

		chkNotifyGame.setBounds(8, 160, 294, 21);
		contentPane.add(chkNotifyGame);
		chkNotifyGame.addActionListener(this);
		chkNotifyGame.setActionCommand("chkNotifyGame");

		chkNotifyInvite.setBounds(8, 183, 294, 21);
		contentPane.add(chkNotifyInvite);
		chkNotifyInvite.addActionListener(this);
		chkNotifyInvite.setActionCommand("chkNotifyInvite");

		btnLaunchBot.setBounds(12, 210, 290, 42);
		contentPane.add(btnLaunchBot);
		btnLaunchBot.addActionListener(this);
		btnLaunchBot.setActionCommand("Launch");

		try {
			loadConfigFile();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(this, "Discordサーバーに問題があります。\n時間を置いて再度実行してください。");
			System.exit(-1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Twitter")){
			twitter.TwitterAuth();
			String value = JOptionPane.showInputDialog(this, "Input PIN number here.");
			if (value != null) {
				twitter.authorization(value);
				try {
					btnLoginTwitter.setText("Logged in as @" + twitter.twitter.getScreenName());
					btnLoginTwitter.setEnabled(false);
					config.setAccessToken(twitter.accessToken);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "PIN number was empty. Please restart application. ");
				System.exit(-1);
			}
		}

		if(e.getActionCommand().equals("Discord")){
			Desktop desktop = Desktop.getDesktop();
			URI uri;
			try {
				uri = new URI("https://discordapp.com/developers/applications/me");
				desktop.browse(uri);
			} catch (Exception error) {
				error.printStackTrace();
			}
			String clientID = JOptionPane.showInputDialog(this, "Input ClientID here.");
			String token = JOptionPane.showInputDialog(this, "Input Token here.");
			if(token != null && clientID != null){
				discord = new Discord();
				client = Discord.getBuiltDiscordClient(token);
				client.getDispatcher().registerListener(new Events());
				System.out.println("Logged in as " + client.getApplicationName());
				btnOpenDiscord.setText("Logged in as " + client.getApplicationName());
				btnOpenDiscord.setEnabled(false);
				config.setDiscordToken(token);
				config.setDiscordClientID(clientID);
			} else {
				JOptionPane.showMessageDialog(this, "Please input ClientID and Token.");
			}
		}

		if(e.getActionCommand().equals("Launch")){
			if(!botLaunch) {
				if(config.isFirst()){
					Desktop desktop = Desktop.getDesktop();
					URI uri;
					JOptionPane.showMessageDialog(this, "Select the server you want bot to join.\nIf bot was already joined the server, close the browser.");
					try {
						uri = new URI("https://discordapp.com/oauth2/authorize?&client_id="+config.getDiscordClientID()+"&scope=bot&permissions=0");
						desktop.browse(uri);
					} catch (Exception error) {
						error.printStackTrace();
					}
				}
				client.login();
				botLaunch = true;
				btnLaunchBot.setText("Shutdown Bot");
				config.setFirstLaunch(false);
			} else {
				client.logout();
				botLaunch = false;
				btnLaunchBot.setText("Launch Bot");
			}
			storeConfigFile();
		}

		if(e.getActionCommand().equals("chkNotifyVC")){
			config.setNotifyVC(chkNotifyVC.isSelected());
			storeConfigFile();
		}

		if(e.getActionCommand().equals("chkNotifyGame")){
			config.setNotifyGame(chkNotifyGame.isSelected());
			storeConfigFile();
		}

		if(e.getActionCommand().equals("chkNotifyInvite")){
			config.setNotifyInvite(chkNotifyInvite.isSelected());
			storeConfigFile();
		}

	}

	public void loadConfigFile() throws UnknownHostException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			config = (Config) ois.readObject();
			// 1. Twitter Login.
			twitter.authorization(config.getAccessToken());
			btnLoginTwitter.setText("Logged in as @" + twitter.twitter.getScreenName());
			btnLoginTwitter.setEnabled(false);
			// 2. Discord Login.
			discord = new Discord();
			client = Discord.getBuiltDiscordClient(config.getDiscordToken());
			client.getDispatcher().registerListener(new Events());
			btnOpenDiscord.setText("Logged in as " + client.getApplicationName());
			btnOpenDiscord.setEnabled(false);
			// 3. Set options.
			chkNotifyVC.setSelected(config.getNotifyVC());
			chkNotifyGame.setSelected(config.getNotifyGame());
			chkNotifyInvite.setSelected(config.getNotifyInvite());
		} catch (IOException | ClassNotFoundException | IllegalStateException | TwitterException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Thank you for using. Please read about how to use at first.");
			Desktop desktop = Desktop.getDesktop();
			URI uri;
			try {
				uri = new URI("https://github.com/advancedbear/DiscordTweetBot/blob/master/README.md");
				desktop.browse(uri);
			} catch (Exception error) {
				error.printStackTrace();
			}
		}
	}

	public void storeConfigFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
			oos.writeObject(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
