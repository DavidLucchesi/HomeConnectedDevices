package com.home.lucchesi.homemade;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HomemadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomemadeApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Device Yeelight Manager - Start");
            DeviceManager deviceManager = new DeviceManager();
            deviceManager.show();
            CommandManager commandManager = new CommandManager(deviceManager);
            commandManager.main();
            System.out.println("Device Yeelight Manager - Stop");
        };
    }

}
