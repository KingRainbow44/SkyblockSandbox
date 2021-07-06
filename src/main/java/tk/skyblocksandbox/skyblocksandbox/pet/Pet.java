package tk.skyblocksandbox.skyblocksandbox.pet;

import tk.skyblocksandbox.skyblocksandbox.pet.pets.BabyYeti;

public enum Pet {

    BABY_YETI(SkyblockPetIds.BABY_YETI, new BabyYeti());

    private final String petIdentifier;
    private final SkyblockPet petItemInstance;

    Pet(SkyblockPetIds petIdentifier, SkyblockPet petItem) {
        this.petIdentifier = petIdentifier.getIdFromEnum(petIdentifier);
        petItemInstance = petItem;
    }

}
