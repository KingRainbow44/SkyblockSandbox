package tk.skyblocksandbox.skyblocksandbox.item.weapons;

import org.bukkit.Material;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.Collection;

public final class GiantsSword extends SandboxItem {

    public GiantsSword() {
        super(Material.IRON_SWORD, "Giant's Sword", SkyblockItemIds.GIANTS_SWORD);
    }

    @Override
    public Collection<String> getLore() {
        Lore generator = new Lore();

        return generator.genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isDungeonItem = true;
        itemData.rarity = LEGENDARY;
        itemData.itemType = SWORD;

        itemData.baseDamage = 500;

        itemData.hasAbility = true;
        itemData.abilityName = "Giant's Slam";
        itemData.abilityTrigger = RIGHT_CLICK_TRIGGER;
        itemData.abilityCooldown = 30;
        itemData.abilityCost = 100;
        itemData.abilityDescription = "&7Slam your sword into the ground\n" +
                "&7dealing &c100,000 &7damage to\n" +
                "&7nearby enemies.";

        return itemData;
    }

}
