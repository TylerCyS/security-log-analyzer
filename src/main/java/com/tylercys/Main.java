package com.tylercys;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //read file, initialize LoginAttempt objects, and store them in an ArrayList
        Path logFile = Path.of("sample.log");
        try {
            List<String> lines = Files.readAllLines(logFile);
            ArrayList<LoginAttempt> loginAttempts = new ArrayList<>(lines.size());
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    LoginAttempt attempt = new LoginAttempt(parts[0], parts[1].substring("account=".length()), parts[2].substring("source=".length()), parts[3].substring("result=".length()));
                    loginAttempts.add(attempt);
                } else {
                    System.err.println("Invalid log entry: " + line);
                }
            }
            new LogAnalyzerCli().run(loginAttempts);
        }
        catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }
}