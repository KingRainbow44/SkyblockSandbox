package tk.skyblocksandbox.skyblocksandbox.item.weapons;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ShadowFury extends SandboxItem {
    public ShadowFury() {
        super(Material.DIAMOND_SWORD, "Shadow Fury", SkyblockItemIds.SHADOW_FURY);
    }

    @Override
    public Collection<String> getLore() {
        return new Lore().genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.rarity = LEGENDARY;
        itemData.itemType = SWORD;

        itemData.baseDamage = 300;
        itemData.baseStrength = 125;
        itemData.baseSpeed = 30;

        itemData.canHaveStars = true;
        itemData.canReforge = true;
        itemData.isDungeonItem = true;
        itemData.abilityCooldown = 15;

        itemData.hasAbility = true;
        itemData.abilityTrigger = RIGHT_CLICK_TRIGGER;
        itemData.abilityName = "Shadow Fury";
        itemData.abilityDescription =
                "§7Rapidly teleports you to up to\n" +
                        "§7§b5 §7enemies within §e12\n" +
                        "§e§7blocks, rooting each of them\n" +
                        "§7and allowing you to hit them.\n";

        return itemData;
    }


    @Override
    public void ability(int action, SkyblockPlayer sbPlayer) {
        if(action != INTERACT_RIGHT_CLICK) return;
        if(!sbPlayer.manaCheck(0, "Shadow Fury")) return;
        Player player = sbPlayer.getBukkitPlayer();
        List<Entity> inRange = player.getNearbyEntities(12, 12, 12);
        List<Entity> filteredList = new ArrayList<Entity>();

        for (Entity e : inRange) {
            if (e instanceof Damageable && e != player) {
                if (filteredList.size() < 5) {
                    filteredList.add(e);
                }else {
                    break;
                }
            }
        }

        new BukkitRunnable() {

            private int run = 0;

            @Override
            public void run() {
                if (run < filteredList.size()) {
                    if (!filteredList.get(run).isDead()){
                        player.teleport(filteredList.get(run).getLocation().add(filteredList.get(run).getLocation().getDirection().multiply(-1)));
                    }
                    run++;
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(SkyblockSandbox.getInstance(), 0L, 20L);
    }
}
