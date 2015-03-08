package lucene;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

/** Simple command-line based search demo. */
public class SearchFiles {

  private SearchFiles() {}

  /** Simple command-line based search demo. */
  public static void main(String[] args) throws Exception {
    String usage =
      "Usage:\tjava org.apache.lucene.demo.SearchFiles [-index dir] [-field f] [-repeat n] [-queries file] [-query string] [-raw] [-paging hitsPerPage]\n\nSee http://lucene.apache.org/core/4_1_0/demo/ for details. :-)";
    if (args.length > 0 && ("-h".equals(args[0]) || "-help".equals(args[0]))) {
      System.out.println(usage);
      System.exit(0);
    }

    String index = "./index";
    String field = "content";
    String queryString = "solution";
    int hitsPerPage = 10;
    
    IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
    IndexSearcher searcher = new IndexSearcher(reader);
    // :Post-Release-Update-Version.LUCENE_XY:
    Analyzer analyzer = new StandardAnalyzer();

    QueryParser parser = new QueryParser("content", analyzer);

      
    Query query = parser.parse(queryString);
    System.out.println("Searching for: " + queryString);
            

    TopDocs results = searcher.search(query, reader.maxDoc());
    ScoreDoc[] hits = results.scoreDocs;
    
    int numTotalHits = results.totalHits;
    System.out.println(numTotalHits + " total matching documents");
  
    
    
    
    SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
    Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
    highlighter.setMaxDocCharsToAnalyze(90000000);
      

      for (int i = 0; i < numTotalHits; i++) {
    	  int docID = results.scoreDocs[i].doc;
    	  
    	  Terms terms = reader.getTermVector(docID, field);
    	  TermsEnum termE= terms.iterator(null);
    	  
    	  while(termE.next() != null){
    		  BytesRef term = termE.term();
    		  if(term.utf8ToString().equals(queryString))
    		  {
    			  System.out.println("Total Occurences: "+ termE.totalTermFreq());
    		  }
    	  }
    	  
    	  int docId = results.scoreDocs[i].doc;
    	  	    
    	    	    
        
    	  Document doc = searcher.doc(docId);   
    	  String path = doc.get("path");
    	  System.out.println((i+1) + ". " + path);
    	  

    	  String text = doc.get("content");
    	  TokenStream tokenStream = TokenSources.getAnyTokenStream(reader, docId, "content", analyzer);
    	  TextFragment[] frag = highlighter.getBestTextFragments(tokenStream,text,false,10);
    	  
    	  for(int j = 0; j<frag.length;j++){
    		  if ((frag[j] != null) && (frag[j].getScore() > 0)) {
    	          System.out.println((frag[j].toString()));
    	        }
    	  }
    	  System.out.println("------------------------");
          
      }
      
      reader.close();   
  }
}
