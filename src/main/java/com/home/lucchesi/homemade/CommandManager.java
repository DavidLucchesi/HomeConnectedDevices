package com.home.lucchesi.homemade;

public class CommandManager {
    public DeviceManager deviceManager;

    public CommandManager(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void main() {
        testColors();
    }

    public void testColors() {
        try {
            powerOnAll();
            for (Color c : Color.values()) {
                System.out.println(c.getColor());
                setAllColor(c);
                Thread.sleep(1000);
            }
            powerOffAll();
        } catch (Exception e) {
            System.out.println("An error occured during testColors.");
        }
    }

    public void toggleLight(int deviceNumber) {
        deviceManager.sendOneDevice(Command.TOGGLE.getValue(), deviceNumber);
    }

    public void toggleAllLights() {
        deviceManager.sendAllDevices(Command.TOGGLE.getValue());
    }

    public void setColor(int deviceNumber, Color color) {
        deviceManager.sendOneDevice(Command.COLOR.getValue().replaceAll("COLOR_VALUE", String.valueOf(color.getValue())), deviceNumber);
    }

    public void setAllColor(Color color) {
        deviceManager.sendAllDevices(Command.COLOR.getValue().replaceAll("COLOR_VALUE", String.valueOf(color.getValue())));
    }

    public void powerOn(int deviceNumber) {
        deviceManager.sendOneDevice(Command.POWER_ON.getValue(), deviceNumber);
    }

    public void powerOnAll() {
        deviceManager.sendAllDevices(Command.POWER_ON.getValue());
    }

    public void powerOff(int deviceNumber) {
        deviceManager.sendOneDevice(Command.POWER_OFF.getValue(), deviceNumber);
    }

    public void powerOffAll() {
        deviceManager.sendAllDevices(Command.POWER_OFF.getValue());
    }
}
