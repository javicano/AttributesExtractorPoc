package io.github.javicano.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileWriter {

	private String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .collect(Collectors.joining(","));
	} 
	
	public void generateCSVfile(List<String[]> dataRows, String outputDirectory, String outputFileName) throws FileNotFoundException {
		System.out.println("Generating " + outputFileName + ".cvs file");
		File csvOutputFile = new File(outputDirectory + "/" + outputFileName + ".csv");
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	    	dataRows.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
		System.out.println("Done");
	}

}
