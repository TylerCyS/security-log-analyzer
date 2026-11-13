package com.tylercys;

import java.util.ArrayList;
import java.util.Scanner;

public class LogAnalyzerCli {
    public void run(ArrayList<LoginAttempt> loginAttempts) {
        System.out.println("Security Log Analyzer\n"
                + "Loaded " + loginAttempts.size() + " login attempts.\n"
                + "Menu:\n"
                + "1. Show all login attempts\n"
                + "2. Show failed login attempts\n"
                + "3. Show login attempts from a specific IP address\n"
                + "4. Show login attempts for a specific account\n"
                + "5. Show repeated failed login attempts\n"
                + "6. Exit");
        Scanner scanner = new Scanner(System.in);
        int initialChoice;
        while (true) {
            System.out.println("Enter your choice (1-6): ");
            if (!scanner.hasNextLine()) {
                return;
            }
            try {
                initialChoice = Integer.parseInt(scanner.nextLine().trim());
                if (initialChoice >= 1 && initialChoice <= 6) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
            System.out.println("Please enter a whole number between 1 and 6.");
        }
        if (initialChoice == 1) {
            System.out.println("All login attempts:");
            for (LoginAttempt attempt : loginAttempts) {
                System.out.println(attempt);
            }
        }
        else if (initialChoice == 2) {
            System.out.println("Enter the account name (or enter to show all): ");
            String account = scanner.nextLine().trim();
            if (account.isEmpty()) {
                ArrayList<LoginAttempt> failedLogins = LoginAnalyzer.getFailedLogins(loginAttempts);
                System.out.println("All failed login attempts:");
                for (LoginAttempt attempt : failedLogins) {
                    System.out.println(attempt);
                }
                return;
            }
            ArrayList<LoginAttempt> failedLogins = LoginAnalyzer.getFailedLogins(loginAttempts, account);
            System.out.println("Failed login attempts for " + account + ":");
            for (LoginAttempt attempt : failedLogins) {
                System.out.println(attempt);
            }
        }
        else if (initialChoice == 3) {
            System.out.println("Enter the IP address: ");
            String ipAddress = scanner.nextLine().trim();
            ArrayList<LoginAttempt> loginsFromIP = LoginAnalyzer.getLoginsFromIP(loginAttempts, ipAddress);
            System.out.println("Login attempts from IP address " + ipAddress + ":");
            for (LoginAttempt attempt : loginsFromIP) {
                System.out.println(attempt);
            }
        }
        else if (initialChoice == 4) {
            System.out.println("Enter the account name: ");
            String account = scanner.nextLine().trim();
            ArrayList<LoginAttempt> loginsForAccount = LoginAnalyzer.getLoginsForAccount(loginAttempts, account);
            System.out.println("Login attempts for account " + account + ":");
            for (LoginAttempt attempt : loginsForAccount) {
                System.out.println(attempt);
            }
        }
        else if (initialChoice == 5) {
            System.out.println("Enter the account name (or enter to check all accounts): ");
            String account = scanner.nextLine().trim();
            System.out.println("Enter the threshold number of failed attempts: ");
            int threshold = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Enter the time window in minutes: ");
            int timeWindow = Integer.parseInt(scanner.nextLine().trim());

            // runs for all accounts if the user enters an empty string for the account name
            if (account.isEmpty()) {
                ArrayList<Double> repeatedFailedLogins = LoginAnalyzer.areRepeatedFailedLogins(loginAttempts, threshold, timeWindow);
                if (repeatedFailedLogins.isEmpty()) {
                    System.out.println("No repeated failed login attempts found.");
                } else {
                    System.out.println("Repeated failed login attempts found at the following times:");
                    for (Double time : repeatedFailedLogins) {
                        System.out.printf("%.2f minutes since midnight ", time);
                        System.out.println("(account: " + LoginAnalyzer.getLogin(loginAttempts, time) + ")");
                    }
                }
            }
             else {
                ArrayList<Double> repeatedFailedLogins = LoginAnalyzer.areRepeatedFailedLogins(loginAttempts, account, threshold, timeWindow);
                System.out.println("Repeated failed login attempts found for account " + account + " at the following times:");
                for (Double time : repeatedFailedLogins) {
                    System.out.printf("%.2f minutes since midnight\n", time);
                }
            }
        }
        else{
            System.out.println("Exiting program.");
        }
    }
}