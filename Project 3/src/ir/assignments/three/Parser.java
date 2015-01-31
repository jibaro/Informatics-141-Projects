package ir.assignments.three;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
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
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            
            // TODO: remove print
            System.out.println("URL: " + href);
            System.out.println(!FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/"));
            return !FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
    }
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
            System.out.println("URL: " + url);

            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String filename = "ParseData"+ htmlParseData.getTitle() +".txt";
                    try {
                    	
						PrintWriter out = new PrintWriter(filename);
						out.println(text);
						out.close();

						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
					File file = new File(filename);
					List<String> words = Utilities.tokenizeFile(file);
					List<Frequency> frequencies = WordFrequencyCounter.computeWordFrequencies(words);
					Utilities.printFrequencies(frequencies);
					
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    
                    System.out.println("Text length: " + text.length());
                    System.out.println("Html length: " + html.length());
                    System.out.println("Number of outgoing links: " + links.size());
            }
    }
}