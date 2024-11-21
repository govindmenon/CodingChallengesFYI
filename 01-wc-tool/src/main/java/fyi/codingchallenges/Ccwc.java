package fyi.codingchallenges;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ccwc {

    private final Path path;

    public Ccwc(File file) {
        this.path = file.toPath();
    }

    public int calculateLines() throws IOException {
        return Files.readAllLines(path).size();
    }

    public int calculateBytes() throws IOException {
        return Files.readAllBytes(path).length;
    }

    public int calculateChars() throws IOException {
        return Files.readString(path).length();
    }

    public int calculateWords() throws IOException {
        return Files.readString(path).split("\\s+").length;
    }
}
