package biz.personalAcademics.lib.utilities;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * class that handles importing exporting of objects to XML files. 
 * Originally use with a media player
 * 
 * @author Karottop
 *
 */
public class XmlUtilities {

	
	public static <Type> void exportObjectToXML(Type prefs, String exportLocation){
		FileWriter file = null;
		PrintWriter fileOut = null;
		try {

			file = new FileWriter(exportLocation);
			fileOut = new PrintWriter(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ByteArrayOutputStream songList = new ByteArrayOutputStream();
		XMLEncoder write = new XMLEncoder(songList);
		write.writeObject(prefs);
		write.close();
		fileOut.println(songList.toString());

		try {
			fileOut.close();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Exports all Objects passed in to an XML at the specified
	 * location including name of xml file.
	 * 
	 * @param exportLocation
	 */
	public static <T> void exportListOfObjectsToXML(String exportLocation,
			List<T> songs) {

		FileWriter file = null;
		PrintWriter fileOut = null;
		try {

			file = new FileWriter(exportLocation);
			fileOut = new PrintWriter(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ObservableLists apparently cannot be encoded so it is converted to an
		// ArrayList
		ArrayList<T> toXML = new ArrayList<>();
		toXML.addAll(songs);
		ByteArrayOutputStream songList = new ByteArrayOutputStream();
		XMLEncoder write = new XMLEncoder(songList);
		write.writeObject(songs);
		write.close();
		fileOut.println(songList.toString());

		try {
			fileOut.close();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	/**
	 * This method will import an XML file containing playlistBeans.
	 * 
	 * Should only be used when the XML file location is different
	 * than the location passed into the object. The File  passed in
	 * should be either contain the XML with an absolute path that is
	 * retrievable
	 * @param xml
	 * @return
	 * @throws FileNotFoundException
	 */
	public static <T> List<T> importListOfObjectFromXML(File xml)
			throws FileNotFoundException {

		FileInputStream xmlFile = new FileInputStream(xml.getAbsolutePath());
		BufferedInputStream fileIn = new BufferedInputStream(xmlFile);
		XMLDecoder decoder = new XMLDecoder(fileIn);
		
		@SuppressWarnings("unchecked")
		ArrayList<T> fromXML = (ArrayList<T>) decoder
				.readObject();
		decoder.close();
		try {
			fileIn.close();
			xmlFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fromXML;
	}
	
	public static <Type> Type readInObjectFromXML(String location) throws FileNotFoundException{
		FileInputStream xmlFile = new FileInputStream(location);
		return readInObjectFromXML(xmlFile);
	}
	
	@SuppressWarnings("unchecked")
	public static <Type> Type readInObjectFromXML(InputStream stream){
		
		Type prefs = null;
		
//		FileInputStream xmlFile = null;
//		try {
//			xmlFile = new FileInputStream(location);
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		BufferedInputStream fileIn = new BufferedInputStream(stream);
		XMLDecoder decoder = new XMLDecoder(fileIn);
		
		prefs = (Type) decoder.readObject();
		
		decoder.close();
		try {
			fileIn.close();
//			xmlFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prefs;
		
	}
	
	
	
	public static String convertDecimalMinutesToTimeMinutes(double minutes){
		DecimalFormat time = new DecimalFormat("00");
		int fullMinutes = (int)minutes;
		int secondsRemainder = (int)((minutes - fullMinutes) * 60);
		return String.format("%d.%s", fullMinutes, time.format(secondsRemainder));
	}

	/**
	 * This class will populate the FileBean metaData after the MediaPlayer's
	 * status has been changed to READY. Uses the FileBean's setter methods so
	 * that they will be picked up by the XMLEncoder. This allows the use of the
	 * Media's ID3v2 tag reading abilities. If tags are not read due to
	 * incompatibility, they are not changed.
	 * 
	 * This step is computationally expensive but should not need to be done
	 * very often and it saves a ton of memory during normal use. Setting the 
	 * Media and MediaPlayer objects to null make this run much faster and uses
	 * less memory
	 * 
	 * @author Karottop
	 *
	 */
	protected class OnMediaReadyEvent implements Runnable {
		private FileBean fileBean;

		public OnMediaReadyEvent(FileBean fileBean) {
			this.fileBean = fileBean;
		}

		@Override
		public void run() {
			String songName = null;
			String album = null;
			String artist = null;
			double duration = 0.0;
			try{
				// Retrieve track song title
				songName = (String) fileBean.getMedia().getMetadata()
						.get("title");
				
				// Retrieve Album title
				album = (String) fileBean.getMedia().getMetadata()
						.get("album");
				
				// Retrieve Artist title
				artist = (String) fileBean.getMedia().getMetadata()
						.get("artist");
				
				// Retrieve Track duration
				duration = fileBean.getMedia().getDuration().toMinutes();
			}catch(NullPointerException e){
				System.out.println(e.getMessage());
			}
			// Set track song title
			
			if (songName != null)
				fileBean.setSongName(songName);

			// Set Album title
			
			if (album != null)
				fileBean.setAlbum(album);

			// Retrieve and set Artist title
			
			if (artist != null)
				fileBean.setArtist(artist);

			// Set Track duration
			fileBean.setDuration(Double.parseDouble(
					convertDecimalMinutesToTimeMinutes(duration)));
			
			fileBean.getPlayer().dispose();
			fileBean.setMedia(null);
			fileBean.setPlayer(null);

		}

	}
	
	protected class OnMediaPlayerStalled implements Runnable{
		
		private ObservableList<FileBean> playlist;
		private FileBean fileBean;
		
		public OnMediaPlayerStalled(ObservableList<FileBean> playlist, FileBean fileBean) {
			this.playlist = playlist;
			this.fileBean = fileBean;
		}

		@Override
		public void run() {
			int index = playlist.indexOf(this.fileBean);
			fileBean.setPlayer(null);
			fileBean.setMedia(null);
			System.out.println("Removing: " + playlist.remove(index));
		}
		
	}
	
	
}
