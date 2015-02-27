import os, sys, glob
os.environ["PATH"] +=";C:\\Program Files (x86)\\Java\\jre7\\bin\\client;C:\\Python27\\;C:\\Python27\\Scripts;C:\\Program Files\\Java\\jre7\\bin\\server;"

import jcc
import lucene
from lucene import SimpleFSDirectory, System, File, Document, Field, StandardAnalyzer, IndexWriter, Version, IndexSearcher, QueryParser

def luceneIndexer(docdir,indir):

    """

    Index Documents from a dirrcory

    """

    lucene.initVM()

    DIRTOINDEX = docdir

    INDEXIDR = indir

    indexdir = SimpleFSDirectory(File(INDEXIDR))

    analyzer = StandardAnalyzer(Version.LUCENE_30)

    index_writer = IndexWriter(indexdir,analyzer,True,IndexWriter.MaxFieldLength.LIMITED)

    for tfile in glob.glob(os.path.join(DIRTOINDEX,'*.txt')):

        print ("Indexing: ", tfile)

        document = Document()

        content = open(tfile,'r').read()

        document.add(Field("text",content,Field.Store.YES,Field.Index.ANALYZED))

        index_writer.addDocument(document)

        print ("Done: ", tfile)

    index_writer.optimize()

    print (index_writer.numDocs())

    index_writer.close()
    
def main():
    print("Starting")
    luceneIndexer("./text","./index")
    print("Done")
main()

