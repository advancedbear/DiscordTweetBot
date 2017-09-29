import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.dv8tion.jda.core.exceptions.RateLimitedException;
import twitter4j.TwitterException;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

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
				try {
					discord = new Discord(token);
				} catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
}
