//Fernando de Paz
//81962579
//Assignment 2 ICS 121 
package ir.assignments.three;

import ir.assignments.three.Frequency;
import ir.assignments.three.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WordFrequencyCounter {

	private WordFrequencyCounter() {}
	
	public static ArrayList<String> UniqueStrings(List<Frequency> list){
		ArrayList<String> Uniques = new ArrayList<String>();
		for(Frequency x: list){
			if(x.getFrequency() == 1){
				Uniques.add(x.getText());
			}
		}
		Collections.sort(Uniques);
		return Uniques;
	}
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
	


}
