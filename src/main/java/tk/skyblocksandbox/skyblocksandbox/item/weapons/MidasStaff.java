package tk.skyblocksandbox.skyblocksandbox.item.weapons;

import org.bukkit.Material;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.Collection;

public final class MidasStaff extends SandboxItem {

    public MidasStaff() {
        super(Material.GOLDEN_SHOVEL, "Midas Staff", SkyblockItemIds.MIDAS_STAFF);
    }

    @Override
    public Collection<String> getLore() {
        return new Lore().genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        // Abilities - START \\
        itemData.hasAbility = true;
        itemData.abilityName = "Molten Wave";
        itemData.abilityDescription = "&7Cast a wave of molten gold in\n" +
                "the direction you are facing!\n" +
                "Deals up to &c26,000.0\n" +
                "&7damage.";
        itemData.abilityCost = 500;
        itemData.abilityCooldown = 1;
        itemData.abilityTrigger = RIGHT_CLICK_TRIGGER;

        itemData.hasSecondAbility = true;
        itemData.abilityName2 = "Greed";
        itemData.abilityDescription2 = "&7The &3ability damage bonus &7of\n" +
                "&7this item is dependent on the\n" +
                "&7price paid for it at the &5Dark\n" +
                "&5Auction&7!\n" +
                "&7The maximum bonus of this item\n" +
                "&7is &326,000 &7if the bid was\n" +
                "&6100,000,000 Coins &7or higher!\n" +
                " \n" +
                "&7Price paid: &6100,000,000 Coins\n" +
                "&7Base Ability Damage Bonus: &326,000";
        // Abilities - END \\

        itemData.baseDamage = 130;
        itemData.baseStrength = 150;
        itemData.baseIntelligence = 50;

        itemData.rarity = LEGENDARY;
        itemData.itemType = SWORD;

        return itemData;
    }
}
