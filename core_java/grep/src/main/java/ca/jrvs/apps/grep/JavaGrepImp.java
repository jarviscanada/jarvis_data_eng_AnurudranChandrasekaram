package ca.jrvs.apps.grep;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public void process() throws IOException {
        String rootDir = this.getRootPath();
        List<File> files = this.listFiles(rootDir);
        List<String> matchedLines = new ArrayList<>();
        for (File file : files) {
            List<String> lines = this.readLines(file);
            for(String line : lines) {
                if (this.containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }
        this.writeToFile(matchedLines);

    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files;
        try {
            Stream<Path> paths = Files.walk(Paths.get(rootDir));
            files = paths.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();
        FileReader fr = null;
        try {
            fr = new FileReader(inputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = new BufferedReader(fr);
        try {
            String line;
            while ((line=br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            br.close();
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        String regex = this.getRegex();
        return Pattern.matches(regex, line);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        String outFile = this.getOutFile();
        FileWriter fw = new FileWriter(outFile);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String line : lines) {
            bw.write(line + "\n");
        }
        bw.close();

    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
