package biz.personalAcademics.lib.pathClasses;

import java.io.File;


public class PathGetter {
	
	Object localClass;
	
	public PathGetter(Object localClass){
		this.localClass = localClass;
	}
	
	/**
	 * returns an absolute File:/ URI in string form back to the subfolder containing the class that was
	 * passed into the PathGetter object. String has File:/ string built in
	 * 
	 * If used in a runnable jar, the method will return an absolute path back to the jar file
	 * 
	 * String will end with a '/'
	 * @return
	 */
	public String getFileAbsoluteSubfolderURIString(){
		File currentJavaJarFile = new File(localClass.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
		String currentRootDirectoryPath = currentJavaJarFilePath.replace(
				currentJavaJarFile.getName(), "").replace("\\", "/");
		return "File:/" + currentRootDirectoryPath;
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
	
}
