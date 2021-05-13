package tk.skyblocksandbox.skyblocksandbox.item;

import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.util.Collection;

public final class BukkitSandboxItem {

    private final ItemStack toConvert;
    private final SandboxItem sbItem;

    public BukkitSandboxItem(ItemStack item) {
        toConvert = item;

        sbItem = new SandboxItem(toConvert.getType(), Utility.getVanillaItemName(item.getType()), "VANILLA_" + Utility.changeCase(toConvert.getType().toString(), true)) {
            @Override
            public SkyblockItemData getItemData() {
                SkyblockItemData data = new SkyblockItemData();

                data.rarity = COMMON;
                data.isVanilla = true;
                data.canReforge = false;

                return data;
            }

            @Override
            public Collection<String> getLore() {
                Lore lore = new Lore();

                return lore.genericLore(this);
            }
        };
    }

    public ItemStack create() {

        ItemStack item = sbItem.create();
        item.setAmount(toConvert.getAmount());

        return item;
    }

    public SandboxItem toSandboxItem() {
        return sbItem;
    }

}
