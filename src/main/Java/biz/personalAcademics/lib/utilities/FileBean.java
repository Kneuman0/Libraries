package biz.personalAcademics.lib.utilities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public interface FileBean {
	
	Media getMedia();
	
	MediaPlayer getPlayer();
	
	String getSongName();
	
	void setSongName(String songName);
	
	void setAlbum(String songName);
	
	void setArtist(String songName);
	
	void setDuration(double duration);
	
	void setMedia(Media media);
	
	void setPlayer(MediaPlayer player);
	 
	 

}
