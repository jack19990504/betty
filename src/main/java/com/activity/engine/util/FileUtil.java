package com.activity.engine.util;

import javax.ws.rs.core.MultivaluedMap;

public class FileUtil {
	private StringBuilder originalName;
	private StringBuilder fileType;
	private StringBuilder fileName;
	private StringBuilder lowerCaseName;
	
	public FileUtil(){}
	
	public FileUtil(StringBuilder originalName) {
		super();
		this.originalName = originalName;
		this.fileType = new StringBuilder(originalName.substring(originalName.lastIndexOf(".")+1).toLowerCase());
		this.fileName = new StringBuilder(originalName.substring(0,originalName.lastIndexOf(".")));
		this.lowerCaseName = new StringBuilder(fileName+"."+fileType);
	}

	public StringBuilder getOriginalName() {
		return originalName;
	}

	public void setOriginalName(StringBuilder originalName) {
		this.originalName = originalName;
		this.fileType = new StringBuilder(originalName.substring(originalName.lastIndexOf(".")+1).toLowerCase());
		this.fileName = new StringBuilder(originalName.substring(0,originalName.lastIndexOf(".")));
		this.lowerCaseName = new StringBuilder(fileName+"."+fileType);
	}

	public StringBuilder getFileType() {
		return fileType;
	}

	public void setFileType(StringBuilder fileType) {
		this.fileType = fileType;
	}

	public StringBuilder getFileName() {
		return fileName;
	}

	public void setFileName(StringBuilder fileName) {
		this.fileName = fileName;
	}

	public StringBuilder getLowerCaseName() {
		return lowerCaseName;
	}

	public void setLowerCaseName(StringBuilder lowerCaseName) {
		this.lowerCaseName = lowerCaseName;
	}
	public String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		String[] name = null;
		String finalFileName = "";
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				name = filename.split("=");
				finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	
}