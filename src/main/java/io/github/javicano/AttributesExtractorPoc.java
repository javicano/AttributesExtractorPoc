package io.github.javicano;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.github.javicano.io.FileWriter;
import io.github.javicano.io.FilesReader;
import io.github.javicano.phishing.filter.Features;

/**
 * AttributesExtractorPoc
 *
 */
public class AttributesExtractorPoc {
    
	public static void main( String[] args ) {
        
		if (args.length != 3) {
			System.out.println("[ERROR] Incorrect arguments ");
			System.out.println("[INFO]  AttributesExtractorPoc: ");
			System.out.println("[INFO]   - arg1: Phishing collection path");
			System.out.println("[INFO]   - arg2: Ham collection path");
			System.out.println("[INFO]   - arg3: Output csv path");
		} else {
			String phishingCollectionPath = args[0];
			String hamCollectionPath = args[1];
			String outputDirectory = args[2];
			
			FilesReader phishingFilesReader = new FilesReader(true);
			FilesReader hamFilesReader = new FilesReader(false);
			
			try {
				phishingFilesReader.readCollection(phishingCollectionPath);
				hamFilesReader.readCollection(hamCollectionPath);
				
				List<Features> phishingFeaturesList = phishingFilesReader.getFeaturesList();
				List<Features> hamFeaturesList = hamFilesReader.getFeaturesList();
				List<Features> featuresList = new ArrayList<Features>();
				
				featuresList.addAll(phishingFeaturesList);
				featuresList.addAll(hamFeaturesList);
				
				List<String[]> dataRows = new ArrayList<String[]>();
				for(Features features: featuresList) {
					if (dataRows.size() == 0) {
						dataRows.add(features.fieldNames());
					}
					dataRows.add(features.toArray());
				}
				
				FileWriter fileWriter = new FileWriter();
				fileWriter.generateCSVfile(dataRows, outputDirectory, "email_features_output");
			} catch (FileNotFoundException e) {
				//TODO: Handle exception
				e.printStackTrace();
			}			
		}
    }
}
