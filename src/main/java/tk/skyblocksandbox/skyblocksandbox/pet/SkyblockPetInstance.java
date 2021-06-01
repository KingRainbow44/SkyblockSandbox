package tk.skyblocksandbox.skyblocksandbox.pet;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class SkyblockPetInstance {

    private final ItemStack bukkitPet;
    private final NBTItem nbtPet;

    private final NBTCompound petData;

    public SkyblockPetInstance(ItemStack petAsItem) {
        bukkitPet = petAsItem;
        nbtPet = new NBTItem(petAsItem, true);

        petData = nbtPet.getCompound("petData");
    }

    /*
     * Get Raw Data
     */

    public ItemStack getBukkitPet() {
        return bukkitPet;
    }

    public NBTItem getPetItemNbt() {
        return nbtPet;
    }

    public NBTCompound getPetData() {
        return petData;
    }

    /*
     * Get Parsed Data
     */

    public String getPetName() {
        return petData.getString("petName");
    }

    public int getPetLevel() {
        return petData.getInteger("petLevel");
    }

    public long getPetExperience() {
        return petData.getLong("petExperience");
    }

    /*
     * Set Parsed Data
     */

    public void addPetLevel() {
        petData.setInteger("petLevel",
                petData.getInteger("petLevel") + 1
        );
    }

    public void addPetExperience(long experience) {
        petData.setLong("petExperience",
                petData.getLong("petExperience") + experience
        );
    }

    public void setPetLevel(int level) {
        petData.setInteger("petLevel", level);
    }

    public void setPetExperience(long experience) {
        petData.setLong("petExperience", experience);
    }

    /*
     * Static Get Methods
     */

    public static boolean isPet(ItemStack pet) {
        if(pet == null || pet.getType() == Material.AIR) {
            return false;
        }

        NBTItem nbtPet = new NBTItem(pet, true);
        if(!nbtPet.hasKey("itemData")) return false; NBTCompound itemData = nbtPet.getCompound("itemData");

        return itemData.hasKey("petData");
    }


}
