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
                    LoginAttempt attempt = new LoginAttempt(parts[0], parts[1].substring("account=".length()), parts[2].substring("ip=".length()), parts[3].substring("result=".length()));
                    loginAttempts.add(attempt);
                } else {
                    System.err.println("Invalid log entry: " + line);
                }
            }

            //tests
            ArrayList<LoginAttempt> failedLogins = LoginAnalyzer.getFailedLogins(loginAttempts);
            System.out.println(failedLogins.size() + " failed login attempts found.");
            ArrayList<LoginAttempt> failedLoginsForAccount = LoginAnalyzer.getFailedLogins(loginAttempts, "admin");
            System.out.println(failedLoginsForAccount.size() + " failed login attempts found for admin.");
            ArrayList<LoginAttempt> loginsFromIP = LoginAnalyzer.getLoginsFromIP(loginAttempts, "192.168.1.1");
            System.out.println(loginsFromIP.size() + " login attempts found from IP 192.168.1.1.");
            ArrayList<LoginAttempt> loginsForAccount = LoginAnalyzer.getLoginsForAccount(loginAttempts, "admin");
            System.out.println(loginsForAccount.size() + " login attempts found for admin.");
        } catch (IOException e) {
            System.err.println("Could not read the log file: " + e.getMessage());
        }
    }
}