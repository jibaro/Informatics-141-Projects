//Chia Huang 25475733
//Fernando De Paz 81962579
package ir.assignments.three;

import ir.assignments.helper.Frequency;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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




public class Crawler{
	private final static String[] sw = {"a",
		"about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't",
		"did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's",
		"hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off",
		"on","once","only","or","other","ought","our","oursourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should",
		"shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this",
		"those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while",
		"who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"};
	
	public final static ArrayList<String> Stop_Word = new ArrayList<String>(Arrays.asList(sw));
	public static final String GroupName = "UCI Inf141-CS121 crawler StudentID 81962579 25475733";

	public static HashMap<String,Integer> Subdomain = new HashMap<String, Integer>();
	public static HashMap<String,Integer> Words = new HashMap<String,Integer>(); 
	public static List<Frequency> siteFrequencies = new ArrayList<Frequency>();
	
	
	public static int siteCount = 0;
	
	public static Date date1 = new Date();
	
	public static int Biggest_Page = 0;
	public static String Biggest_Page_URL;
	public static void main(String[] args) throws Exception{
		String crawlStorageFolder = args[0];
		int numofCrawlers = Integer.parseInt(args[1]);
		
		
		
		CrawlConfig config = new CrawlConfig();
		
		config.setUserAgentString(GroupName);
		
		
		config.setCrawlStorageFolder(crawlStorageFolder);
	    config.setPolitenessDelay(500);
	    config.setMaxDepthOfCrawling(-1);
	    config.setMaxPagesToFetch(-1);
	    config.setIncludeBinaryContentInCrawling(false);
	    config.setResumableCrawling(false);
	    
	    
	    
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        String[] crawlerDomains = new String[] {"http://www.ics.uci.edu/"};
        controller.setCustomData(crawlerDomains);
        
       
        SimpleDateFormat timeFormat1 = new SimpleDateFormat("h:mm:ss");
        String time1 = timeFormat1.format(date1);
        System.out.println(time1);
        
        controller.addSeed("http://www.ics.uci.edu/");
        //controller.addSeed("http://www.lib.uci.edu/");
        //controller.addSeed("http://www.hnet.uci.edu/");
        //controller.addSeed("http://www.math.uci.edu/");
        controller.addSeed("http://www.wics.ics.uci.edu/");
        controller.addSeed("http://www.ics.uci.edu/~lopes/");

        

        controller.start(Parser.class, numofCrawlers);
        
        Date date2 = new Date();
        SimpleDateFormat timeFormat2 = new SimpleDateFormat("h:mm:ss");
        String time2 = timeFormat2.format(date2);
        System.out.println(time2);
        


        
        // The times are in millisecond, so to convert it. To get seconds, divide it by 1000
        long difference = (date2.getTime() - date1.getTime()) / 1000;
        System.out.println("Total time in seconds: " + difference);
        
       
        
       

	}

}


