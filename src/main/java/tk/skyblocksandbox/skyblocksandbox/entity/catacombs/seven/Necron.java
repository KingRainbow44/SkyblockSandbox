package tk.skyblocksandbox.skyblocksandbox.entity.catacombs.seven;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public final class Necron extends SkyblockEntity {

    public Necron() {
        super(EntityType.WITHER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Necron";
        entityData.isBoss = true;

        entityData.canTakeKnockback = false;
        entityData.isUndead = true;

        entityData.health = 1000000000;
        entityData.defense = 300;
        entityData.vanillaHealth = 300;

        return entityData;
    }

    @Override
    public void updateBossBar() {
        if(getEntityBossBar() == null) return;

        if(getEntityHealth() >= 1000000000) {
            getEntityBossBar().setProgress(1.0f);
            return;
        }

        if(getEntityHealth() < 1000000000 && getEntityHealth() > 99999999) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 1000000000 );
            return;
        }

        if(getEntityHealth() < 99999999 && getEntityHealth() > 9999999) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 100000000 );
            return;
        }

        if(getEntityHealth() < 9999999 && getEntityHealth() > 999999) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 10000000 );
            return;
        }

        if(getEntityHealth() < 999999 && getEntityHealth() > 99999) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 1000000 );
            return;
        }

        if(getEntityHealth() < 99999 && getEntityHealth() > 9999) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 100000 );
            return;
        }

        if(getEntityHealth() < 9999 && getEntityHealth() > 999) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 10000 );
            return;
        }

        if(getEntityHealth() < 999 && getEntityHealth() > 99) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 1000 );
            return;
        }

        if(getEntityHealth() < 99 && getEntityHealth() > 9) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 100 );
            return;
        }

        if(getEntityHealth() < 9 && getEntityHealth() > 0) {
            getEntityBossBar().setProgress( (float) getEntityHealth() / 10 );
        }
    }
}
