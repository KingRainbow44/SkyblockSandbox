package tk.skyblocksandbox.skyblocksandbox;

import org.bukkit.configuration.file.FileConfiguration;

public final class Configuration {

    private final FileConfiguration configuration;

    public Configuration(FileConfiguration configuration) {
        this.configuration = configuration;

        databaseSetup();
        islandSetup();
    }

    public static String[] export() {
        return null;
    }

    /*
     * Database
     */
    public boolean databaseEnabled = false;
    public boolean noDatabaseConfig = true;

    private void databaseSetup() {
        if(!configuration.getString("databasePassword").matches("defaultPasswordYouShouldChange")) {
            noDatabaseConfig = false;
        }
    }

    /*
     * Islands
     */
    public boolean oneIslandServer = true;
    public boolean privateIslandServer = false;
    public boolean enableDefaultBuilding = false;
    public boolean dungeonCatacombsEnabled = false;

    // // Multi-Island Server Config \\ //
    public boolean dungeonHubEnabled = false;
    public boolean theParkEnabled = false;
    public boolean spidersDenEnabled = false;
    public boolean theEndEnabled = false;
    public boolean farmingIslandsEnabled = false;
    public boolean jerrysWorkshopEnabled = false;
    // \\ Multi-Island Server Config // //

    public void islandSetup() {
        oneIslandServer = configuration.getBoolean("oneIslandServer");
        privateIslandServer = configuration.getBoolean("privateIslandServer");
        enableDefaultBuilding = configuration.getBoolean("enableDefaultBuilding");
        dungeonCatacombsEnabled = configuration.getBoolean("dungeonCatacombsEnabled");

        dungeonHubEnabled = configuration.getBoolean("dungeonHubEnabled");
        theParkEnabled = configuration.getBoolean("theParkEnabled");
        spidersDenEnabled = configuration.getBoolean("spidersDenEnabled");
        theEndEnabled = configuration.getBoolean("theEndEnabled");
        farmingIslandsEnabled = configuration.getBoolean("farmingIslandsEnabled");
        jerrysWorkshopEnabled = configuration.getBoolean("jerrysWorkshopEnabled");
    }

    /*
     * Copyright-ed Content
     */
    public String hubWorld = "hub";

    public void contentSetup() {
        hubWorld = configuration.getString("hubWorld");
    }

}
