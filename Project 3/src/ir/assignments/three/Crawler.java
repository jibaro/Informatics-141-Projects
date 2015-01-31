package ir.assignments.three;

import ir.assignments.three.Frequency;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import com.sleepycat.je.txn.LockerFactory;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.*;
import edu.uci.ics.crawler4j.url.WebURL;




public class Crawler {

	public static final String GroupName = "UCI Inf141-CS121 crawler StudentID 81962579 25475733";

	public static HashMap<String,ArrayList<String>> Subdomain = new HashMap<String, ArrayList<String>>();
	public static void main(String[] args) throws Exception{
		String crawlStorageFolder = args[0];
		int numofCrawlers = Integer.parseInt(args[1]);
		
		
		
		CrawlConfig config = new CrawlConfig();
		
		config.setUserAgentString(GroupName);
		
		
		config.setCrawlStorageFolder(crawlStorageFolder);
	    config.setPolitenessDelay(300);
	    config.setMaxDepthOfCrawling(2);
	    config.setMaxPagesToFetch(30);
	    config.setIncludeBinaryContentInCrawling(false);
	    config.setResumableCrawling(false);
	    
	    
	    
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        Date date1 = new Date();
        SimpleDateFormat timeFormat1 = new SimpleDateFormat("h:mm:ss");
        String time1 = timeFormat1.format(date1);
        System.out.println(time1);
        
        controller.addSeed("http://www.ics.uci.edu/");
        controller.addSeed("http://www.lib.uci.edu/");
        controller.addSeed("http://www.hnet.uci.edu/");
        controller.addSeed("http://www.math.uci.edu/");
        //controller.addSeed("http://www.ics.uci.edu/~welling/");
        //controller.addSeed("http://www.ics.uci.edu/~lopes/");
        //controller.addSeed("https://students.ics.uci.edu/~vutn1");
        

        controller.start(Parser.class, numofCrawlers);
        
        Date date2 = new Date();
        SimpleDateFormat timeFormat2 = new SimpleDateFormat("h:mm:ss");
        String time2 = timeFormat2.format(date2);
        System.out.println(time2);
        

        SortedMap<String,ArrayList<String>> sorted = new TreeMap<String,ArrayList<String>>(Subdomain);
        
        
        // The times are in millisecond, so to convert it. To get seconds, divide it by 1000
        long difference = (date2.getTime() - date1.getTime()) / 1000;
        System.out.println("Total time in seconds: " + difference);
        
        System.out.println("Number of Subdomains : " + Subdomain.size());
        
        for(Map.Entry<String, ArrayList<String>> entry : sorted.entrySet()){
        	System.out.println(entry.getKey() + " "+ entry.getValue() );
        	
        }
	}

	
}
