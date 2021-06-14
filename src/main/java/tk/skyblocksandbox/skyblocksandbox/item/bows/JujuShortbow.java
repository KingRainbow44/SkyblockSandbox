package tk.skyblocksandbox.skyblocksandbox.item.bows;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.Collection;

import static tk.skyblocksandbox.skyblocksandbox.util.Utility.colorize;

public final class JujuShortbow extends SandboxItem {

    public JujuShortbow() {
        super(Material.BOW, "Juju Shortbow", SkyblockItemIds.JUJU_SHORTBOW);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        itemData.itemType = BOW;
        itemData.canDungeonize = true;

        itemData.baseDamage = 310;
        itemData.baseStrength = 40;
        itemData.baseCriticalStrikeChance = 10;
        itemData.baseCriticalDamage = 110;

        itemData.rarity = EPIC;

        itemData.hasAbility = true; itemData.hideAbilityTag = true;
        itemData.abilityTrigger = LEFT_CLICK_TRIGGER;
        itemData.abilityName = "Shortbow";
        itemData.abilityDescription = "&7Hits &c3&7 mobs on impact.\n" +
                "&7Can damage endermen.";

        return itemData;
    }

    @Override
    public Collection<String> getLore() {
        return new Lore(4,
                "",
                colorize("&5Shortbow: Instantly Shoots!"),
                colorize("&7Hits &c3&7 mobs on impact."),
                colorize("&7Can damage endermen.")
        ).genericLore(this);
    }

    @Override
    public void ability(int action, SkyblockPlayer sbPlayer) {
        switch(action) {
            default:
                return;
            case INTERACT_LEFT_CLICK:
            case INTERACT_RIGHT_CLICK:
                Player player = sbPlayer.getBukkitPlayer();

                Arrow arrow = player.launchProjectile(Arrow.class); arrow.setBounce(false);

                Arrow arrow2 = player.launchProjectile(Arrow.class); arrow2.setBounce(false);
                arrow2.setVelocity(
                        player.getEyeLocation().getDirection()
                                .rotateAroundY(0.05)
                                .multiply(arrow.getVelocity().length())
                );

                Arrow arrow3 = player.launchProjectile(Arrow.class); arrow3.setBounce(false);
                arrow3.setVelocity(
                        player.getEyeLocation().getDirection()
                                .rotateAroundY(-0.05)
                                .multiply(arrow.getVelocity().length())
                );
        }
    } @Override public boolean shouldCancel() { return true; }
}
