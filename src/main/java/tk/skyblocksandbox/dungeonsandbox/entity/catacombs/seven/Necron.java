package tk.skyblocksandbox.dungeonsandbox.entity.catacombs.seven;

import org.bukkit.entity.EntityType;
import tk.skyblocksandbox.dungeonsandbox.util.BossDialogue;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.entity.SkyblockEntityData;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public class Necron extends SandboxEntity implements BossDialogue {

    public Necron() {
        super(EntityType.WITHER);
    }

    final String PREFIX = colorize("&2[BOSS] Necron&c: &c");

    @Override
    public SkyblockEntityData getEntityData() {
        SkyblockEntityData entityData = new SkyblockEntityData();

        entityData.entityName = "Necron";
        entityData.isBoss = true;

        entityData.canTakeKnockback = false;
        entityData.isUndead = true;

        entityData.health = 1000000000;
        entityData.defense = 300;
        entityData.damage = 5000;

        return entityData;
    }

    @Override
    public String getDialogue(int phase) {
        switch(phase) {
            case -1: // SPEECH - START
                return PREFIX + "You tricked me!";
            case -2:
                return PREFIX + "STOP USING MY FACTORY AGAINST ME!";

            default:
            case 0: // PHASE 1 - START
                return PREFIX + "Finally, I heard so much about you. The Eye likes you very much.";
            case 1:
                return PREFIX + "You went further down than any human before, congratulations.";
            case 2:
                return PREFIX + "My master and I spent centuries building this factory...and this army.";
            case 3: // PHASE 1 - END
                return PREFIX + "You caused me many troubles, your journey ends here adventurers!";

            case 4: // PHASE 2 - START
                return PREFIX + "FINE! LET'S MOVE TO SOMEWHERE ELSE!!";
            case 5:
                return PREFIX + "This is my favorite place, no more crystals, no more beams, just explosions!";
            case 6:
                return PREFIX + "Can't hit me with that one anymore!!";
            case 7: // PHASE 2 - END
                return PREFIX + "I'VE HAD ENOUGH! YOU'RE NOT HITTING ME WITH ANY MORE PILLARS!";
        }
    }
}
