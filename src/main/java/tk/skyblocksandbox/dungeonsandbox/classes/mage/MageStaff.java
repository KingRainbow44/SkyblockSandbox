package tk.skyblocksandbox.dungeonsandbox.classes.mage;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tk.skyblocksandbox.dungeonsandbox.classes.ClassAbility;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItemStack;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;

import java.util.ArrayList;
import java.util.List;

public final class MageStaff extends ClassAbility {

    public MageStaff() {
        super("Mage Staff", "" +
                "All your melee weapon attacks become ranged. Those attacks deal 30% +5 of their melee",
                "Damage (increased by 0.09 * your",
                "Intelligence) up to 15 blocks, after which damage is reduced."
        );
    }

    @EventHandler
    @Override
    public void interact(PlayerInteractEvent event) {
        if(event.getAction() != Action.LEFT_CLICK_AIR) return;

        Player player = event.getPlayer();
        SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(player);

        SandboxItemStack item = sbPlayer.getItemStackOfHand(true);
        ItemStack itemStack = item.getBukkitItemStack();
        SandboxItem sbItem = SandboxItemStack.toSandboxItem(itemStack);

        if(sbItem.getItemData().itemType != SandboxItem.SWORD) return;
        particleBeam(sbPlayer);
    }

    private void particleBeam(SkyblockPlayer sbPlayer){
        Player player = sbPlayer.getBukkitPlayer();

        // Player's eye location is the starting location for the particle
        Location startLoc = player.getEyeLocation();

        // We need to clone() this location, because we will add() to it later.
        Location particleLoc = startLoc.clone();

        World world = startLoc.getWorld(); // We need this later to show the particle

        // dir is the Vector direction (offset from 0,0,0) the player is facing in 3D space
        Vector dir = startLoc.getDirection();

        /* vecOffset is used to determine where the next particle should appear
        We are taking the direction and multiplying it by 0.5 to make it appear 1/2 block
          in its continuing Vector direction.
        NOTE: We have to clone() because multiply() modifies the original variable!
        For a straight beam, we only need to calculate this once, as the direction does not change.
        */
        Vector vecOffset = dir.clone().multiply(0.5);

        // This can also be done without the extra "dir" variable:
        // Vector vecOffset = startLoc.getDirection().clone().multiply(0.5);

        List<Entity> damaged = new ArrayList<>();
        for(int i = 0; i <= 25; i++) {
            // Damage Entities
            for (Entity entity : world.getNearbyEntities(particleLoc, 5, 5, 5)) {

                if(!entity.hasMetadata("skyblockEntityId")) continue;
                if(entity instanceof Player && !entity.hasMetadata("NPC")) continue;

                SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(entity);

                /* Define the bounding box of the particle.
                        We will use 0.25 here, since the particle is moving 0.5 blocks each time.
                        That means the particle won't miss very small entities like chickens or bats,
                          as the particle bounding box covers 1/2 of the movement distance.
                         */
                Vector particleMinVector = new Vector(
                        particleLoc.getX() - 0.25,
                        particleLoc.getY() - 0.25,
                        particleLoc.getZ() - 0.25);
                Vector particleMaxVector = new Vector(
                        particleLoc.getX() + 0.25,
                        particleLoc.getY() + 0.25,
                        particleLoc.getZ() + 0.25);

                // Now use a spigot API call to determine if the particle is inside the entity's hitbox
                if(entity.getBoundingBox().overlaps(particleMinVector, particleMaxVector)){
                    if(!damaged.contains(entity)) {
                        damaged.add(entity);
                    } else {
                        continue;
                    }

                    Calculator.damage(sbEntity, sbPlayer, false);
                }
            }

            // Now we add the direction vector offset to the particle's current location
            particleLoc.add(vecOffset);

            // Display the particle in the new location
            world.spawnParticle(Particle.FIREWORKS_SPARK, particleLoc, 0);
        }
    }

}
