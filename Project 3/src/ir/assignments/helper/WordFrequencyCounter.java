//Chia Huang 25475733
//Fernando De Paz 81962579
//Assignment 2 ICS 121 
package ir.assignments.helper;

import ir.assignments.helper.Frequency;
import ir.assignments.helper.Utilities;

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
	public static List<Frequency> computeWordFrequencies(String Url,List<Frequency> frequency) {

		//Go through tokens and check if they exist, if it does update counter
		
			boolean exists = false;
			for(Frequency word: frequency){
				if(Url.equals(word.getText())){
					word.incrementFrequency();
					exists = true;
					break;
				}
			}
			//if they don't exist create newword of type Frequency and add it to frequency list
			if(exists == false){
				Frequency newword = new Frequency(Url,1);
				frequency.add(newword);
			}
			
		
		return frequency;
	}
	


}
