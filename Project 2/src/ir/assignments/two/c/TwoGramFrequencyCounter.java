//Fernando de Paz
//81962579
//Assignment 2 ICS 121 
package ir.assignments.two.c;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class TwoGramFrequencyCounter {

	private TwoGramFrequencyCounter() {}

	private static List<Frequency> computeTwoGramFrequencies(ArrayList<String> words) {
		List<Frequency> twogram = new ArrayList<Frequency>();
		//Go through list of token words
		for(int i = 0; i < words.size(); i++){
			String word = "";
			//Get current and get next words
			if(i +1 < words.size()){
				word = words.get(i) +" "+ words.get(i+1);
			}
				if (word != ""){
					
				//Check if exists, if so update frequency
				boolean exists = false;
				for(Frequency gram : twogram){
					if(word.equals(gram.getText())){
						gram.incrementFrequency();
						exists = true;
					}
				}
				//If it doesn't exist, create newtwogram as Frequency and add it to list
				if(exists == false){
					Frequency newtwogram = new Frequency(word,1);
					twogram.add(newtwogram);
				}
			}
			
		}
		return twogram;
	}

	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeTwoGramFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}

}
