import java.text.SimpleDateFormat;
import java.util.Date;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.shard.ReconnectFailureEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;

public class Events {

	Date date;
	SimpleDateFormat date_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	@EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
		if(Main.config.getNotifyInvite()){
			date = new Date();
	        if(event.getMessage().getContent().startsWith("invite")){
	        	if(event.getAuthor().getPresence().getPlayingText().isPresent()){
	        		Tweet.tweet(event.getAuthor().getDisplayName(event.getGuild())+ "さんが"+ event.getAuthor().getPresence().getPlayingText().get() + "への参加者を募集しています。 ("+ date_time.format(date) +")");
	        		Discord.sendMessage(event.getChannel(), "Invite message was tweeted.");
	        	} else {
	        		Tweet.tweet(event.getAuthor().getDisplayName(event.getGuild())+ "さんが"+ event.getGuild().getName() + "サーバーへの参加者を募集しています。 ("+ date_time.format(date) +")");
	        		Discord.sendMessage(event.getChannel(), "Invite message was tweeted.");
	        	}
	        }
		}
    }

	@EventSubscriber
	public void joinVoiceChannel(UserVoiceChannelJoinEvent event){
		if(Main.config.getNotifyVC()){
			date = new Date();
			Tweet.tweet(event.getUser().getName() + " join to the voice channel. ("+ date_time.format(date) +")");
		}
	}

	@EventSubscriber
	public void leftVoiceChannel(UserVoiceChannelLeaveEvent event){
		if(Main.config.getNotifyVC()){
			date = new Date();
		Tweet.tweet(event.getUser().getName() + " left from the voice channel. ("+ date_time.format(date) +")");
		}
	}

	@EventSubscriber
	public void onUserGameUpdate (PresenceUpdateEvent event){
		if(Main.config.getNotifyGame()){
			date = new Date();
			if(event.getNewPresence().getPlayingText().isPresent()){
				Tweet.tweet(event.getUser().getName()+" is now playing " +event.getNewPresence().getPlayingText().get()+ ". ("+ date_time.format(date) +")");
			}
		}
	}

	@EventSubscriber
	public void onDisconnectedEvent (ReconnectFailureEvent event){
		if(Main.client.isLoggedIn()) Main.client.login();
	}

}
