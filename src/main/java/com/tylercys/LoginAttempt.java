package com.tylercys;

public class LoginAttempt {
    //fields
    private String timestamp;
    private String account;
    private String ipAddress;
    private boolean success;

    //constructor
    public LoginAttempt(String timestamp, String account, String ipAddress, String success) {
        this.timestamp = timestamp;
        this.account = account;
        this.ipAddress = ipAddress;
    if (success.equals("SUCCESS"))
        this.success = true;
    else if (success.equals("FAILURE"))
        this.success = false;
    else
        System.err.println("Incorrect Formatting of result");
    }

    //getters
    public String getTimestamp() {
        return timestamp;
    }

    public String getAccount() {
        return account;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isSuccess() {
        return success;
    }

    //toString method
    public String toString() {
        return "LoginAttempt{" +
                "timestamp='" + timestamp + '\'' +
                ", account='" + account + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", success=" + success +
                '}';
    }
}
