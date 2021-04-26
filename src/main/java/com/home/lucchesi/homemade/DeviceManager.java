package com.home.lucchesi.homemade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;

public class DeviceManager {
    public ArrayList<Device> deviceList;
    public static int maxTries = 10;

    public DeviceManager() {
        this.deviceList = new ArrayList<>();
        createDeviceList();
        //setDeviceList();
    }

    public Device explore() {
        try {
            byte[] sendData;
            byte[] receiveData = new byte[1024];
            String MSEARCH = "M-SEARCH * HTTP/1.1\r\nHOST: 239.255.255.250:1982\r\nMAN: \"ssdp:discover\"\r\nST: wifi_bulb";
            sendData = MSEARCH.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("239.255.255.250"), 1982);
            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String response = new String(receivePacket.getData());
            clientSocket.close();
            Device result = new Device(response);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public void createDeviceList() {
        System.out.print("Explore ");
        for (int i = 0; i < maxTries; i++) {
            try {
                System.out.print(".");
                addIfNotExists(explore());
            } catch (Exception e) {
                System.out.println("Error during DeviceManager exploration.");
            }
        }
        System.out.println();
    }

    public void setDeviceList() {
        deviceList.add((new Device("0x0000000011271eb5", "192.168.1.15", 55443)));
        deviceList.add((new Device("0x0000000007fc8f67", "192.168.1.18", 55443)));
    }

    public void show() {
        for (Device d : this.deviceList) {
            d.show();
        }
    }

    public boolean addIfNotExists(Device device) {
        boolean toAdd = true;
        for (Device d : deviceList) {
            if (d.id.equalsIgnoreCase(device.id)) {
                toAdd = false;
            }
        }
        if (toAdd) {
            deviceList.add(device);
        }
        return toAdd;
    }

    public void sendAllDevices(String message) {
        for (int i = 0; i < deviceList.size(); i++) {
            sendOneDevice(message, i);
        }
    }

    public void sendOneDevice(String message, int deviceNumber) {
        Device device = deviceList.get(deviceNumber);
        //System.out.println("Test for : " + device.id + " ; " + device.ipAddress);
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(device.ipAddress, device.port);
            Socket socket = new Socket();
            socket.connect(inetSocketAddress, 1000);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //System.out.println(message);
            socketWriter.write(message);
            socketWriter.flush();
            String result = socketReader.readLine();
            socket.close();
            //System.out.println("Data received : " + result);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

}
