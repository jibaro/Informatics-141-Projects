//Chia Huang 25475733
//Fernando De Paz 81962579
package ir.assignments.three;

import ir.assignments.helper.Frequency;
import ir.assignments.helper.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;


public class Parser extends WebCrawler{
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf|mat" 
            + "|rm|smil|wmv|swf|wma|zip|rar|gz|cpp|c|))$");
    
    
    public ArrayList<String> urlSite = new ArrayList<String>();
    public static HashMap<String,Integer> Subdomain = new HashMap<String, Integer>();
    
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        
        // TODO: add each url to list & .add() append the element return true;
        
        if(href.contains("drzaius.ics.uci.edu")){
        	Subdomain.put("drzaius.ics", 1);
        	return false;
        }
        if(href.contains("calendar.ics.uci.edu")){
        	Subdomain.put("calendar.ics", 1);
        	return false;
        }
        if(href.contains("archive.ics.uci.edu")){
        	Subdomain.put("archive.ics", 1); 
        	return false;
        }
        if(href.contains("wics.ics.uci.edu")){
        	Subdomain.put("wics.ics", 1);
        	return false;
        }
        if(href.contains("fano.ics.uci.edu")){
        	Subdomain.put("fano.ics", 1);
        	return false;
        }
        if(href.contains("vcp.ics.uci.edu")){
        	Subdomain.put("vcp.ics", 1);
        	return false;
        }
        if(href.contains("luci.ics.uci.edu")){
        	Subdomain.put("luci.ics", 1);
        	return false;
        }
        // TODO: only check the domain not subdomain & need to optimize it
        
        if(!FILTERS.matcher(href).matches() && href.contains(".ics.uci.edu"))
        {
        	
        	//urlSite.add(href);
        	// TODO: Use WordFrequencyCounter to see uniqueness
            //Crawler.siteFrequencies = WordFrequencyCounter.computeWordFrequencies(href,Crawler.siteFrequencies);
            
            return true;
        }
        return false;
            //return !FILTERS.matcher(href).matches() && href.contains(".ics.uci.edu");
        	//return true;
    }
    @Override
    public void visit(Page page) {
    		
    		String line = null;
    		File input = new File("Subdomains.txt");
            
			try {
				
				BufferedReader bf = new BufferedReader(new FileReader(input));
				
				while((line = bf.readLine()) != null){
					String[] things = line.split("\\s+");
					
					if(things.length > 1){
						break;
					}
					for(int i = 0; i < things.length; i++){
						if(i+1 < things.length){
							Subdomain.put(things[i], Integer.valueOf(things[i+1]));
						}
					}
					
					
				}
				bf.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(Subdomain);
    		
            String url = page.getWebURL().getURL();
            String subdomain = page.getWebURL().getSubDomain();
            
            if(Subdomain.containsKey(subdomain)){
            	int counter = Subdomain.get(subdomain);
            	counter +=1;
            	Subdomain.put(subdomain, counter);
            }
            else{
            	
            	Subdomain.put(subdomain, 1);
            }
            
            
            Frequency freq = new Frequency(url);
            freq.incrementFrequency();
            Crawler.siteFrequencies.add(freq);
            Crawler.siteCount++;
            
            System.out.println("This is: " + url + " Site_count: " + Crawler.siteCount);
            
            //System.out.println("URL: " + url);

            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    
                    String text = htmlParseData.getText();
                    
                    ArrayList<String> tokens = Utilities.tokenizeFile(text);
                    
                    for(String word : tokens){
                        if(Crawler.Words.containsKey(word)){
                        	int counter = Crawler.Words.get(word);
                        	counter += 1;
                        	Crawler.Words.put(word,counter);
                        	
                        	
                        }
                        else{
                        	Crawler.Words.put(word,1);
                        }
                    }

                    
                    
                    String html = htmlParseData.getHtml();
                   

                    List<WebURL> links = htmlParseData.getOutgoingUrls();

                    if(Crawler.Biggest_Page < text.length()){
                    	Crawler.Biggest_Page = text.length();
                    	Crawler.Biggest_Page_URL = url;
                    }
                    
                    Date date2 = new Date();
                    long difference = (date2.getTime() - Crawler.date1.getTime()) / 1000;
                    System.out.println("Total time so far in seconds: " + difference);
                    
                    
                    
                    //System.out.println("Text length: " + text.length());
                    //System.out.println("Html length: " + html.length());
                    //System.out.println("Number of outgoing links: " + links.size());
                    
                    
            }
            
            SortedMap<String,Integer> sorted = new TreeMap<String,Integer>(Subdomain);
            ValueComparator bvc = new ValueComparator(Crawler.Words);
            TreeMap<String,Integer> sorted_words = new TreeMap<String,Integer>(bvc);
            sorted_words.putAll(Crawler.Words);
            
            String filename = "Subdomains.txt";
            try {
            	PrintWriter out = new PrintWriter(filename);
            	out.println("Number of Subdomains : " + Subdomain.size());
                for(Map.Entry<String, Integer> entry : sorted.entrySet()){
                	
                	
                	//System.out.print(unique);
                	String text = entry.getKey() + "  "+ entry.getValue() ;
                	out.println(text);
                }
    			
    			
    			out.close();

    			
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            String filename1 = "Words.txt";
            try {
            	
    			PrintWriter out = new PrintWriter(filename1);
    			Iterator it = sorted_words.entrySet().iterator();
    			//Iterator it = Words.entrySet().iterator();
    			int count = 0;
    			while(it.hasNext()){
    				Map.Entry<String, Integer> pairs = (Map.Entry)it.next();
    				
    				if(!Crawler.Stop_Word.contains(pairs.getKey())){
    					if (count < 500){
    					out.println(pairs.getKey() + " , occurance : "+pairs.getValue());
    					count++;
    					}
    					else{break;}
    				}
    			}
    			
    			out.close();

    			
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
            String filename2 = "SiteFrequencies.txt";
            try {
            	
    			PrintWriter out = new PrintWriter(filename2);
    			
    			int count = 0;
    			for(Frequency url1: Crawler.siteFrequencies){
    				if(url1.getFrequency() <= 1)
    				{
    					out.println(url1.getText() + " : " + url1.getFrequency() );
    					count++;
    				}
    			}
    			System.out.println("The number of unique pages : " + count);
    			out.println("The number of unique pages : " + count);
    			out.close();

    			
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            System.out.println("Longest Page: "+ Crawler.Biggest_Page_URL + " , has : "+Crawler.Biggest_Page+" words");
            System.out.println("---------------------------------------------------------");

    	}

            
    
    
}
class ValueComparator implements Comparator<String>{
	Map<String,Integer> base;
	public ValueComparator(Map<String,Integer> base){
		this.base = base;
	}
	public int compare(String a, String b){
		if(base.get(a) >= base.get(b)){
			return -1;
		}
		else{
			return 1;
		}
	}
}