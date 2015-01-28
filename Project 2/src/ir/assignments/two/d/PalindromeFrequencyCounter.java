//Fernando de Paz
//81962579
//Assignment 2 ICS 121 
package ir.assignments.two.d;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PalindromeFrequencyCounter {

	private PalindromeFrequencyCounter() {}

	public static boolean PalindromeChecker(String Palindrome){
		if(Palindrome.length() < 3){
			return false;
		}
		String NoSpaces = "";
		//Create a string with pure characters
		for(int i = 0; i < Palindrome.length();i++){
			Character x = Palindrome.charAt(i);
			if(x.equals(' ') == false){
				NoSpaces += String.valueOf(Palindrome.charAt(i));
			}
			
		}
		String left = "";
		String right = "";
		//Check weather string is odd or even, split it accordingly
		if((NoSpaces.length() % 2) == 0){
			left = NoSpaces.substring(0,NoSpaces.length()/2);
			right = NoSpaces.substring(NoSpaces.length()/2  , NoSpaces.length());
			
		}
		else{
			left = NoSpaces.substring(0,NoSpaces.length()/2+1);
			right = NoSpaces.substring(NoSpaces.length()/2 , NoSpaces.length());
		}
		
		//Create reversed version of the right side and compare
		String reversed = new StringBuilder(right).reverse().toString();
		if(left.equals(reversed)){
			return true;
		}

		return false;
	}
	private static List<Frequency> computePalindromeFrequencies(ArrayList<String> words) {
		List<Frequency> Palindromes = new ArrayList<Frequency>();
		String combined = "";
		//Combine Separate words into one long string for analysis
		for(String Token : words){
			combined += Token+ " ";
		}
		//Go through combined string, character by character
		for(int i = 0; i < combined.length(); i++){
			if(combined.charAt(i) != ' '){
				String copy = combined.substring(i, combined.length());

				int count = 0;
				Character countee  = combined.charAt(i);
	
				for (int j = 0; j< copy.length(); j++){
					Character counter = copy.charAt(j);
					if(countee.equals(counter)){
						count++;
					}
				}
	
				for(int k = 0; k < count; k++){
					if(k + 1 == count){
						break;
					}
					String reversed = new StringBuilder(copy).reverse().toString();
					int index1 = copy.indexOf(combined.charAt(i));
					int index2 = reversed.indexOf(combined.charAt(i));
					
					String substring = copy.substring(index1, copy.length()-index2);
					
					copy = copy.substring(index1, copy.length()-index2-1);
					// Look for copies, update frequencies
					if(PalindromeChecker(substring) == true){
						i += substring.length();
						boolean exists = false;
						for(Frequency palindrome : Palindromes){
							if(palindrome.getText().equals(substring)){
								exists = true;
								palindrome.incrementFrequency();
								
								break;
							}
						}
						//Add String as Frequency to Palindromes list
						if(exists == false){
							Frequency newsubstring = new Frequency(substring,1);
							Palindromes.add(newsubstring);
							break;
						}
						
					}
					
					 
					
					
				}

				if(i+1 >= combined.length()){
					break;
				}
			}
		}
		return Palindromes;
	}
	

	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computePalindromeFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}

}
