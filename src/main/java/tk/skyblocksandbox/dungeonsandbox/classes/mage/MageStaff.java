package tk.skyblocksandbox.dungeonsandbox.classes.mage;

import tk.skyblocksandbox.dungeonsandbox.classes.ClassAbility;

public final class MageStaff extends ClassAbility {

    public MageStaff() {
        super("Mage Staff", "" +
                "All your melee weapon attacks become ranged. Those attacks deal 30% +5 of their melee",
                "Damage (increased by 0.09 * your",
                "Intelligence) up to 15 blocks, after which damage is reduced."
        );
    }

}
