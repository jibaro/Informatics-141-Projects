package ir.assignments.three;

import ir.assignments.helper.Frequency;
import ir.assignments.helper.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;


public class Parser extends WebCrawler{
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
            + "|rm|smil|wmv|swf|wma|zip|rar|gz|cpp|c|))$");
    
    
    public ArrayList<String> urlSite = new ArrayList<String>();
    
    
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        
        // TODO: add each url to list & .add() append the element return true;
        
        if(href.contains("drzaius.ics.uci.edu")){
        	Crawler.Subdomain.put("drzaius.ics", 1);
        	return false;
        }
        if(href.contains("calendar.ics.uci.edu")){
        	Crawler.Subdomain.put("calendar.ics", 1);
        	return false;
        }
        if(href.contains("archive.ics.uci.edu")){
        	Crawler.Subdomain.put("archive.ics", 1);
        	return false;
        }
        if(href.contains("wics.ics.uci.edu")){
        	Crawler.Subdomain.put("wics.ics", 1);
        	return false;
        }
        if(href.contains("fano.ics.uci.edu")){
        	Crawler.Subdomain.put("flamingo.ics", 1);
        	return false;
        }
        if(href.contains("flamingo.ics.uci.edu")){
        	Crawler.Subdomain.put("flamingo.ics", 1);
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
            String url = page.getWebURL().getURL();
            String subdomain = page.getWebURL().getSubDomain();

            if(Crawler.Subdomain.containsKey(subdomain)){
            	int counter = Crawler.Subdomain.get(subdomain);
            	counter +=1;
            	Crawler.Subdomain.put(subdomain, counter);
            }
            else{
            	
            	Crawler.Subdomain.put(subdomain, 1);
            }
            
            
            Frequency freq = new Frequency(url);
            freq.incrementFrequency();
            Crawler.siteFrequencies.add(freq);
            
            
            System.out.println("This is: " + url + " count: " + Crawler.count);
            
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
                    
                    System.out.println("---------------------------------------------------------");
            }
    }
}