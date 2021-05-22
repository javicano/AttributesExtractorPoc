package io.github.javicano.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.james.mime4j.MimeException;

import io.github.javicano.phishing.filter.Features;
import io.github.javicano.phishing.filter.PhishingFilter;
import io.github.javicano.phishing.filter.PhishingFilterFactory;


public class FilesReader {
	
	private List<Features> featuresList = new ArrayList<Features>();
	
	private boolean isPhishing;
	
	private PhishingFilter phishingFilter;
	
	public FilesReader(boolean isPhishing){
		this.isPhishing = isPhishing;
		this.phishingFilter = PhishingFilterFactory.getInstance();	
	}
	
	public void readCollection(String colllectionPath) throws FileNotFoundException {
		
		File folder = new File(colllectionPath);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
				try {
					Features features = this.phishingFilter.getFeatures(file);
					features.setPhishing(isPhishing);
					featuresList.add(features);
				} catch (StackOverflowError | MimeException | IOException e) {
					System.out.println("ERROR::Error Reading file " + file.getName());
				}
		    } else if(file.isDirectory()) {
		    	this.readCollection(file.getPath());
		    }
		}
	}
	

	public List<Features> getFeaturesList() {
		return this.featuresList;
	}
}
