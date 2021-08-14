package tk.skyblocksandbox.dungeonsandbox.entity.catacombs.six;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.dungeonsandbox.util.BossDialogue;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

public class SadanNpc extends SandboxEntity implements BossDialogue {

    public SadanNpc() {
        super(EntityType.PLAYER);
    }

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Sadan";
        entityData.isBoss = true;

        entityData.health = 40000000;

        return entityData;
    }

    @Override
    public String getDialogue(int phase) {
        switch(phase) {
            default:
            case 0:
                return "";
        }
    }

    public enum Dialogue {

    }
}
