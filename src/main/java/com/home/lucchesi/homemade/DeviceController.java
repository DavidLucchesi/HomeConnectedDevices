package com.home.lucchesi.homemade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
    public DeviceManager deviceManager = new DeviceManager();
    public CommandManager commandManager = new CommandManager(deviceManager);

    @GetMapping("/device")
    public ResponseEntity<Device> device(@RequestParam String id) {
        for (Device device : deviceManager.deviceList) {
            if (device.id.equalsIgnoreCase(id)) {
                return ResponseEntity.ok().body(device);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/poweron")
    public ResponseEntity powerOn() {
        commandManager.powerOnAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/poweroff")
    public ResponseEntity powerOff() {
        commandManager.powerOffAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/testcolors")
    public ResponseEntity testColors() {
        commandManager.testColors();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/blinkgreen")
    public ResponseEntity blinkGreen() {
        try {
            commandManager.powerOnAll();
            commandManager.setAllColor(Color.GREEN);
            Thread.sleep(600);
            commandManager.powerOffAll();
            Thread.sleep(800);
            commandManager.powerOnAll();
            Thread.sleep(1000);
            commandManager.powerOffAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error during blinkGreen()");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/blinkred")
    public ResponseEntity blinkRed() {
        try {
            commandManager.powerOnAll();
            commandManager.setAllColor(Color.RED);
            Thread.sleep(600);
            commandManager.powerOffAll();
            Thread.sleep(800);
            commandManager.powerOnAll();
            Thread.sleep(1000);
            commandManager.powerOffAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error during blinkRed()");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ambiance")
    public ResponseEntity ambiance() {
        commandManager.powerOnAll();
        commandManager.setAllColor(Color.BLUE);
        return ResponseEntity.ok().build();
    }


}
