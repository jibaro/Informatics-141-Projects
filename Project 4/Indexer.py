import os
import re
import sqlite3
import Porter_Stem

STOP_WORDS = ["a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't",
"did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's",
"hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off",
"on","once","only","or","other","ought","our","oursourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should",
"shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this",
"those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while",
"who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"]




class Frequency:
    
    
    def __init__(self,word,root):
        self.word = word
        self.root = {}
        
    def __str__(self):
        string = ""
        string += self.word + "\t"
        keylist2 = list(self.root.keys())
        keylist2.sort()
        for location in keylist2:
            string += location + " " + str(len(self.root[location]))+" : ["
            for thing in self.root[location]:
                string += str(thing) + ", "
            string+="]\n\t\t"
        return string

    def add_count(self, path, position):
        if(path in self.root):
            info = self.root[path]
            info.append(position)
            self.root[path] = info
        else:
            info = [position]
            self.root[path] = info


def Write_Out(Words,conn):
    cursor = conn.cursor()
    keylist = list(Words.keys())
    keylist.sort()
    scripts = []
    
    for key in keylist:
        word_key = key
        olist = Words[key].root
        
        for key2 in olist:
            path_key = key2 
            nlist = list(olist[key2])
            for locations in nlist:
                if(locations != ""):
                    location_value = locations
                    
                    scripts.append("INSERT INTO words VALUES('"+ word_key + "','"+path_key+"',"+str(location_value)+")")
    cursor.execute("BEGIN")
    for script in scripts:
        cursor.execute(script)
    try:
        cursor.execute("COMMIT")
    except:
        print("")
    conn.commit()
    
                    


def Sort_Export(conn):

    cursor = conn.cursor()


    print("Writing")
    f = open('output.txt','w')
    word = ""
    string = ""
    location = ""
    places = []
    print("Printing Out")
    print("Ordering")
    for row in cursor.execute("SELECT * FROM words ORDER BY word, path"):

        if(row[0] != word):
            if(string == ""):
                string = location +" " +str(len(places)) + " : ["
                for place in places:
                        string += str(place) 
                string += "]\n\t\t"
            f.write(word + "\t" + string + "\n\n")
            
            word = row[0]
            location = row[1]
            places = [row[2]]
            string = ""
            
        else:
            if(row[1] != location):
                string += location +" " +str(len(places)) + " : ["
                for place in places:
                    string += str(place) + ", "
                string += "]\n\t\t"
                location = row[1]
                places = [row[2]]
            else:
                places.append(row[2])
                
    f.close()
    
        
def Frequency_Counter(Words, parsed_words,root):
    count = 0
    
    for word in parsed_words:
        word = Porter_Stem.STEM(word)
        
        if(not word in STOP_WORDS):
            
            if(len(word) > 1):
                if(word in Words):
                    
                    tempword = Words[word]
                    
                    tempword.add_count(root,count)
                    Words[word] = tempword
                else:
                    f =  Frequency(word,root)
                    f.add_count(root,count)
                    Words[word] = f
                    
                count +=1
    return Words
    
    
def Parser(raw_file):

    start = False
    Content = ""
    for line in raw_file:

        if(start):
            if(":" in line):
                line= line[line.index(":")+1:]
            line = re.sub('[-/)(\n\t\\\*=&,".<>;?!~%+#$|_{}'+"'"+']', " ",line)
            Content += line
        if("ID: " in line):
           
            start = True
            
    Words = Content.split(" ")
    return list(filter(None,Words))
    

def main(): 
    print("start")
    conn = sqlite3.connect("out_file_db.db")

    cursor = conn.cursor()
    cursor.execute("DROP TABLE IF EXISTS words")
    cursor.execute("CREATE TABLE words (word TEXT, path TEXT,location INT)")
    cursor.execute("PRAGMA synchronous = OFF")
    conn.commit()
    print("opened")
            
    Words = {}
    for root, dirs, files in os.walk("."):
        count = 0
        path = root.split('\\')
        if (len(path) == 4):
            count +=1
            
            print(str(count)+" / " + "150 " + path[-1])
            Write_Out(Words, conn)
            Words = {}
            
        if(len(path) > 4):
            
            for i in range(len(files)):
                #print("{:3f}".format(count/len(files) * 100))
                
                f = open(root+'\\'+files[i],'r')
                parsed_words = Parser(f.readlines())
                s_Dir = ""
                c=0
                for sub in path:
                    if(c >2):
                        s_Dir += sub + "\\"
                    c+=1

                Words = Frequency_Counter(Words, parsed_words,s_Dir+files[i]+".txt")
                
                
                f.close()
                count +=1
    print("Done traversing")
    print("Writing to SQL...")
    Write_Out(Words,conn)

    Sort_Export(conn)
    conn.close()
    print("done")

    
main()
