import os
STOP_WORDS = ["a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't",
"did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's",
"hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off",
"on","once","only","or","other","ought","our","oursourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should",
"shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this",
"those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while",
"who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"]
def Parser(raw_file):

    start = False
    Content = ""
    for line in raw_file:
        if(start):
            
            Content += line.rstrip()
        if("X-FileName" in line):
            start = True
            
    Words = Content.split(" ")
    
    print(Words)
def main():
    Words = {}
    for root, dirs, files in os.walk("."):
        path = root.split('\\')
        if(len(path) > 4):
            for i in range(len(files)):
                
                f = open(root+'\\'+files[i],'r')
                Parser(f.readlines())
                
                f.close()
    
main()