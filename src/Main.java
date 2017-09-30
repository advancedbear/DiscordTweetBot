import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

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

	Tweet twitter = new Tweet();
	Discord discord;
	IDiscordClient client;
	/**
	 * Launch the application.
	 */
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

		chkNotifyVC.setBounds(8, 137, 294, 21);
		contentPane.add(chkNotifyVC);

		chkNotifyGame.setBounds(8, 160, 294, 21);
		contentPane.add(chkNotifyGame);

		JLabel lblSelectFew = new JLabel("3. Choose options for notification.");
		lblSelectFew.setBounds(12, 118, 290, 13);
		contentPane.add(lblSelectFew);

		chkNotifyInvite.setBounds(8, 183, 294, 21);
		contentPane.add(chkNotifyInvite);

		btnLaunchBot.setBounds(12, 210, 290, 42);
		contentPane.add(btnLaunchBot);
		btnLaunchBot.addActionListener(this);
		btnLaunchBot.setActionCommand("Launch");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Twitter")){
			twitter.TwitterAuth();
			String value = JOptionPane.showInputDialog(this, "Input PIN number here.");
			if (value == null) {
			} else {
				twitter.authorization(value);
				try {
					btnLoginTwitter.setText("Logged in as @" + twitter.twitter.getScreenName());
					btnLoginTwitter.setEnabled(false);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
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
			String token = JOptionPane.showInputDialog(this, "Input Token here.");
			if(token != null){
				discord = new Discord();
				client = Discord.getBuiltDiscordClient(token);
				client.getDispatcher().registerListener(new Events());
				System.out.println("Logged in as " + client.getApplicationName());
				btnOpenDiscord.setText("Logged in as " + client.getApplicationName());
				btnOpenDiscord.setEnabled(false);
			}
		}

		if(e.getActionCommand().equals("Launch")){
			Desktop desktop = Desktop.getDesktop();
			URI uri;
			JOptionPane.showMessageDialog(this, "Select the server you want bot to join.\nIf you already joined the server, close the browser.");
			try {
				uri = new URI("https://discordapp.com/oauth2/authorize?&client_id=361147580698066955&scope=bot&permissions=0");
				desktop.browse(uri);
			} catch (Exception error) {
				error.printStackTrace();
			}

			client.login();
		}

	}
}
