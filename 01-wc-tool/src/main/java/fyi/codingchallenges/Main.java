package fyi.codingchallenges;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.concurrent.Callable;

@Command(name = "ccwc", version = "ccwc_v1.0.0", description = "A simple version of the wc utility")
public class Main implements Callable<Integer> {

    @Parameters(index = "0", description = "File to be processed", defaultValue = Parameters.NULL_VALUE)
    private File file;

    @Option(names = "-c", description = "outputs the number of bytes in a file")
    private boolean bytesOption;

    @Option(names = "-l", description = "outputs the number of lines in a file")
    private boolean linesOption;

    @Option(names = "-w", description = "outputs the number of words in a file")
    private boolean wordsOption;

    @Option(names = "-m", description = "outputs the number of characters in a file")
    private boolean charsOption;

    @Override
    public Integer call() throws Exception {
        StringBuilder result = new StringBuilder();
        boolean appendFileName = true;
        if(null == file){
            if (new Scanner(System.in).hasNext()) {
                file = File.createTempFile("ccwc",Long.toString(System.currentTimeMillis()));
                file.deleteOnExit();
                Files.copy(System.in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                appendFileName = false;
            } else {
                System.out.println("File Argument missing");
                return 5;
            }
        } else if (!file.exists()) {
            System.out.println("File does not exist");
            return 5;
        }

        Ccwc ccwc = new Ccwc(file);

        if(!bytesOption && !linesOption && !wordsOption && !charsOption){
            bytesOption = linesOption = wordsOption = true;
        }

        if (linesOption) {
            result.append(ccwc.calculateLines()).append("\t");
        }

        if (wordsOption) {
            result.append(ccwc.calculateWords()).append("\t");
        }

        if (charsOption) {
            result.append(ccwc.calculateChars()).append("\t");
        }

        if (bytesOption) {
            result.append(ccwc.calculateBytes()).append("\t");
        }

        if(appendFileName){
            result.append(file.getName());
        }
        System.out.println(result);

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}