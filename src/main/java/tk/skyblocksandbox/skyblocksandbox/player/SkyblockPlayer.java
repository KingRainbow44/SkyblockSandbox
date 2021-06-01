package tk.skyblocksandbox.skyblocksandbox.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.kingrainbow44.customplayer.player.CustomPlayer;
import com.kingrainbow44.customplayer.player.ICustomPlayer;
import me.vagdedes.mysql.database.SQL;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.partyandfriends.PartyModule;
import tk.skyblocksandbox.partyandfriends.party.PartyManager;
import tk.skyblocksandbox.permitable.rank.PermitableRank;
import tk.skyblocksandbox.permitable.util.scoreboard.TagChanger;
import tk.skyblocksandbox.permitable.util.scoreboard.TeamAction;
import tk.skyblocksandbox.skyblocksandbox.area.SkyblockLocations;
import tk.skyblocksandbox.partyandfriends.party.PartyInstance;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.*;
import tk.skyblocksandbox.skyblocksandbox.scoreboard.HubScoreboard;
import tk.skyblocksandbox.skyblocksandbox.scoreboard.SkyblockScoreboard;
import tk.skyblocksandbox.skyblocksandbox.util.Music;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;
import tk.skyblocksandbox.skyblocksandbox.util.ram.FieldInstance;

import java.lang.reflect.InvocationTargetException;

public class SkyblockPlayer extends CustomPlayer implements ICustomPlayer {

    /*
     * Constants
     */
    public static final int SUBLOC_NONE = -1;
    public static final int SUBLOC_VILLAGE = 0;
    public static final int SUBLOC_DUNGEON_HUB = 1;

    private final SkyblockPlayerData playerData;
    private final SkyblockScoreboard scoreboard;

    private final PlayerConnection playerConnection;

    private PartyInstance currentParty = null;

    private final FieldInstance fieldInstance = new FieldInstance();

    /*
     * Private Methods/Data Methods
     */

    public SkyblockPlayer(Player player) {
        super(player);

        playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        playerData = new SkyblockPlayerData(this);
        scoreboard = new HubScoreboard(this);
        if(SkyblockSandbox.getConfiguration().databaseEnabled) {
            if (!SQL.exists("uuid", player.getUniqueId().toString(), "players")) {
                SQL.insertData("uuid, data", "'" + player.getUniqueId() + "'" + ", '" + "{}" + "'", "players");
            } else if (!SQL.tableExists("players")) {
                throw new NullPointerException("SQL Table 'players' does not exist.");
            }

            Object existingData = SQL.get("data", "uuid", "=", player.getUniqueId().toString(), "players");

            if(existingData != null) {
                getPlayerData().importData(existingData);
            }
        }
    }

    public void onRegister() {
        getBukkitPlayer().teleport(new Location(Bukkit.getWorld(SkyblockSandbox.getConfiguration().hubWorld), -3, 70, -70, 180, 0));

        SandboxItem sbMenu = (SandboxItem) SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(SkyblockItemIds.SKYBLOCK_MENU);
        getBukkitPlayer().getInventory().setItem(8, sbMenu.create());

        new BukkitRunnable() {
            @Override
            public void run() {
                updateNameTag();
            }
        }.runTaskLater(SkyblockSandbox.getInstance(), 50L);
    }

    public void onUnregister() {
        String uuid = getBukkitPlayer().getUniqueId().toString();
        if(SkyblockSandbox.getConfiguration().databaseEnabled) {
            if (!SQL.exists("uuid", uuid, "players")) {
                SQL.insertData("uuid, data", "'" + uuid + "'" + ", '" + getPlayerData().exportData() + "'", "players");
            } else if (!SQL.tableExists("players")) {
                throw new NullPointerException("SQL Table 'players' does not exist.");
            }

            SQL.set("data", getPlayerData().exportData(), "uuid", "=", uuid, "players");
        }

        if(!getPlayerData().playingSong.matches("none")) {
            Music.cancelMusic(this);
        }

        if(inParty()) {
            if(getPartyPermissions() == 2) {
                PartyManager partyManager = PartyModule.getPartyManager();
                partyManager.disbandParty(this);
            } else {
                PartyInstance partyInstance = getCurrentParty();
                partyInstance.removeMember(this);
            }
        }
    }

    /*
     * Set Methods
     */

    public void setCurrentParty(Object partyInstance) {
        if(partyInstance instanceof PartyInstance) {
            currentParty = (PartyInstance) partyInstance;
        } else {
            currentParty = null;
        }
    }

    public void setPartyPermissions(int partyPermission) {
        if(!inParty()) return;

        getCurrentParty().setPermissions(this, partyPermission);
    }

    /*
     * Get Methods
     */

    /**
     * A convenient way of accessing and writing data to a player without creating more variables.
     * @return The FieldInstance. The player ram.
     */
    public FieldInstance getPlayerRam() {
        return fieldInstance;
    }

    public PartyInstance getCurrentParty() {
        return currentParty;
    }

    public boolean inParty() {
        return currentParty != null;
    }

    public int getPartyPermissions() {
        if(!inParty()) return 0;

        return getCurrentParty().getPlayerPermissions(this);
    }

    public Location getSpawn() {
        // TODO: Refactor with Public Islands.
        switch(getPlayerData().location) {
            default:
            case SUBLOC_VILLAGE:
                return new Location(Bukkit.getWorld(SkyblockSandbox.getConfiguration().hubWorld), -3.5, 70, -70.5, 180, 0);
            case SUBLOC_DUNGEON_HUB:
                return new Location(Bukkit.getWorld(SkyblockSandbox.getConfiguration().dungeonHubWorld), -31.5, 121, 0.5, 90, 0);
        }
    }

    public int getCoins() {
        return getPlayerData().coins;
    }

    public int getBits() {
        return getPlayerData().bits;
    }

    public SkyblockPlayerData getPlayerData() {
        return playerData;
    }

    public Object getLocation(boolean asString) {
        if(!asString) return getPlayerData().location;

        switch(getPlayerData().location) {
            default:
            case SUBLOC_NONE:
                return Utility.colorize("&7None");
            case SUBLOC_VILLAGE: // village
                return Utility.colorize("&bVillage");
            case SUBLOC_DUNGEON_HUB:
                return Utility.colorize("&cDungeon Hub");
        }
    }

    public SandboxItem getItemInHand(boolean mainHand) {
        ItemStack bukkitItem = mainHand ? getBukkitPlayer().getInventory().getItemInMainHand() : getBukkitPlayer().getInventory().getItemInOffHand();
        return SandboxItemStack.toSandboxItem(bukkitItem);
    }

    public SkyblockScoreboard getScoreboard() {
        return scoreboard;
    }

    public void kill() {
        getPlayerData().coins /= 2;

        getPlayerData().currentHealth = getPlayerData().getFinalMaxHealth();
        getPlayerData().currentMana = getPlayerData().getFinalIntelligence() + 100;
        updateHud();
        getScoreboard().updateScoreboard();

        getBukkitPlayer().teleport(getSpawn());
        for(PotionEffect effect : getBukkitPlayer().getActivePotionEffects()) { // TODO: Implement custom effects.
            getBukkitPlayer().removePotionEffect(effect.getType());
        }

        sendMessage("&cYou died and lost " + getPlayerData().coins + " coins!");
        for(ICustomPlayer cPlayer : SkyblockSandbox.getApi().getPlayerManager().getPlayers().values()) {
            if(cPlayer.equals(this)) return;
            cPlayer.sendMessage("&7" + this.getBukkitPlayer().getDisplayName() + " died.");
        }
    }

    public void updateNameTag() {
        TagChanger.changePlayerName(
                getBukkitPlayer(),
                PermitableRank.getRankByEnum(getPlayerData().rank).getPrefix(), "", TeamAction.CREATE
        );
    }

    /*
     * Private
     */

    private String getHudHealth() {

        if(getPlayerData().absorptionHealth > 0) {
            return "&6" + (getPlayerData().currentHealth + getPlayerData().absorptionHealth) + "/" + playerData.getFinalMaxHealth() + "❤";
        }

        return "&c" + getPlayerData().currentHealth + "/" + playerData.getFinalMaxHealth() + "❤";
    }

    /*
     * General Methods
     */

    public void teleportTo(String skyblockLocation) {
        switch(skyblockLocation) {
            default:
            case SkyblockLocations.MAIN_HUB:
                getBukkitPlayer().teleport(new Location(Bukkit.getWorld(SkyblockSandbox.getConfiguration().hubWorld), -3, 70, -70, 180, 0));
                return;
            case SkyblockLocations.DUNGEON_HUB:
                getBukkitPlayer().teleport(new Location(Bukkit.getWorld(SkyblockSandbox.getConfiguration().dungeonHubWorld), -31, 121, 0, 90, 0));
                return;
        }
    }

    public String getName() {
        return getBukkitPlayer().getDisplayName();
    }

    public void updateHud() {
        if(playerData.getFinalDefense() > 0) {
            Utility.sendActionBarMessage(getBukkitPlayer(), Utility.colorize(
                    getHudHealth() + "   " + "&a" + Math.round(playerData.getFinalDefense()) + "❈ Defense" + "   " + "&b" + getPlayerData().currentMana + "/" + playerData.getFinalIntelligence() + "✎ Mana"
            ));
        } else {
            Utility.sendActionBarMessage(getBukkitPlayer(), Utility.colorize(
                    getHudHealth() + "   " + "&b" + getPlayerData().currentMana + "/" + playerData.getFinalIntelligence() + "✎ Mana"
            ));
        }
    }

    public boolean manaCheck(int manaCost, String usageMessage) {
        if(getPlayerData().currentMana >= manaCost) {
            if(!getPlayerData().infiniteMana) getPlayerData().currentMana -= manaCost;

            Utility.sendActionBarMessage(getBukkitPlayer(), Utility.colorize(
                    getHudHealth() + "   " + "&b-" + manaCost + " Mana (&6" + usageMessage + "&b)" + "   " + "&b" + getPlayerData().currentMana + "/" + playerData.getFinalIntelligence() + "✎ Mana"
            ), 1, SkyblockSandbox.getInstance());

            return true;
        } else {
            sendMessage("&cYou do not have enough mana!");
            return false;
        }
    }

    /**
     * Fakes the red effect on an entity using ProtocolLib.
     */
    public void hurt() {
        getBukkitPlayer().getWorld().playSound(
                getBukkitPlayer().getLocation(),
                Sound.ENTITY_PLAYER_HURT,
                1, 1
        );

        PacketContainer entityStatus = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);

        entityStatus.getIntegers().write(0, getBukkitPlayer().getEntityId());
        entityStatus.getBytes().write(0, (byte) 2);

        for(Player player : Bukkit.getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, entityStatus);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Static Methods
     */

    public static SkyblockPlayer getSkyblockPlayer(Player player) {
        ICustomPlayer customPlayer = SkyblockSandbox.getApi().getPlayerManager().isCustomPlayer(player);
        if(!(customPlayer instanceof SkyblockPlayer)) throw new NullPointerException("Invalid player.");

        return (SkyblockPlayer) customPlayer;
    }
}
