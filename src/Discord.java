import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;

public class Discord implements EventListener{
	JDA jda;
	Discord(String token) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException{
		jda = new JDABuilder(AccountType.BOT)
				.setToken(token)
	            .addEventListener(this)
	            .buildBlocking();
	}
	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		if (e instanceof ReadyEvent)
            System.out.println("API is ready!");
		if (e instanceof GuildVoiceJoinEvent){
			System.out.println("Joined!");
			//getChannelJoined
		}
	}
}
