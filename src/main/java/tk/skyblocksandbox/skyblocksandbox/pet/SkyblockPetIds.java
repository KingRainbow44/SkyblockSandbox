package tk.skyblocksandbox.skyblocksandbox.pet;

public enum SkyblockPetIds {

    BABY_YETI();

    SkyblockPetIds() {

    }

    public final String getIdFromEnum(SkyblockPetIds id) {
        return id.name();
    }

}
