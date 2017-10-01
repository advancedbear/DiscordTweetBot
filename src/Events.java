import java.text.SimpleDateFormat;
import java.util.Date;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;

public class Events {
	Date date;
	SimpleDateFormat date_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
		date = new Date();
        if(event.getMessage().getContent().startsWith("invite")){
        	if(event.getAuthor().getPresence().getPlayingText().isPresent()){
        		Tweet.tweet(event.getAuthor().getName()+ "さんが"+ event.getAuthor().getPresence().getPlayingText().get() + "への参加者を募集しています。 ("+ date_time.format(date) +")");
        		Discord.sendMessage(event.getChannel(), "Invite message was tweeted.");
        	} else {
        		Tweet.tweet(event.getAuthor().getName()+ "さんが"+ event.getGuild().getName() + "サーバーへの参加者を募集しています。 ("+ date_time.format(date) +")");
        		Discord.sendMessage(event.getChannel(), "Invite message was tweeted.");
        	}
        }
    }

	@EventSubscriber
	public void joinVoiceChannel(UserVoiceChannelJoinEvent event){
		date = new Date();
		System.out.println(event.getUser().getName() + " join to the server.");
		Tweet.tweet(event.getUser().getName() + " join to the voice channel. ("+ date_time.format(date) +")");
	}

	@EventSubscriber
	public void leftVoiceChannel(UserVoiceChannelLeaveEvent event){
		date = new Date();
		System.out.println(event.getUser().getName() + " left from the server.");
		Tweet.tweet(event.getUser().getName() + " left from the voice channel. ("+ date_time.format(date) +")");
	}

	@EventSubscriber
	public void onUserGameUpdate (PresenceUpdateEvent event){
		date = new Date();
		if(event.getNewPresence().getPlayingText().isPresent()){
			System.out.println(event.getUser().getName()+" is launching " +event.getNewPresence().getPlayingText().get());
			Tweet.tweet(event.getUser().getName()+" is now playing " +event.getNewPresence().getPlayingText().get()+ ". ("+ date_time.format(date) +")");
		}
	}

}
