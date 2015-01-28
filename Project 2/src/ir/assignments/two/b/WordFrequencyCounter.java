//Fernando de Paz
//81962579
//Assignment 2 ICS 121 
package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class WordFrequencyCounter {

	private WordFrequencyCounter() {}

	public static List<Frequency> computeWordFrequencies(List<String> words) {

		List<Frequency> frequency = new ArrayList<Frequency>();
		//Go through tokens and check if they exist, if it does update counter
		for (String token : words){
			boolean exists = false;
			for(Frequency word: frequency){
				if(token.equals(word.getText())){
					word.incrementFrequency();
					exists = true;
					break;
				}
			}
			//if they don't exist create newword of type Frequency and add it to frequency list
			if(exists == false){
				Frequency newword = new Frequency(token,1);
				frequency.add(newword);
			}
			
		}
		return frequency;
	}
	

	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
