import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;

public class Events {
	@EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMessage().getContent().startsWith("test"))
            Discord.sendMessage(event.getChannel(), "I am sending a message from an EventSubscriber listener");
    }

	public void joinVoiceChannel(UserVoiceChannelJoinEvent event){
		System.out.println(event.getUser().getName() + " join to the server.");
	}

	public void leftVoiceChannel(UserVoiceChannelLeaveEvent event){
		System.out.println(event.getUser().getName() + " left from the server.");
	}
}
