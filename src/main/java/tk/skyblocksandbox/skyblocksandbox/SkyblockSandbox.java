package tk.skyblocksandbox.skyblocksandbox;

import com.kingrainbow44.customplayer.PlayerAPI;
import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bstats.bukkit.Metrics;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import tk.skyblocksandbox.dungeonsandbox.DungeonsModule;
import tk.skyblocksandbox.partyandfriends.PartyModule;
import tk.skyblocksandbox.skyblocksandbox.command.admin.ItemCommand;
import tk.skyblocksandbox.skyblocksandbox.command.admin.SummonCommand;
import tk.skyblocksandbox.skyblocksandbox.command.all.DebugCommand;
import tk.skyblocksandbox.skyblocksandbox.command.all.SandboxCommand;
import tk.skyblocksandbox.skyblocksandbox.listener.*;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModuleManager;
import tk.skyblocksandbox.skyblocksandbox.npc.traits.SkyblockEntityTrait;
import tk.skyblocksandbox.skyblocksandbox.runnable.PlayerRunnable;
import tk.skyblocksandbox.skyblocksandbox.runnable.RegionCheck;

public final class SkyblockSandbox extends JavaPlugin {

    private final static String version = "v0.3-development";

    /*
     * Skyblock Sandbox To-Do List:
     * - Player Storage (ender chest, backpacks)
     * - Custom Enchants (e-table, anvil, enchantments)
     * - Auctions (display, menu)
     * - Bazaar (menu, system)
     * - Item Reforges
     * - Custom Mining System (pickaxes, drills, axes, etc.)
     * - Real-time updating stats system
     *  - While it isn't needed (we have getFinal_STAT_()) it would be nice to have for efficiency.
     */

    /*
     * Working on:
     * - Dungeons (system, maps)
     * - Skyblock Menu
     * - Custom Item Creator (system, menu)
     * - Regions
     */

    /*
     * Done:
     * - Scoreboard (islands, coins, bits)
     * - Player Stats System
     * - Player Data Sync (stats, storage system)
     * - Custom Mobs System (mobs, stats, bosses)
     * - Module Loader (for multi server)
     * - Custom Items System (items, stats, lore generator)
     * - Custom Damage System (damage indicators & the damage calculation)
     * - Party System
     * - Custom Commands System
     * - New (NBT Based) custom item system
     * - Vanilla Items -> Custom Items System
     */

    private static PlayerAPI api;
    private static SkyblockSandbox instance;
    private static SkyblockManager management;
    private static SandboxModuleManager moduleManager;

    private static Configuration configuration;

    @Override
    public void onLoad() {
        instance = this;

        moduleManager = new SandboxModuleManager();
        moduleManager.addModule(new DungeonsModule());
        moduleManager.addModule(new PartyModule());

        moduleManager.callModules(SandboxModule.LOAD_ON_PLUGIN);
    }

    @Override
    public void onEnable() {
        api = new PlayerAPI(this);
        api.initialize();

        management = new SkyblockManager();

        initializeConfig();
        initializeDatabase();
        initializeDependencies();
        initializeWorlds();
        bukkitStats();

        registerListener(new ItemListener());
        registerListener(new PlayerListener());
        registerListener(new WorldListener());
        registerListener(new DamageListener());
        registerListener(new InventoryListener());

        registerCommand(new ItemCommand());
        registerCommand(new SandboxCommand());
        registerCommand(new DebugCommand());
        registerCommand(new SummonCommand());

        registerRunnable(new PlayerRunnable(), 1L);
        registerRunnable(new RegionCheck(), 1L);

        moduleManager.enableModules();

        getLogger().info("Enabled Skyblock Sandbox.");
    }

    @Override
    public void onDisable() {
        moduleManager.disableModules();
        getLogger().info("Disabled Skyblock Sandbox.");
    }

    /*
     * Private Methods
     */

    private void initializeConfig() {
        this.saveDefaultConfig();
        configuration = new Configuration(getConfig());
    }

    private void initializeDatabase() {
        if(configuration.noDatabaseConfig) return;

        MySQL.connect();
        if(!MySQL.isConnected()) {
            getLogger().severe("Unable to connect to a database. Player data saving WILL NOT WORK.");
            getLogger().severe("Along with data saving not working, you will NOT get support for not having a MySQL database connected.");
            getLogger().severe("A MySQL database should be connected or most (if not all) features will BREAK (this mainly means stats).");
            configuration.databaseEnabled = false;
            return;
        }

        // Setup Tables
        if(!SQL.tableExists("players")) SQL.createTable("players", "`uuid` TEXT NOT NULL , `data` LONGTEXT NOT NULL");
    }

    private void bukkitStats() {
        Metrics metrics = new Metrics(this, 11210);
    }

    private void initializeDependencies() {
        Plugin protocolLib = getServer().getPluginManager().getPlugin("ProtocolLib");
        Plugin noteblockApi = getServer().getPluginManager().getPlugin("NoteBlockAPI");
        Plugin worldEdit = getServer().getPluginManager().getPlugin("FastAsyncWorldEdit");
        Plugin citizens2 = getServer().getPluginManager().getPlugin("Citizens");

        if(protocolLib == null || noteblockApi == null || worldEdit == null) {
            getLogger().severe("Either ProtocolLib, NoteBlockAPI, or WorldEdit has NOT been loaded. Please install the plugin(s) ASAP.");
            getServer().getPluginManager().disablePlugin(this);
        }

        if(citizens2 == null) {
            getLogger().severe("Citizens has NOT been loaded. Please install the plugin(s) ASAP.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(SkyblockEntityTrait.class).withName("SkyblockEntity"));
        }
    }

    private void initializeWorlds() {
        Configuration configuration = getConfiguration();

        new WorldCreator(configuration.hubWorld).createWorld();
        new WorldCreator(configuration.dungeonHubWorld).createWorld();
    }

    /*
     * Register Methods
     */

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerCommand(Command command) {
        SimpleCommandMap simpleCommandMap = ((CraftServer) getServer()).getCommandMap();
        simpleCommandMap.register(getDescription().getName(), command);
    }

    private void registerRunnable(Runnable runnable, long tps) {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, runnable, 0L, tps);
    }

    /*
     * Static Get Methods
     */

    public static PlayerAPI getApi() {
        return api;
    }

    public static SkyblockSandbox getInstance() {
        return instance;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static String getVersion() {
        return version;
    }

    public static SkyblockManager getManagement() {
        return management;
    }
}
