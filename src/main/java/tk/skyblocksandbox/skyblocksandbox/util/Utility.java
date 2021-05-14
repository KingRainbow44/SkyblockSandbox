package tk.skyblocksandbox.skyblocksandbox.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.LocaleLanguage;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.text.translate.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class Utility {

    /*
     * Utility Functions
     * Basic functions that can be used anywhere for any application.
     */

    private static final Map<Player, BukkitTask> PENDING_MESSAGES = new HashMap<>();

    /**
     * Sends a message to the player's action bar.
     * <p/>
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param message the message to send.
     */
    public static void sendActionBarMessage(Player bukkitPlayer, String message) {
        sendRawActionBarMessage(bukkitPlayer, "{\"text\": \"" + message + "\"}");
    }

    /**
     * Sends a raw message (JSON format) to the player's action bar. Note: while the action bar accepts raw messages
     * it is currently only capable of displaying text.
     * <p/>
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param rawMessage the json format message to send.
     */
    public static void sendRawActionBarMessage(Player bukkitPlayer, String rawMessage) {
        CraftPlayer player = (CraftPlayer) bukkitPlayer;
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(rawMessage);
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatBaseComponent, ChatMessageType.GAME_INFO, bukkitPlayer.getUniqueId());
        player.getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

    /**
     * Sends a message to the player's action bar that lasts for an extended duration.
     * <p/>
     * The message will appear above the player's hot bar for the specified duration and fade away during the last
     * second of the duration.
     * <p/>
     * Only one long duration message can be sent at a time per player. If a new message is sent via this message
     * any previous messages still being displayed will be replaced.
     *
     * @param bukkitPlayer the player to send the message to.
     * @param message the message to send.
     * @param duration the duration the message should be visible for in seconds.
     * @param plugin the plugin sending the message.
     */
    public static void sendActionBarMessage(final Player bukkitPlayer, final String message,
                                            final int duration, Plugin plugin) {
        cancelPendingMessages(bukkitPlayer);
        final BukkitTask messageTask = new BukkitRunnable() {
            private int count = 0;
            @Override
            public void run() {
                if (count >= (duration - 3)) {
                    this.cancel();
                }
                sendActionBarMessage(bukkitPlayer, message);
                count++;
            }
        }.runTaskTimer(plugin, 0L, 20L);
        PENDING_MESSAGES.put(bukkitPlayer, messageTask);
    }

    private static void cancelPendingMessages(Player bukkitPlayer) {
        if (PENDING_MESSAGES.containsKey(bukkitPlayer)) {
            PENDING_MESSAGES.get(bukkitPlayer).cancel();
        }
    }

    public static String colorize(String message) {
        return message.replace('&', ChatColor.COLOR_CHAR);
    }

    public static String changeCase(String message, boolean upper) {
        return upper ? message.toUpperCase() : message.toLowerCase();
    }

    public static String commafy(Object input) {

        String integer;
        if(input instanceof String) {
            integer = (String) input;
        } else if (input instanceof Integer) {
            integer = "" + input;
        } else {
            return "" + input;
        }

        String commafiedNum="";
        Character firstChar= integer.charAt(0);

        //If there is a positive or negative number sign,
        //then put the number sign to the commafiedNum and remove the sign from inputNum.
        if(firstChar=='+' || firstChar=='-')
        {
            commafiedNum = commafiedNum + Character.toString(firstChar);
            integer=integer.replaceAll("[-\\+]", "");
        }

        //If the input number has decimal places,
        //then split it into two, save the first part to inputNum
        //and save the second part to decimalNum which will be appended to the final result at the end.
        String [] splittedNum = integer.split("\\.");
        String decimalNum="";
        if(splittedNum.length==2)
        {
            integer=splittedNum[0];
            decimalNum="."+splittedNum[1];
        }

        //The main logic for adding commas to the number.
        int numLength = integer.length();
        for (int i=0; i<numLength; i++) {
            if ((numLength-i)%3 == 0 && i != 0) {
                commafiedNum += ",";
            }
            commafiedNum += integer.charAt(i);
        }

        return commafiedNum+decimalNum;
    }

    public static ChatColor rarityToColor(Integer rarity) {
        switch(rarity) {
            default:
            case SandboxItem.COMMON:
                return ChatColor.WHITE;
            case SandboxItem.UNCOMMON:
                return ChatColor.GREEN;
            case SandboxItem.RARE:
                return ChatColor.BLUE;
            case SandboxItem.EPIC:
                return ChatColor.DARK_PURPLE;
            case SandboxItem.LEGENDARY:
                return ChatColor.GOLD;
            case SandboxItem.MYTHIC:
                return ChatColor.LIGHT_PURPLE;
            case SandboxItem.SUPREME:
                return ChatColor.DARK_RED;
            case SandboxItem.SPECIAL:
            case SandboxItem.VERY_SPEICAL:
                return ChatColor.RED;
        }
    }

    public static String getVanillaItemName(Material item) {
        net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(new ItemStack(item));
        return LocaleLanguage.a().a(nmsStack.getItem().getName());
    }

    public static String toRomanNumeral(int input) throws Exception {
        if (input < 1 || input > 3999)
            throw new Exception("Invalid number: Cannot be converted to a numeral.");

        StringBuilder s = new StringBuilder();

        while (input >= 1000) {
            s.append("M");
            input -= 1000;
        } while (input >= 900) {
            s.append("CM");
            input -= 900;
        } while (input >= 500) {
            s.append("D");
            input -= 500;
        } while (input >= 400) {
            s.append("CD");
            input -= 400;
        } while (input >= 100) {
            s.append("C");
            input -= 100;
        } while (input >= 90) {
            s.append("XC");
            input -= 90;
        } while (input >= 50) {
            s.append("L");
            input -= 50;
        } while (input >= 40) {
            s.append("XL");
            input -= 40;
        } while (input >= 10) {
            s.append("X");
            input -= 10;
        } while (input >= 9) {
            s.append("IX");
            input -= 9;
        } while (input >= 5) {
            s.append("V");
            input -= 5;
        } while (input >= 4) {
            s.append("IV");
            input -= 4;
        } while (input >= 1) {
            s.append("I");
            input -= 1;
        }

        return s.toString();
    }

    public static NamespacedKey key(String key) {
        return new NamespacedKey(SkyblockSandbox.getInstance(), key);
    }

}
