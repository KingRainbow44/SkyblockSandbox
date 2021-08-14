package tk.skyblocksandbox.skyblocksandbox.pet.pets;

import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.pet.SkyblockPet;

public final class BabyYeti extends SkyblockPet {

    public BabyYeti() {
        super("Baby Yeti");
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.isPet = true;
        itemData.itemType = PET;

        itemData.rarity = EPIC;
        itemData.baseDamage = 1;

        return itemData;
    }
}
