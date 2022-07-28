# Introduction
This app mimics the grep command functionality. It takes a directory or file and traverses each file and each line in the file to find a pattern that matches the regex expression given. This app is a Java Maven project that uses Java's lambda and stream APIs. This project was written using intellij IDE and is contained in a docker image. For storage of the code we used GitHub. 

# Quick Start
```
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp {regex} {rootPath} {outFile}
- regex: a special text string for describing a search pattern
- rootPath: root directory path
- outFile: output file name

Example:
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data ./out/grep.txt

Can also use Docker example:

docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
anurudranchandrasekaram/grep {regex} {rootPath} {outFile}
```


# Implementation
## Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```
This app goes through the directory to find every file in it and reads each line in a file to find any that matches the regex expression. At the end it outputs the lines that were found that matches the regex expression.
## Performance Issue
As the app reads a file that have large amounts of data in it, it can cause memory issues and get a memory leak in the JVM heap. A fix for this is allocate more memory in the heap so the app is able to process large amounts of data.

# Test
To get the sample data I used 
```
egrep -r {regex} {rootPath} > {outFile}
```
Once I got the results I compared it with the results I received from the app I created and see if they match.

# Deployment
A dockerfile is created to dockerize the grep app. The docker image was pushed to the docker hub and you can pull the docker image which is called anurudranchandrasekaram/grep

# Improvement
1. Be able to say the file name and line number where the matched line was found in
2. Create automatic test cases
3. Being able to identify duplicate files if there are any in the root dir and not having to repeatedly find matched lines for the duplicated files.
