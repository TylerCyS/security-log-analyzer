package com.tylercys;
import java.util.ArrayList;

public class LoginAnalyzer {
    private LoginAnalyzer(){}

    //returns an ArrayList of failed login attempts from the given ArrayList of all login attempts
    public static ArrayList<LoginAttempt> getFailedLogins(ArrayList<LoginAttempt> allLogins){
    ArrayList<LoginAttempt> failedLogins = new ArrayList<>();
        for (LoginAttempt attempt : allLogins){
            if (!attempt.isSuccess())
                failedLogins.add(attempt);
        }
        return failedLogins;
    }
    //returns an ArrayList of failed login attempts for a specific account from the given ArrayList of all login attempts
    public static ArrayList<LoginAttempt> getFailedLogins(ArrayList<LoginAttempt> allLogins, String account){
    ArrayList<LoginAttempt> failedLogins = new ArrayList<>();
        for (LoginAttempt attempt : allLogins){
            if (!attempt.isSuccess() && attempt.getAccount().equals(account))
                failedLogins.add(attempt);
        }
        return failedLogins;
    }
    //returns an ArrayList of login attempts from a specific IP address from the given ArrayList of all login attempts
    public static ArrayList<LoginAttempt> getLoginsFromIP(ArrayList<LoginAttempt> allLogins, String ipAddress){
        ArrayList<LoginAttempt> loginsFromIP = new ArrayList<>();
        for (LoginAttempt attempt : allLogins){
            if (attempt.getIpAddress().equals(ipAddress))
                loginsFromIP.add(attempt);
        }
        return loginsFromIP;
    }
    //returns an ArrayList of login attempts for a specific account from the given ArrayList of all login attempts
    public static ArrayList<LoginAttempt> getLoginsForAccount(ArrayList<LoginAttempt> allLogins, String account){
        ArrayList<LoginAttempt> loginsForAccount = new ArrayList<>();
        for (LoginAttempt attempt : allLogins){
            if (attempt.getAccount().equals(account))
                loginsForAccount.add(attempt);
        }
        return loginsForAccount;
    }

    //return the acccount associated with a specific login time (in minutes past midnight)
    public static String getLogin(ArrayList<LoginAttempt> allLogins, double time) {
        for (LoginAttempt attempt : allLogins) {
            if (minutesSinceMidnight(attempt.getTimestamp()) == time) {
                return attempt.getAccount();
            }
        }
        return null; // Return null if no matching login attempt is found
    }

    //returns an ArrayList of failed login attempts for a specific account that have occurred more than the threshold number of times within the timeWindow (in minutes)
    public static ArrayList<Double> areRepeatedFailedLogins(ArrayList<LoginAttempt> allLogins, String account, int threshold, int timeWindow) {
        ArrayList<LoginAttempt> failedLoginsForAccount = getFailedLogins(allLogins, account);
        ArrayList<Double> failTimeframes = new ArrayList<>();
        for (int i = 0; i < failedLoginsForAccount.size(); i++) {

            String currentTimestamp = failedLoginsForAccount.get(i).getTimestamp();
            double currentTime = minutesSinceMidnight(currentTimestamp);
            int offset = 1;
            int failCount = 1; // Count the current failed login attempt
            while (i + offset < failedLoginsForAccount.size() && minutesSinceMidnight(failedLoginsForAccount.get(i + offset).getTimestamp())
            <= currentTime + timeWindow) {
                if (!failedLoginsForAccount.get(i+offset).isSuccess()){
                    failCount++;
                }
                offset++;
            }
            if (failCount >= threshold){
                failTimeframes.add(currentTime);
            }
        }
        return failTimeframes;
    }

    //returns an ArrayList of failed login attempts for all accounts that have occurred more than the threshold number of times within the timeWindow (in minutes)
    public static ArrayList<Double> areRepeatedFailedLogins(ArrayList<LoginAttempt> allLogins, int threshold, int timeWindow) {
        ArrayList<LoginAttempt> failedLogins = getFailedLogins(allLogins);
        ArrayList<Double> failTimeframes = new ArrayList<>();

        // Create a list of unique accounts from the failed logins
        ArrayList<String> totalAccounts = new ArrayList<>();
        for (LoginAttempt attempt : failedLogins) {
            if (!totalAccounts.contains(attempt.getAccount())) {
                totalAccounts.add(attempt.getAccount());
            }
        }


        for (int j = 0; j < totalAccounts.size(); j++){
            for (int i = 0; i < failedLogins.size(); i++) {
                if (!failedLogins.get(i).getAccount().equals(totalAccounts.get(j))) {
                    continue;
                }

                String currentTimestamp = failedLogins.get(i).getTimestamp();
                double currentTime = minutesSinceMidnight(currentTimestamp);
                int offset = 1;
                int failCount = 1; // Count the current failed login attempt
                while (i + offset < failedLogins.size() && minutesSinceMidnight(failedLogins.get(i + offset).getTimestamp()) <= currentTime + timeWindow) {
                    if (!failedLogins.get(i + offset).getAccount().equals(totalAccounts.get(j))){
                        offset++;
                        continue;
                    }
                    failCount++;
                    offset++;
                }
                if (failCount >= threshold){
                    failTimeframes.add(currentTime);
                }
            }
        }
        return failTimeframes;
    }

    //helper method to convert timestamp to minutes since midnight
    private static double minutesSinceMidnight(String timestamp) {
        int hours = Integer.parseInt(timestamp.substring(11, 13));
        int minutes = Integer.parseInt(timestamp.substring(14, 16));
        int seconds = Integer.parseInt(timestamp.substring(17, 19));

        return (hours * 3600 + minutes * 60 + seconds)/60.0;
    }
}