package edu.handong.java.utils;

public class Util {
	public static String getExtension(String path)
	{
		return path.substring(path.lastIndexOf(".")+1);
	}
	public static String getPathNoExt(String path)
	{
		return path.substring(0, path.lastIndexOf("."));
	}
}
