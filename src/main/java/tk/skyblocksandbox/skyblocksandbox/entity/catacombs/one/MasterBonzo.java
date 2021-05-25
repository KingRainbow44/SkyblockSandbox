package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.one;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class MasterBonzo extends SkyblockEntity {

    public MasterBonzo() {
        super(EntityType.PLAYER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.isNpc = true;
        entityData.isBoss = true;
        entityData.isHostile = true;
        entityData.isUndead = true;

        entityData.mainHand = new ItemStack(Material.BLAZE_ROD);

        entityData.entityName = "Bonzo";
        entityData.health = 200000000;
        entityData.damage = 100000;
        entityData.speed = 170;

        entityData.skinName = "StormStormy";
        entityData.skinSignature = "q7yqBQpmiIX3C7VsN+4i0GSUHCk1E9KUct8Jxp+7DT6ysYsBBx7Cttn353HERv+8brC2U8z0WjRVsyaMcjmWKsAfiGslSmHHSRf2z6SnPIxWAq/B+aL9YybYimBMwsNb7PJFO76JURGTJuifjmzzZtc1Vb52tgJQUw0vJoUuG9o9dBgWNE8nK1ooScK1y5Q48so9i8dWtgP1Ykoo6b4wg/8alcrd" +
                "/WPNm2qUTgXIkf16aj982mclHwJ79CylJhuJNXG1fBr20y/Xg6kmw40Y5eaFY6Wf3YL7xPOI49lxnymhxPgl58NdzvLHmPDoLr4vxoqkTdg87mz11EIMGy+v7KJq5PYmHm6hog/Kyrkky9nSGZ2p0vLZ3qbTLpX7NI/AYIkV3gmO4/Ry17MTO4ghAvLZMtQ6uzrqOTm4y8XgVOKlFvOi6mbCaWIqfFUppqItUIcG7FSiCfU" +
                "bqlIwN4Zly+21WjllznrPS6ZGrnab5yucvg3zm6dlP3FIJ199OnDAQoPvJoVbuvy4hd2BXHqg12MehrKxHM/QYK9KVj11REUIU3s+OTEcus5pyEmFmkk2M7LHjPe6zjYIKF+bCadrMd5Aogm4E3bJne8jcGMwzJ7CfbSw9Fo+mD+YkLiozVQG92NzFPCDNR+5LWmO3ADniDn/kq5N/vHHaguegVHq3GU=";
        entityData.skinData = "ewogICJ0aW1lc3RhbXAiIDogMTYxNDUyNzM4NjA3MSwKICAicHJvZmlsZUlkIiA6ICI5MWYwNGZlOTBmMzY0M2I1OGYyMGUzMzc1Zjg2ZDM5ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdG9ybVN0b3JteSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgI" +
                "CJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMjcxNmVjYmY1YjhkYTAwYjA1ZjMxNmVjNmFmNjFlOGJkMDI4MDViMjFlYjhlNDQwMTUxNDY4ZGM2NTY1NDljIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAg" +
                "ICAgIH0KICAgIH0KICB9Cn0=";

        return entityData;
    }

}
