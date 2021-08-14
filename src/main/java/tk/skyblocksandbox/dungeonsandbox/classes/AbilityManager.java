package tk.skyblocksandbox.dungeonsandbox.classes;

import org.bukkit.Bukkit;
import tk.skyblocksandbox.dungeonsandbox.classes.mage.MageStaff;
import tk.skyblocksandbox.dungeonsandbox.util.Ability;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

import java.util.HashMap;
import java.util.Map;

public final class AbilityManager {

    private final Map<Ability, ClassAbility> abilities = new HashMap<>();

    public AbilityManager() {
        registerAbility(new MageStaff(), Ability.MAGE_STAFF);
    }

    private void registerAbility(ClassAbility ability, Ability abilityConstant) {
        Bukkit.getPluginManager().registerEvents(ability, SkyblockSandbox.getInstance());
        abilities.put(abilityConstant, ability);
    }

    public ClassAbility getAbilityData(Ability abilityConstant) {
        return abilities.get(abilityConstant);
    }

}
