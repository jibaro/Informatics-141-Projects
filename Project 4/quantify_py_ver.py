import os
import operator

count = 0
total_people = 0
total_inbox = 0
total_sent = 0
num_files = 0
Total_data ={}
Sent_mail = {}
Inbox_mail = {}
string = 'Complete ['
for root, dirs, files in os.walk("."):
    path = root.split('\\')

    if(len(path) == 3):
        total_people = len(dirs)
    if(len(path) == 4):
        Total_data[path[3]] = 0
        Inbox_mail[path[3]] = 0
        Sent_mail[path[3]] = 0
        count+=1
    if(len(path)> 4):
        if("sent" in path[4]):
            total_sent += len(files)
            Sent_mail[path[3]] += len(files)
        else:
            if(not "deleted" in path[4]):
                total_inbox += len(files)
                Inbox_mail[path[3]] +=len(files)
        Total_data[path[3]] += len(files)
        num_files += len(files)
    if(count % 100 == 0):
        string += '*'
        print(string)
string +='] done!'

print(string)
print("*"*30)
### 2
print("People Targeted: "+ str(total_people))
print("*"*30)
### 3
print("Number of files: "+str(num_files))
print("*"*30)
### 4
print("Total amount of outgoing mail: " + str(total_sent))
print("*"*30)
### 5
print("Total amount of incoming mail: " + str(total_inbox))      
print("*"*30)
data = sorted(Total_data.items(),key = operator.itemgetter(1))
count = 0
### 6
print("5 Top people with the most files: ")

data.reverse()
for person in data :
    if(count > 9):
        break
    print(person[0]+ " : " + str(person[1])+" files")
    count += 1

print("*"*30)
print("Done!")
select * from words
where word like 'conflict' or word like 'of' or word like 'interest'
group by path
order by word, location;
