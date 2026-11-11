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
}