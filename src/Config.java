import java.io.Serializable;

import twitter4j.auth.AccessToken;

public class Config implements Serializable{
	private static final long serialVersionUID = -9101085459063432816L;

	private AccessToken accessToken;
	private String discordToken;
	private String discordClientID;
	private boolean notifyVC;
	private boolean notifyGame;
	private boolean notifyInvite;

	private boolean firstLaunch = true;
	
	public void setAccessToken(AccessToken a){
		accessToken = a;
	}

	public void setDiscordToken(String s){
		discordToken = s;
	}

	public void setDiscordClientID(String s){
		discordClientID = s;
	}

	public void setNotifyVC(boolean b){
		notifyVC = b;
	}

	public void setNotifyGame(boolean b){
		notifyGame = b;
	}

	public void setNotifyInvite(boolean b){
		notifyInvite = b;
	}

	public void setFirstLaunch(boolean b){
		firstLaunch = b;
	}

	public AccessToken getAccessToken(){
		return accessToken;
	}

	public String getDiscordToken(){
		return discordToken;
	}

	public String getDiscordClientID(){
		return discordClientID;
	}

	public boolean getNotifyVC(){
		return notifyVC;
	}

	public boolean getNotifyGame(){
		return notifyGame;
	}

	public boolean getNotifyInvite(){
		return notifyInvite;
	}

	public boolean isFirst(){
		return firstLaunch;
	}
}
