//Fernando de Paz
//81962579
//Assignment 2 ICS 121 
package ir.assignments.three;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Class overrides the compare operator 
class Compare implements Comparator<Frequency>{

	@Override
	public int compare(Frequency f1, Frequency f2) {
		//Compare frequencies in reverse
		if(f1.getFrequency() > f2.getFrequency()){
			return -1;
		}
		if(f1.getFrequency() < f2.getFrequency()){
			return 1;
		}
		//If frequency is the same, compare alphabetically
		if(f1.getFrequency() == f2.getFrequency()){
			if(f1.getText().compareTo(f2.getText()) < 0){
				return -1;
			}
			if(f1.getText().compareTo(f2.getText()) > 0){
				return 1;
			}
		}
		return 0;
	}
	
}
public class Utilities {
	
	
	public static void Char_to_String(ArrayList<Character> token, ArrayList<String> Tokens){
		//String builder contains the string of Characters
		StringBuilder builder = new StringBuilder(token.size());
		//Lowercase all the characters
		for(Character ch: token){
			
			ch = Character.toLowerCase(ch);
			builder.append(ch);
		}
		//Append characters to builder to make string
		if(builder.length() > 0){
				Tokens.add(builder.toString());
		
			
			
		}
	}
	@SuppressWarnings("resource")
	public static ArrayList<String> tokenizeFile(File input){
		//Prepare to read 
		BufferedReader br;
		ArrayList<String> Tokens = new ArrayList<String>();
		ArrayList<Character> Delimiters = new ArrayList<Character>();
		for(int i = 48; i < 58 ; i++){
			Delimiters.add((char) i);
		}
		for(int i = 65; i < 90 ; i++){
			Delimiters.add((char) i);
		}
		for(int i = 97; i < 122 ; i++){
			Delimiters.add((char) i);
		}
		try {
			br = new BufferedReader(new FileReader(input));
			String line;
			try {
				while((line = br.readLine()) != null){
					//Create new Token list of characters that gets created every time a delimiter is come across
					ArrayList<Character> token = new ArrayList<Character>();
					// Go through each Character
					for(int i = 0; i < line.length(); i++){
						//check if the char matches a delimiter
						// if so send to Char_to_String to make a string out of a list of characters
						// Make a new Token to start over
						if(! Delimiters.contains(line.charAt(i))){
							Char_to_String(token,Tokens);
							token = new ArrayList<Character>();
							
						}
						else{
							//Add Char to token to the list
							token.add(line.charAt(i));
							
							if(i+1 == line.length()){
								
								Char_to_String(token,Tokens);
							}
						}
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Tokens;
	}
	

	public static void printFrequencies(List<Frequency> frequencies){
		int count = 0;
		//Override the sort method in collections to compare differently
		Collections.sort(frequencies, new Compare());
		for(Frequency f: frequencies){
			count += f.getFrequency();
		}
		System.out.println("Total item count: "+String.valueOf(count));
		System.out.println("Unique item count: "+ String.valueOf(frequencies.size())+"\n");
		for(Frequency f : frequencies){
			System.out.print(f.getText() + " : " + String.valueOf(f.getFrequency())+ " | ");
		}
		System.out.println(" }");
	}

}

