import javax.sound.sampled.*;
import javafx.embed.swing.JFXPanel;

import com.sun.org.apache.xerces.internal.util.Status;



import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SongList {
	MediaPlayer mp;
	Map<String ,String> songMap = new HashMap<String ,String>();
	Duration PauseTime;
	Duration lengthOfSong = new Duration(0.0);
	public static Boolean play = false;
	
	
	public void addMap(String songName, String songPath) {
		songMap.put(songName , songPath);
	}
	
	public void mp(Media media) {
		mp = new MediaPlayer(media);
		mp.setOnReady(() -> {
        });
	}
	//https://stackoverflow.com/questions/22344020/could-not-get-audio-input-stream-from-input-file
	public void playSong(String songName) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		String songPath = songMap.get(songName);
		System.out.println(songPath);
		System.out.println("value:" + songPath);
		File fin = new File(songPath);
		Media hit = new Media(fin.toURI().toString());
        mp(hit);
        System.out.println(mp.getStatus());
        mp.play();

       }

	
	public void showList() {
		for (Map.Entry<String,String> entry : songMap.entrySet()) 
            System.out.println("Key = " + entry.getKey() +
                             ", Value = " + entry.getValue());
	}
	public void pauseSound() {
        mp.pause();
      }
	
	public String getValue(String key) {
		return songMap.get(key);
	}
	
	public void resume() {
		mp.play();
	    
	}
	
	public void playFrom(Duration dur) {
		if (dur.toMillis() <= lengthOfSong.toMillis()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Whoa!");
			alert.setContentText("WHAT??? ");
			alert.show();
		}
		else if ((dur.toMillis() > lengthOfSong.toMillis()) ) {
			
			return;
		}
		mp.seek(dur);
		mp.play();
		
	}
	
	public void dispose() {
		mp.dispose();
	}
	
	public void stop() {
		mp.stop();
	}
	
	public void clearAll(Map map) {
		map.clear();
	}
	
	
	public Double getLength(String key) throws InterruptedException {
		
		mp.setOnReady(new Runnable() {
		
			@Override
			public void run() {
				 lengthOfSong = mp.getTotalDuration();		

			}
			
		});
		return lengthOfSong.toMillis();
	}

		
	


}
