package ir.assignments.three;

import java.util.List;
import java.util.regex.Pattern;

import com.sleepycat.je.txn.LockerFactory;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.*;
import edu.uci.ics.crawler4j.url.WebURL;


public class Crawler {

	
	public static void main(String[] args) throws Exception{
		String crawlStorageFolder = args[0];
		int numofCrawlers = Integer.parseInt(args[1]);
		
		CrawlConfig config = new CrawlConfig();
		
		config.setCrawlStorageFolder(crawlStorageFolder);
	    config.setPolitenessDelay(1000);
	    config.setMaxDepthOfCrawling(2);
	    config.setMaxPagesToFetch(3);
	    config.setIncludeBinaryContentInCrawling(false);
	    config.setResumableCrawling(false);
	    
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        controller.addSeed("http://www.ics.uci.edu/~welling/");
        controller.addSeed("http://www.ics.uci.edu/~lopes/");
        controller.addSeed("http://www.ics.uci.edu/");

        controller.start(Parser.class, numofCrawlers); 
	}
}
