package com.tylercys;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Read only the fields needed to construct login attempts.
        Path logFile = Path.of("log.json");
        Gson gson = new GsonBuilder().setStrictness(com.google.gson.Strictness.STRICT).create();
        try (Reader reader = Files.newBufferedReader(logFile)) {
            LogEntry[] entries = gson.fromJson(reader, LogEntry[].class);
            if (entries == null) {
                throw new JsonParseException("Expected an array of login attempts");
            }
            ArrayList<LoginAttempt> loginAttempts = new ArrayList<>(entries.length);
            for (LogEntry entry : entries) {
                if (entry == null || entry.timestamp == null || entry.account == null
                        || entry.source == null || !("SUCCESS".equals(entry.result)
                        || "FAILURE".equals(entry.result))) {
                    throw new JsonParseException("Invalid login entry: timestamp, account, source, and a SUCCESS/FAILURE result are required");
                }
                loginAttempts.add(new LoginAttempt(entry.timestamp, entry.account, entry.source, entry.result));
            }
            new LogAnalyzerCli().run(loginAttempts);
        }
        catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
        catch (JsonParseException e) {
            System.err.println("Error parsing log file: " + e.getMessage());
        }
    }

    private static class LogEntry {
        String timestamp;
        String account;
        String source;
        String result;
    }
}
