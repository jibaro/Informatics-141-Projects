package ir.assignments.three;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    
    
    public ArrayList<String> urlSite = new ArrayList<String>();
    List<Frequency> siteFrequencies;
    int count=0;
    
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        
        // TODO: add each url to list & .add() append the element return true;
        urlSite.add(href);
       
        // TODO: only check the domain not subdomain & need to optimize it
        
        if(href.contains(".ics.uci.edu"))
        {
        	// TODO: Use WordFrequencyCounter to see uniqueness
            siteFrequencies = WordFrequencyCounter.computeWordFrequencies(urlSite);
            
        }
            return href.contains(".ics.uci.edu");
        	//return true;
    }
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
            String subdomain = page.getWebURL().getSubDomain();

            if(Crawler.Subdomain.containsKey(subdomain)){
            	ArrayList<String> list = Crawler.Subdomain.get(subdomain);
            	list.add(url);
            }
            else{
            	ArrayList<String> list = new ArrayList<String>();
            	list.add(url);
            	Crawler.Subdomain.put(subdomain, list);
            }
            
            
            
            
            System.out.println("URL: " + url);

            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    
                    String text = htmlParseData.getText();
                    text = text.replaceAll("^[a-aA-Z0-9_]+$", " " ).toLowerCase();
                    String[] tokens = text.split("\\s+");
                    
                    for(String word : tokens){
                        if(Crawler.Words.containsKey(word)){
                        	int counter = Crawler.Words.get(word);
                        	counter += 1;
                        	
                        }
                        else{
                        	Crawler.Words.put(word,1);
                        }
                    }

                    
                    
                    String html = htmlParseData.getHtml();
                   

                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    System.out.println("The size of this array: " + siteFrequencies.size());
                    for(int i=0; i<siteFrequencies.size(); i++)
                    {
                    	if(siteFrequencies.get(i).getFrequency() <= 1)
                    	{
                    		count++;
                    	}
                    	
                    }
                    if(Crawler.Biggest_Page < text.length()){
                    	Crawler.Biggest_Page = text.length();
                    	Crawler.Biggest_Page_URL = url;
                    }
                    System.out.println("Text length: " + text.length());
                    System.out.println("Html length: " + html.length());
                    System.out.println("Number of outgoing links: " + links.size());
            }
    }
}