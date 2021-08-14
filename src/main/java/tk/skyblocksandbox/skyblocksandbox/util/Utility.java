package tk.skyblocksandbox.skyblocksandbox.util;

import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.LocaleLanguage;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tk.skyblocksandbox.permitable.rank.PermitableRank;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static ItemStack getHeadFromUrl(String url) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if(meta != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;

            try {
                profileField = meta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }

            profileField.setAccessible(true);

            try {
                profileField.set(meta, profile);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getHeadFromUrl(SkullMeta meta, String url) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if(meta != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;

            try {
                profileField = meta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }

            profileField.setAccessible(true);

            try {
                profileField.set(meta, profile);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        item.setItemMeta(meta);

        return item;
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String changeCase(String message, boolean upper) {
        return upper ? message.toUpperCase() : message.toLowerCase();
    }

    public static Player getRandomPlayer() {
        ArrayList<Player> allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        int random = new Random().nextInt(allPlayers.size());

        return allPlayers.get(random);
    }

    public static String commafy(Object input) {

        String integer;
        if(input instanceof String) {
            integer = (String) input;
        } else if (input instanceof Integer || input instanceof Long) {
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
            commafiedNum = commafiedNum + firstChar;
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
            case SandboxItem.NONE:
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
            case SandboxItem.PURPLE:
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

    public static String generateRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    public static String generateRandomString(String charList) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * charList.length());
            salt.append(charList.charAt(index));
        }
        return salt.toString();

    }

    public static int generateRandomNumber(int min, int max) {
        return (int) Math.round(Math.floor(Math.random()*(max-min+1)+min));
    }

    public static void applyGamerules(World world) {
        world.setGameRule(
                GameRule.DO_MOB_SPAWNING, false
        );
        world.setGameRule(
                GameRule.DO_DAYLIGHT_CYCLE, false
        );
        world.setGameRule(
                GameRule.DO_FIRE_TICK, false
        );
        world.setGameRule(
                GameRule.MOB_GRIEFING, false
        );

        world.setGameRule(
                GameRule.KEEP_INVENTORY, true
        );
    }

    public static PermitableRank getRankOfPlayer(SkyblockPlayer sbPlayer) {
        return PermitableRank.getRankByEnum(sbPlayer.getPlayerData().rank);
    }

    /**
     * @return A double of 2 integers. The first is the amount of loops. The second is the remaining quantity.
     */
    public static Collection<Integer> countSteps(int loopOver, int loopQuantity) {
        for(int i = 0; i <= 2147483646; i++) {
            if(loopOver % loopQuantity == 0) {
                Collection<Integer> steps = new ArrayList<>();
                steps.add(i);
                steps.add(0);

                return steps;
            }

            if(loopOver - loopQuantity < 0) {
                Collection<Integer> steps = new ArrayList<>();
                steps.add(i);
                steps.add(loopOver);

                return steps;
            }

            loopOver -= loopQuantity;
        }

        Collection<Integer> steps = new ArrayList<>();
        steps.add(loopOver); steps.add(loopQuantity);

        return steps;
    }

    public static boolean isValidUsername(String username) throws IOException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT;
    }

    public static int getLastIntFromString(String string) {
        String result = "0";

        Pattern p = Pattern.compile("[0-9]+$");
        Matcher m = p.matcher(string);
        if(m.find()) {
            result = m.group();
        }

        return Integer.parseInt(result);
    }

    public static Location floor(Location location) {
//        Location finalLocation = location.clone();
//
//        while(finalLocation.getBlock().getType().isAir() && finalLocation.getY() < 256) {
//            finalLocation.add(0, 1, 0);
//        }
//
//        if(finalLocation.getY() == location.getY()) {
//            finalLocation.add(0, 1, 0);
//        }
//
//        if(finalLocation.getY() == 255) {
//            while(finalLocation.getBlock().getType().isAir() && finalLocation.getY() > 0) {
//                finalLocation.subtract(0, 1, 0);
//            }
//        }
//
/*        return finalLocation; TODO: Fix & Complete {Utility#floor()}*/ return location;
    }

}
