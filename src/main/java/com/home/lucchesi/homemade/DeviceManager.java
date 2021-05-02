package com.home.lucchesi.homemade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DeviceManager {
    public ArrayList<Device> deviceList;
    public static int timeToCheckMillis = 2000;

    public DeviceManager() {
        this.deviceList = new ArrayList<>();
        createDeviceList();
        //setDeviceList();
    }

    public List<Device> explore() {
        try {
            //Handle interface and port
            NetworkInterface nif = NetworkInterface.getByName("wlan2");
            Enumeration<InetAddress> nifAddreses = nif.getInetAddresses();
            InetAddress internalWifiAddress = nifAddreses.nextElement();
            MulticastSocket ms = new MulticastSocket(52000);
            ms.setInterface(internalWifiAddress);
            ms.setSoTimeout(2000);

            //Prepare data packet
            byte[] sendData;
            byte[] receiveData = new byte[2048];
            String MSEARCH = "M-SEARCH * HTTP/1.1\r\nHOST: 239.255.255.250:1982\r\nMAN: \"ssdp:discover\"\r\nST: wifi_bulb";
            sendData = MSEARCH.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("239.255.255.250"), 1982);

            //Send and receive packet
            ms.send(sendPacket);

            long startTime = System.currentTimeMillis();
            long endTime = startTime + timeToCheckMillis;
            ArrayList<Device> result = new ArrayList<>();
            while (System.currentTimeMillis() < endTime) {
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    ms.setSoTimeout(2000);
                    ms.receive(receivePacket);
                    String response = new String(receivePacket.getData());
                    result.add(new Device(response));
                    Thread.sleep(100);
                } catch (SocketTimeoutException e) {
                    continue;
                }
            }
            ms.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public void createDeviceList() {
        System.out.println("Explore");
        try {
            for (Device device : explore()) {
                addIfNotExists(device);
            }
        } catch (Exception e) {
            System.out.println("Error during DeviceManager exploration.");
        }
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
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(device.ipAddress, device.port);
            Socket socket = new Socket();
            socket.connect(inetSocketAddress, 1000);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socketWriter.write(message);
            socketWriter.flush();
            socketReader.readLine();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

}
