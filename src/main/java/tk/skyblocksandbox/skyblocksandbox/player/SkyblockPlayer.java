package tk.skyblocksandbox.skyblocksandbox.player;

import com.kingrainbow44.customplayer.player.CustomPlayer;
import com.kingrainbow44.customplayer.player.ICustomPlayer;
import me.vagdedes.mysql.database.SQL;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R3.ItemArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItem;
import tk.skyblocksandbox.skyblocksandbox.item.VanillaItemData;
import tk.skyblocksandbox.skyblocksandbox.scoreboard.HubScoreboard;
import tk.skyblocksandbox.skyblocksandbox.scoreboard.SkyblockScoreboard;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public final class SkyblockPlayer extends CustomPlayer implements ICustomPlayer {

    /*
     * Constants
     */
    public static final int SUBLOC_NONE = -1;
    public static final int SUBLOC_VILLAGE = 0;

    private final SkyblockPlayerData playerData;
    private final SkyblockScoreboard scoreboard;

    /*
     * Private Methods/Data Methods
     */

    public SkyblockPlayer(Player player) {
        super(player);

        playerData = new SkyblockPlayerData(this);
        scoreboard = new HubScoreboard(this);
        if(SkyblockSandbox.getConfiguration().databaseEnabled) {
            if (!SQL.exists("uuid", player.getUniqueId().toString(), "players")) {
                SQL.insertData("uuid, data", "'" + player.getUniqueId().toString() + "'" + ", '" + "{}" + "'", "players");
            } else if (!SQL.tableExists("players")) {
                throw new NullPointerException("SQL Table 'players' does not exist.");
            }

            Object existingData = SQL.get("data", "uuid", "=", player.getUniqueId().toString(), "players").toString();

            if(existingData != null) {
                getPlayerData().importData(existingData);
            }
        }
    }

    public void saveData() {
        String uuid = getBukkitPlayer().getUniqueId().toString();
        if(SkyblockSandbox.getConfiguration().databaseEnabled) {
            if (!SQL.exists("uuid", uuid, "players")) {
                SQL.insertData("uuid, data", "'" + uuid + "'" + ", '" + getPlayerData().exportData() + "'", "players");
            } else if (!SQL.tableExists("players")) {
                throw new NullPointerException("SQL Table 'players' does not exist.");
            }

            SQL.set("data", getPlayerData().exportData(), "uuid", "=", uuid, "players");
        }
    }

    /*
     * Set Methods
     */

    /*
     * Get Methods
     */

    public Location getSpawn() {
        // TODO: Refactor with Public Islands.
        switch(getPlayerData().location) {
            default:
            case 0:
                return new Location(Bukkit.getWorld("hub"), -3, 70, -70, 180, 0);
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
        }
    }

    public VanillaItemData getVanillaItemData() {
        VanillaItemData itemData = new VanillaItemData();
        itemData.importData(getBukkitPlayer().getInventory().getItemInMainHand());
        return itemData;
    }

    public SkyblockItem getItemInHand(boolean mainHand) {
        ItemStack item = mainHand ? getBukkitPlayer().getInventory().getItemInMainHand() : getBukkitPlayer().getInventory().getItemInOffHand();
        Object sbItem = SkyblockSandbox.getManagement().getItemManager().isSkyblockItem(item);
        if(!(sbItem instanceof SkyblockItem)) return null;

        return (SkyblockItem) sbItem;
    }

    public SkyblockScoreboard getScoreboard() {
        return scoreboard;
    }

    public void updateMaxHP() {
        double maxHP = getPlayerData().vanillaMaxHealth;

        AttributeInstance attribute = getBukkitPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(maxHP);
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

        sendMessage("&cYou died and lost " + playerData.coins + " coins!");
    }

    /*
     * Private
     */

    private String getHudHealth() {

        if(getPlayerData().absorptionHealth > 0) {
            return "&6" + (getPlayerData().currentHealth + getPlayerData().absorptionHealth) + "/" + playerData.health + "❤";
        }

        return "&c" + getPlayerData().currentHealth + "/" + playerData.health + "❤";
    }

    /*
     * General Methods
     */

    public void updateHud() {
        if(playerData.defense > 0) {
            Utility.sendActionBarMessage(getBukkitPlayer(), Utility.colorize(
                    getHudHealth() + "   " + "&a" + Math.round(playerData.defense) + "❈ Defense" + "   " + "&b" + getPlayerData().currentMana + "/" + playerData.intelligence + "✎ Mana"
            ));
        } else {
            Utility.sendActionBarMessage(getBukkitPlayer(), Utility.colorize(
                    getHudHealth() + "   " + "&b" + getPlayerData().currentMana + "/" + playerData.intelligence + "✎ Mana"
            ));
        }
    }

    public boolean manaCheck(int manaCost, String usageMessage) {
        if(getPlayerData().currentMana >= manaCost) {
            if(!getPlayerData().infiniteMana) getPlayerData().currentMana -= manaCost;

            Utility.sendActionBarMessage(getBukkitPlayer(), Utility.colorize(
                    getHudHealth() + "   " + "&b-" + manaCost + " Mana (&6" + usageMessage + "&b)" + "   " + "&b" + getPlayerData().currentMana + "/" + playerData.intelligence + "✎ Mana"
            ), 1, SkyblockSandbox.getInstance());

            return true;
        } else {
            sendMessage("&cYou do not have enough mana!");
            return false;
        }
    }
}
