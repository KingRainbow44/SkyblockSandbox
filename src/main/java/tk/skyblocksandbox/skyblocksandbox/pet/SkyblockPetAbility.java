package tk.skyblocksandbox.skyblocksandbox.pet;

public abstract class SkyblockPetAbility {

    public final String abilityName;
    public final String[] abilityDescription;

    public SkyblockPetAbility(String abilityName, String... abilityDescription) {
        this.abilityName = abilityName;
        this.abilityDescription = abilityDescription;
    }

}
