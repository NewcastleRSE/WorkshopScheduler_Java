package com.jannetta.workshopscheduler.controller;

import java.util.Properties;

public class Globals {

    Properties properties;
    String configDirectory;

    public Globals() {
        this.properties = new Properties();
        configDirectory = System.getProperty("User.home");
    }

    public Globals(Properties properties, String configDirectory) {
        this.properties = properties;
        this.configDirectory = configDirectory;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getConfigDirectory() {
        return configDirectory;
    }

    public void setConfigDirectory(String configDirectory) {
        this.configDirectory = configDirectory;
    }
}
