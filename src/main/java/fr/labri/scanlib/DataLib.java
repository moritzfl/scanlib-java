package fr.labri.scanlib;

import java.util.HashSet;
import java.util.Set;

public class DataLib {
	
	String library;
	Set<String> files = new HashSet<String>();
	int nbFiles;
	
	public Set<String> getFiles() {
		return files;
	}

	public void setFiles(Set<String> files) {
		this.files = files;
	}
	int totalFiles;
	public int getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(int totalFiles) {
		this.totalFiles = totalFiles;
	}
	double ratio;
	
	public DataLib(String library, int nbFiles, double ratio, int totalFiles, Set<String> files) {
		super();
		this.library = library;
		this.nbFiles = nbFiles;
		this.ratio = ratio;
		this.totalFiles = totalFiles;
		this.files = files;
	}
	
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	public int getNbFiles() {
		return nbFiles;
	}
	public void setNbFiles(int nbFiles) {
		this.nbFiles = nbFiles;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
}
