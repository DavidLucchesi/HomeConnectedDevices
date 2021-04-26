package com.home.lucchesi.homemade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
    public DeviceManager deviceManager = new DeviceManager();

    @GetMapping("/device")
    public Device device(@RequestParam String id){
        for (Device device : deviceManager.deviceList) {
            if (device.id.equalsIgnoreCase(id)){
                return device;
            }
        }
        return null;
    }
}
