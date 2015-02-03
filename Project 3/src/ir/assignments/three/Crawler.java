package ir.assignments.three;

import ir.assignments.three.Frequency;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import com.sleepycat.je.txn.LockerFactory;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.*;
import edu.uci.ics.crawler4j.url.WebURL;




public class Crawler {

	public static final String GroupName = "UCI Inf141-CS121 crawler StudentID 81962579 25475733";

	public static HashMap<String,Integer> Subdomain = new HashMap<String, Integer>();
	public static HashMap<String,Integer> Words = new HashMap<String,Integer>(); 
	public static int Biggest_Page = 0;
	public static String Biggest_Page_URL;
	public static void main(String[] args) throws Exception{
		String crawlStorageFolder = args[0];
		int numofCrawlers = Integer.parseInt(args[1]);
		
		
		
		CrawlConfig config = new CrawlConfig();
		
		config.setUserAgentString(GroupName);
		
		
		config.setCrawlStorageFolder(crawlStorageFolder);
	    config.setPolitenessDelay(300);
	    config.setMaxDepthOfCrawling(15);
	    config.setMaxPagesToFetch(300);
	    config.setIncludeBinaryContentInCrawling(false);
	    config.setResumableCrawling(false);
	    
	    
	    
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        String[] crawlerDomains = new String[] {"http://www.ics.uci.edu/"};
        controller.setCustomData(crawlerDomains);
        
        Date date1 = new Date();
        SimpleDateFormat timeFormat1 = new SimpleDateFormat("h:mm:ss");
        String time1 = timeFormat1.format(date1);
        System.out.println(time1);
        
        controller.addSeed("http://www.ics.uci.edu/");
        //controller.addSeed("http://www.lib.uci.edu/");
        //controller.addSeed("http://www.hnet.uci.edu/");
        //controller.addSeed("http://www.math.uci.edu/");
        controller.addSeed("http://www.calendar.ics.uci.edu/");
        controller.addSeed("http://www.ics.uci.edu/~lopes/");
        controller.addSeed("https://students.ics.uci.edu/~vutn1");
        

        controller.start(Parser.class, numofCrawlers);
        
        Date date2 = new Date();
        SimpleDateFormat timeFormat2 = new SimpleDateFormat("h:mm:ss");
        String time2 = timeFormat2.format(date2);
        System.out.println(time2);
        

        SortedMap<String,Integer> sorted = new TreeMap<String,Integer>(Subdomain);
        ValueComparator bvc = new ValueComparator(Words);
        TreeMap<String,Integer> sorted_words = new TreeMap<String,Integer>(bvc);
        sorted_words.putAll(Words);
        
        // The times are in millisecond, so to convert it. To get seconds, divide it by 1000
        long difference = (date2.getTime() - date1.getTime()) / 1000;
        System.out.println("Total time in seconds: " + difference);
        
        
       
        String filename = "Subdomains.txt";
        try {
        	PrintWriter out = new PrintWriter(filename);
        	out.println("Number of Subdomains : " + Subdomain.size());
            for(Map.Entry<String, Integer> entry : sorted.entrySet()){
            	
            	
            	//System.out.print(unique);
            	String text = entry.getKey() + " , "+ entry.getValue() ;
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
			while(it.hasNext()){
				Map.Entry<String, Integer> pairs = (Map.Entry)it.next();
				out.println(pairs.getKey() + " , occurance : "+pairs.getValue());
				
			}
			
			out.close();

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("Longest Page: "+Biggest_Page_URL + " , has : "+Biggest_Page+" words");

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
