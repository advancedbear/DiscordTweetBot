import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Tweet {
	static Twitter twitter;
	AccessToken accessToken = null;
	RequestToken requestToken;

	public Tweet() {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthAccessToken(accessToken);
	}

	public void TwitterAuth() {
		try {
			Desktop desktop = Desktop.getDesktop();
			requestToken = twitter.getOAuthRequestToken();
			URI uri = new URI(requestToken.getAuthorizationURL());
			desktop.browse(uri);
		} catch (TwitterException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public void authorization(String pin) {
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			twitter.setOAuthAccessToken(accessToken);
		} catch (TwitterException e) {
			System.out.println("Authorization Error!");
		}
	}

	public void authorization(AccessToken token) {
		twitter.setOAuthAccessToken(token);
	}

	public static String tweet(String t){
			try {
				long id = twitter.updateStatus(new StatusUpdate(t)).getId();
				return "https://twitter.com/user/status/"+id;
			} catch (TwitterException e) {
				e.printStackTrace();
				if (e.getStatusCode() == 403) {
					System.out.println("Over 140 characters.");
				} else if (e.getStatusCode() == 400) {
					System.out.println("Something is wrong.");
				}
				return "Tweet failed";
			}
	}
}