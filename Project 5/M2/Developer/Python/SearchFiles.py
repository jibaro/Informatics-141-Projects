import os, sys, glob
os.environ["PATH"] +=";C:\\Program Files (x86)\\Java\\jre7\\bin\\client;C:\\Python27\\;C:\\Python27\\Scripts;C:\\Program Files\\Java\\jre7\\bin\\server;"

import jcc
import lucene
from lucene import SimpleFSDirectory, System, File, Document, Field, StandardAnalyzer, IndexWriter, Version, IndexSearcher, QueryParser


def luceneRetriver(storedir, query):
    INDEXDIR = storedir
    lucene.initVM()

    indir = SimpleFSDirectory(File(INDEXDIR))

    lucene_analyzer = StandardAnalyzer(Version.LUCENE_30)

    lucene_searcher = IndexSearcher(indir)

    my_query = QueryParser(Version.LUCENE_30,"text",lucene_analyzer).parse(query)

    MAX = 1000

    total_hits = lucene_searcher.search(my_query,MAX)

    print ("Hits: ",total_hits.totalHits)

    for hit in total_hits.scoreDocs:

        print ("Hit Score: ",hit.score, "Hit Doc:",hit.doc, "Hit String:",hit.toString())

        doc = lucene_searcher.doc(hit.doc)

        print (doc.get("text").encode("utf-8"))

def main():
    luceneRetriver("./index","German")
    print("Done")
main()
