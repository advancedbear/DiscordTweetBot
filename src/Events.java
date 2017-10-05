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
			String tMessage = event.getAuthor().getDisplayName(event.getGuild())+ "さんが";
	        if(event.getMessage().getContent().startsWith("invite")){
	        	if(event.getAuthor().getPresence().getPlayingText().isPresent()){
	        		tMessage += event.getAuthor().getPresence().getPlayingText().get() + "への参加者を募集しています。 ";
	        	} else {
	        		tMessage += event.getGuild().getName() + "サーバーへの参加者を募集しています。 ";
	        	}
	        	tMessage += "\n("+ date_time.format(date) +")";
	        	String url = Tweet.tweet(tMessage);
        		Discord.sendMessage(event.getChannel(), url);
	        }
		}
		if(event.getMessage().getContent().equals("help")){
        	String help = "---- Discord Twitter Bot----\n"
        			+ "  version 0.1.2\n"
        			+ "\n"
        			+ "List of Commands\n"
        			+ "  help\n"
        			+ "  　このヘルプを表示します。\n"
        			+ "  status\n"
        			+ "  　通知機能のオン/オフ状態を表示します\n"
        			+ "  invite\n"
        			+ "  　参加者の募集メッセージをツイートします。\n"
        			+ "  　ゲームをプレイ中の場合、ツイート文にゲーム名が含まれます。\n"
        			+ "  start [invite|channel|game]\n"
        			+ "  　参加者募集、ボイスチャンネル入退室、ゲーム開始、それぞれの\n"
        			+ "  　通知機能をオンにします。\n"
        			+ "  stop [invite|channel|game]\n"
        			+ "  　参加者募集、ボイスチャンネル入退室、ゲーム開始、それぞれの\n"
        			+ "  　通知機能をオフにします。\n"
        			+ "  shutdown-bot\n"
        			+ "  　ボットを終了します。BOT管理者のみ実行可能です。\n";
        	Discord.sendMessage(event.getChannel(), help);
        }
		if(event.getMessage().getContent().startsWith("start")){
			try {
				String subCom = event.getMessage().getContent().substring(6);
				if(subCom.equals("invite")) Main.config.setNotifyInvite(true);
				else if(subCom.equals("channel")) Main.config.setNotifyVC(true);
				else if(subCom.equals("game")) Main.config.setNotifyGame(true);
				else return;
				Discord.sendMessage(event.getChannel(), "Notifycation of "+subCom+" is changed into \"ON\".");
			} catch (StringIndexOutOfBoundsException e){
				Discord.sendMessage(event.getChannel(), "!! Invalid Command !!");
			}
		}
		if(event.getMessage().getContent().startsWith("stop")){
			try {
				String subCom = event.getMessage().getContent().substring(5);
				if(subCom.equals("invite")) Main.config.setNotifyInvite(false);
				else if(subCom.equals("channel")) Main.config.setNotifyVC(false);
				else if(subCom.equals("game")) Main.config.setNotifyGame(false);
				else return;
				Discord.sendMessage(event.getChannel(), "Notifycation of "+subCom+" is changed into \"OFF\".");
			} catch (StringIndexOutOfBoundsException e){
				Discord.sendMessage(event.getChannel(), "!! Invalid Command !!");
			}
		}
		
		if(event.getMessage().getContent().startsWith("status")){
			String status = "!!--------Notification Status--------!!\n"
					+ "> Invitation Notification -> " +Main.config.getNotifyInvite() +"\n"
					+ "> VC join/exit Notification -> " +Main.config.getNotifyVC() +"\n"
					+ "> Game Start Notification -> " +Main.config.getNotifyGame() +"\n"
					+ "!!-------------------------------------!!";
			Discord.sendMessage(event.getChannel(), status);
		}
		
		if(event.getMessage().getContent().startsWith("shutdown-bot")){
			if(event.getAuthor().getLongID() == event.getClient().getApplicationOwner().getLongID()){
				String status = "The BOT is now shutting down.\n"
						+ "Check that "+event.getClient().getOurUser().getName()+" is now OFFLINE.";
				Discord.sendMessage(event.getChannel(), status);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.client.logout();
				System.exit(1);
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
