package com.home.lucchesi.homemade;

public class Device {
    public String id;
    public String ipAddress;
    public int port;

    public Device(String id, String ipAddress, int port) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Device(String explorationResult) {
        this.id = explorationResult.split("\r\n")[6].split(": ")[1];
        this.ipAddress = explorationResult.split("\r\n")[4].split("//")[1].split(":")[0];
        this.port = Integer.parseInt(explorationResult.split("\r\n")[4].split("//")[1].split(":")[1]);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void show() {
        System.out.println("ID : " + this.id);
        System.out.println("IP Address : " + this.ipAddress);
        System.out.println("Port : " + this.port);
        System.out.println();
    }
}
