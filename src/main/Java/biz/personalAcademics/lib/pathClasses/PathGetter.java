package biz.personalAcademics.lib.pathClasses;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Available in both static and instance methods. These methods are made for use with jar files
 * @author Karottop
 *
 */
public class PathGetter {
	
	Object localClass;
	
	/**
	 * Pass in a class that you want to use as a starting point for
	 * your relative path.
	 * @param localClass
	 */
	public PathGetter(Object localClass){
		this.localClass = localClass;
	}
	
	// if using static overloaded methods
	public PathGetter(){
		localClass = null;
	}
	
	/**
	 * returns an absolute File:/// URI in string form back to the subfolder containing the class that was
	 * passed into the PathGetter object. String has File:/// string built in
	 * 
	 * If used in a runnable jar, the method will return an absolute path back to the jar file
	 * 
	 * String will end with a '/'
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getFileAbsoluteSubfolderURIString() throws UnsupportedEncodingException{
		File currentJavaJarFile = new File(localClass.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
		String currentRootDirectoryPath = currentJavaJarFilePath.replace(
				currentJavaJarFile.getName(), "").replace("\\", "/");
		
		return "File:///" + URLEncoder.encode(currentRootDirectoryPath, "UTF-8").replace("+", "%20");
	}
	
	/**
	 * returns an absolute path back to the sub-folder containing the class that was
	 * passed into the PathGetter object.
	 * 
	 * If used in a runnable jar, the method will return an absolute path back to the jar file
	 * 
	 * String will end with a '/'
	 * @return
	 */
	public String getAbsoluteSubfolderPath(){
		File currentJavaJarFile = new File(localClass.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
		String currentRootDirectoryPath = currentJavaJarFilePath.replace(
				currentJavaJarFile.getName(), "").replace("\\", "/");
		return currentRootDirectoryPath;
	}
	
	/**
	 * returns an absolute path back to the subfolder containing the class that was
	 * passed into the PathGetter object. Sting contains '\' instead of '/'
	 * 
	 * This method should only be used to display a path to a user. It will not work
	 * as a path in java due to the incapatable '\' syntax
	 * @return
	 */
	public String getAbsoluteSubfolderPathForDisplayOnly(){
		File currentJavaJarFile = new File(localClass.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
		String currentRootDirectoryPath = currentJavaJarFilePath.replace(
				currentJavaJarFile.getName(), "");
		return currentRootDirectoryPath; 
	}
	
	public String readThis(){
		return "This String Right Here";
	}
	
}
