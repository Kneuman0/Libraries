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

import javafx.collections.ObservableList;

/**
 * class that handles importing exporting of objects to XML files. 
 * 
 * @author Karottop
 *
 */
public class XmlUtilities {

	/**
	 * Exports a single object of the type passed in to an XML file overwriting any existing file by the same name
	 * @param prefs Object to export
	 * @param exportLocation Absolute path to the file including the file name and extension
	 */
	public static <Type> void exportObjectToXML(Type prefs, String exportLocation){
		FileWriter file = null;
		PrintWriter fileOut = null;
		try {

			file = new FileWriter(exportLocation);
			fileOut = new PrintWriter(file);

		} catch (IOException e) {
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
			e.printStackTrace();
		}
	}

	/**
	 * Exports all Objects passed in to an XML at the specified
	 * location including name of xml file.
	 * 
	 * Pass in <Type> for best functionality
	 * 
	 * @param exportLocation
	 */
	public static <Type> void exportListOfObjectsToXML(String exportLocation,
			List<Type> songs) {

		FileWriter file = null;
		PrintWriter fileOut = null;
		try {

			file = new FileWriter(exportLocation);
			fileOut = new PrintWriter(file);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// ObservableLists apparently cannot be encoded so it is converted to an
		// ArrayList
		ArrayList<Type> toXML = new ArrayList<>();
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
	public static <Type> List<Type> importListOfObjectFromXML(File xml)
			throws FileNotFoundException {

		FileInputStream xmlFile = new FileInputStream(xml.getAbsolutePath());
		BufferedInputStream fileIn = new BufferedInputStream(xmlFile);
		XMLDecoder decoder = new XMLDecoder(fileIn);
		
		@SuppressWarnings("unchecked")
		ArrayList<Type> fromXML = (ArrayList<Type>) decoder
				.readObject();
		decoder.close();
		try {
			fileIn.close();
			xmlFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fromXML;
	}
	
	/**
	 * Reads in object contained in the XML file and return it
	 * @param stream InputStream to XML file
	 * @return returns the object of the <Type> passed in
	 */
	public static <Type> Type readInObjectFromXML(String location) throws FileNotFoundException{
		FileInputStream xmlFile = new FileInputStream(location);
		return readInObjectFromXML(xmlFile);
	}
	
	/**
	 * Reads in object contained in the XML file and return it
	 * @param stream InputStream to XML file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <Type> Type readInObjectFromXML(InputStream stream){
		
		Type prefs = null;
		
		BufferedInputStream fileIn = new BufferedInputStream(stream);
		XMLDecoder decoder = new XMLDecoder(fileIn);
		
		prefs = (Type) decoder.readObject();
		
		decoder.close();
		try {
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prefs;
		
	}
}
