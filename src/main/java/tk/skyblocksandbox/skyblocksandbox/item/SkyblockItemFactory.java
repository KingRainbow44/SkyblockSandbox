package tk.skyblocksandbox.skyblocksandbox.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.item.armor.chestplates.PurplesPupleCloak;
import tk.skyblocksandbox.skyblocksandbox.item.armor.goldor.GoldorBoots;
import tk.skyblocksandbox.skyblocksandbox.item.armor.goldor.GoldorChestplate;
import tk.skyblocksandbox.skyblocksandbox.item.armor.goldor.GoldorHelmet;
import tk.skyblocksandbox.skyblocksandbox.item.armor.goldor.GoldorLeggings;
import tk.skyblocksandbox.skyblocksandbox.item.armor.helmets.CraftyzFrenchBread;
import tk.skyblocksandbox.skyblocksandbox.item.armor.helmets.FlamingoMask;
import tk.skyblocksandbox.skyblocksandbox.item.armor.helmets.WardenHelmet;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronBoots;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronChestplate;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronHelmet;
import tk.skyblocksandbox.skyblocksandbox.item.armor.necron.NecronLeggings;
import tk.skyblocksandbox.skyblocksandbox.item.armor.storm.StormBoots;
import tk.skyblocksandbox.skyblocksandbox.item.armor.storm.StormChestplate;
import tk.skyblocksandbox.skyblocksandbox.item.armor.storm.StormHelmet;
import tk.skyblocksandbox.skyblocksandbox.item.armor.storm.StormLeggings;
import tk.skyblocksandbox.skyblocksandbox.item.bows.Bonemerang;
import tk.skyblocksandbox.skyblocksandbox.item.misc.SkyblockMenu;
import tk.skyblocksandbox.skyblocksandbox.item.misc.TheCarottesCarrot;
import tk.skyblocksandbox.skyblocksandbox.item.pets.GenericPet;
import tk.skyblocksandbox.skyblocksandbox.item.weapons.*;
import tk.skyblocksandbox.skyblocksandbox.item.materials.NecronsHandle;

import java.util.HashMap;
import java.util.Map;

public final class SkyblockItemFactory {

    private final Map<String, SandboxItem> items = new HashMap<>();

    public SkyblockItemFactory() {
        registerItem(new Hyperion()); registerItem(new Valkyrie());
        registerItem(new MidasStaff());
        registerItem(new Bonemerang());
        registerItem(new AxeOfTheShredded());
        registerItem(new GiantsSword());
        registerItem(new ShadowFury());

        registerItem(new NecronsHandle());

        registerItem(new NecronHelmet()); registerItem(new NecronChestplate()); registerItem(new NecronLeggings()); registerItem(new NecronBoots());

        registerItem(new StormHelmet()); registerItem(new StormChestplate()); registerItem(new StormLeggings()); registerItem(new StormBoots());

        registerItem(new GoldorHelmet()); registerItem(new GoldorChestplate()); registerItem(new GoldorLeggings()); registerItem(new GoldorBoots());

        registerItem(new CraftyzFrenchBread());
        registerItem(new FlamingoMask());
        registerItem(new PurplesPupleCloak());
        registerItem(new WardenHelmet());

        registerItem(new SkyblockMenu());
        registerItem(new TheCarottesCarrot());

        registerItem(new GenericPet());
    }

    public void registerItem(SandboxItem item) {
        items.put(item.getItemId(), item);
    }

    public Object isSkyblockItem(ItemStack item) {
        if(!isSBItemInstance(item)) return null;

        SandboxItemStack itemStack = new SandboxItemStack(item);

        return items.getOrDefault(itemStack.getInternalId(), null);
    }

    public Object isSkyblockItem(String itemId) {
        return items.getOrDefault(itemId, null);
    }

    public boolean isSBItemInstance(ItemStack itemStack) {
        if(itemStack == null || itemStack.getType() == Material.AIR) return false;

        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("itemData");
    }

    public Map<String, SandboxItem> getRegisteredItems() {
        return items;
    }
}
