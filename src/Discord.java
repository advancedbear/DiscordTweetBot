import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

public class Discord {
	@EventSubscriber

	static IDiscordClient getBuiltDiscordClient(String token) {
		return new ClientBuilder().withToken(token).build();
	}

	public static void sendMessage(IChannel channel, String message) {
		RequestBuffer.request(() -> {
			try {
				channel.sendMessage(message);
			} catch (DiscordException e) {
				System.err.println("Message could not be sent with error: ");
				e.printStackTrace();
			}
		});
	}
}
