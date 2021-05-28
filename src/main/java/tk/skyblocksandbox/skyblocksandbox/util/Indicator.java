package tk.skyblocksandbox.skyblocksandbox.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

import java.util.ArrayList;
import java.util.List;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class Indicator {

    public static void displayDamage(Location location, long indicatorDamage, boolean wasCritical) {
        if(location.getWorld() == null) {
            throw new NullPointerException("Location does not contain a Bukkit world.");
        }

        World world = location.getWorld();
        ArmorStand armorStand = (ArmorStand) world.spawnEntity(location.add(
                Utility.generateRandomNumber(-1, 1),
                Utility.generateRandomNumber(-1, 1),
                Utility.generateRandomNumber(-1, 1)
        ), EntityType.ARMOR_STAND);

        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setSmall(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(SkyblockSandbox.getInstance(), 20L);

        if(wasCritical) {
            armorStand.setCustomName(criticalIfy(indicatorDamage));
        } else {
            armorStand.setCustomName(colorize("&7" + Utility.commafy(indicatorDamage)));
        }
    }

    private static String criticalIfy(long initialDamage) {
        List<Integer> numberArray = splitViaString(initialDamage);
        StringBuilder finalDamage = new StringBuilder(colorize("&f✧"));

        numberArray.forEach((num) -> {
            switch(Utility.generateRandomNumber(1, 4)) {
                case 1:
                    finalDamage.append(colorize("&f" + num));
                    break;
                case 2:
                    finalDamage.append(colorize("&e" + num));
                    break;
                case 3:
                    finalDamage.append(colorize("&6" + num));
                    break;
                case 4:
                    finalDamage.append(colorize("&c" + num));
                    break;
            }
        });

        finalDamage.append(colorize("&f✧"));

        return finalDamage.toString();
    }

    private static List<Integer> splitViaString(long number) {

        ArrayList<Integer> result = new ArrayList<>();
        String s = Long.toString(number);

        for (int i = 0; i < s.length(); i++) {
            result.add(s.charAt(i) - '0');
        }
        return result;
    }

}
