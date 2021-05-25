package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.one;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

public final class Bonzo extends SkyblockEntity {

    public Bonzo() {
        super(EntityType.PLAYER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.isNpc = true;
        entityData.isBoss = true;
        entityData.isHostile = true;
        entityData.isUndead = true;

        entityData.mainHand = Utility.getHeadFromUrl("http://textures.minecraft.net/texture/97c4036f80bf3609180c7c4a9568853fd815f154ad8438b92f3851019835b070");

        entityData.entityName = "Bonzo";
        entityData.health = 250000;
        entityData.damage = 1000;
        entityData.speed = 150;

        entityData.skinName = "SafeDrift48";
        entityData.skinSignature = "dj+mS77dCJC2QrkccHatGa3Z8RzrAycthJ6w3T2F8yAxdYnHNsdf6m+K7+L3hoEIqtzktgyKj/rcA4032Oklmp8doxLmFAvzyKbnCuO4yYt4xGJo2mwt1ZXkOv/saoZeV/lIOW0JlBtL+7K+mUgcxcGbBhZNl3i4QMyWQGqPHoAOXKU8pkfORdYjFS+eEVltWL463m/hc//5brrrxBWKjuOg9XEUWE" +
                "RIeV2S4KicDvjY9DTcxiutckvH22t71JKddW2DMk7XfYsASerItk28z8zhbseaHL5v7GsWW7hmWCNiw0teoaWBtsq0Hlyo+qV63MCdxJNJQDR1UeilL6ZfsNSKt0av5bUrJLaVOU1CtGqRueCFABwBRb6zFU53M6F1feCtoODhlLbB8G8xP8L2NuM2qcuS6nv7pIR+5youtKzUjhVupxf2Fj0xSQiDtYV4/QXPvP9GcHiMsi8" +
                "yh/h7mtc802TwG4LGA6mvb9BNOAuXNkWCAcBo2oxQbIYR5LVw2xEyCjCcdjIXlxjAMbKW6TP37BFBgDVaXocS6KmEQ8jCx+2B3LHcXFmT2p+RGFANeIaeewtrwyd7Cm5KWolhwG1WSJASthJYo5sa38q9NBobD37aBkYgKOnjGXR6PhKbAdQEUkQZYifnjX4XycVDHdgWpzUO+F+/fDQ4i1Q36eI=";
        entityData.skinData = "ewogICJ0aW1lc3RhbXAiIDogMTYyMTEzNDcwMTk3MCwKICAicHJvZmlsZUlkIiA6ICI5ZDIyZGRhOTVmZGI0MjFmOGZhNjAzNTI1YThkZmE4ZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJTYWZlRHJpZnQ0OCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJ" +
                "TS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWU3OWY3ZTYyOGYyMWY0Mjc2MTBjYzZjZWQwZmYxNTNlMjE4ODBkNjFjYmFjNjczYmY4NjJiMWQ0ZTQ0MjZiIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAg" +
                "IH0KICAgIH0KICB9Cn0=";

        return entityData;
    }

}
