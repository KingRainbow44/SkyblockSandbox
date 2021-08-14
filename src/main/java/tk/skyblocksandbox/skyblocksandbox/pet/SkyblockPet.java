package tk.skyblocksandbox.skyblocksandbox.pet;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayerStorage;
import tk.skyblocksandbox.skyblocksandbox.storage.IStoragePage;
import tk.skyblocksandbox.skyblocksandbox.storage.pets.PetStorage;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.Collection;

public abstract class SkyblockPet extends SandboxItem {

    public SkyblockPet(String petName) {
        super(Material.PLAYER_HEAD, petName, SkyblockItemIds.PET);
    }

    @Override
    public final Collection<String> getLore() {
        return new Lore().petLore(this);
    }

    @Override
    public void ability(int trigger, SkyblockPlayer sbPlayer) {
        if(trigger != INTERACT_RIGHT_CLICK) return;

        SkyblockPlayerStorage storage = sbPlayer.getPlayerData().getPlayerStorage();
        IStoragePage petsMenu = storage.getPetStorage(); if(!(petsMenu instanceof PetStorage)) return;
        PetStorage ps = (PetStorage) petsMenu;

        ItemStack pet = sbPlayer.getBukkitPlayer().getInventory().getItemInMainHand();
        SkyblockPetInstance petInstance = new SkyblockPetInstance(pet);

        ps.addPet(pet);
        pet.setAmount(0);

        sbPlayer.sendMessages("&aAdded " + petInstance.getPetName() + " to your pets menu!");
    }

}
