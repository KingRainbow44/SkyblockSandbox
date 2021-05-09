package tk.skyblocksandbox.partyandfriends;

import tk.skyblocksandbox.partyandfriends.command.PartyCommand;
import tk.skyblocksandbox.partyandfriends.party.PartyManager;
import tk.skyblocksandbox.skyblocksandbox.module.SandboxModule;

public final class PartyModule extends SandboxModule {

    private static PartyModule instance;
    private static PartyManager partyManager;

    public PartyModule() {
        super("PartyModule", LOAD_ON_ENABLE, 0.1);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        partyManager = new PartyManager();

        registerCommand(new PartyCommand());

        getLogger().info("Enabled Party Module.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled Party Module.");
    }

    public static PartyModule getInstance() {
        return instance;
    }

    public static PartyManager getPartyManager() {
        return partyManager;
    }
}
