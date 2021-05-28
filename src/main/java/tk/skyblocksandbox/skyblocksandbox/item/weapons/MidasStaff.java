package tk.skyblocksandbox.skyblocksandbox.item.weapons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.entity.SandboxEntity;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemData;
import tk.skyblocksandbox.skyblocksandbox.item.SkyblockItemIds;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;
import tk.skyblocksandbox.skyblocksandbox.util.Calculator;
import tk.skyblocksandbox.skyblocksandbox.util.Cuboid;
import tk.skyblocksandbox.skyblocksandbox.util.Lore;

import java.util.ArrayList;
import java.util.Collection;

public final class MidasStaff extends SandboxItem {

    public MidasStaff() {
        super(Material.GOLDEN_SHOVEL, "Midas Staff", SkyblockItemIds.MIDAS_STAFF);
    }

    @Override
    public Collection<String> getLore() {
        return new Lore().genericLore(this);
    }

    @Override
    public SkyblockItemData getItemData() {
        SkyblockItemData itemData = new SkyblockItemData();

        // Abilities - START \\
        itemData.hasAbility = true;
        itemData.abilityName = "Molten Wave";
        itemData.abilityDescription = "&7Cast a wave of molten gold in\n" +
                "the direction you are facing!\n" +
                "Deals up to &c26,000.0\n" +
                "&7damage.";
        itemData.abilityCost = 500;
        itemData.abilityCooldown = 1;
        itemData.abilityTrigger = RIGHT_CLICK_TRIGGER;

        itemData.hasSecondAbility = true;
        itemData.abilityName2 = "Greed";
        itemData.abilityDescription2 = "&7The &3ability damage bonus &7of\n" +
                "&7this item is dependent on the\n" +
                "&7price paid for it at the &5Dark\n" +
                "&5Auction&7!\n" +
                "&7The maximum bonus of this item\n" +
                "&7is &326,000 &7if the bid was\n" +
                "&6100,000,000 Coins &7or higher!\n" +
                " \n" +
                "&7Price paid: &6100,000,000 Coins\n" +
                "&7Base Ability Damage Bonus: &326,000";
        // Abilities - END \\

        itemData.baseDamage = 130;
        itemData.baseStrength = 150;
        itemData.baseIntelligence = 50;

        itemData.rarity = LEGENDARY;
        itemData.itemType = SWORD;

        return itemData;
    }

    @Override
    public void ability(int action, SkyblockPlayer sbPlayer) {
        if (action != INTERACT_RIGHT_CLICK || !sbPlayer.manaCheck(500, "Molten Wave")) return;

        Player player = sbPlayer.getBukkitPlayer();
        World world = player.getWorld();

        Vector direction = player.getLocation().getDirection();
        Location nextLocation = player.getLocation().add(direction);
        Block nextBlock = nextLocation.getBlock();

        Collection<Location> possibleBlocks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Vector nextDirection = player.getLocation().getDirection().multiply(i);
            possibleBlocks.add(player.getLocation().add(nextDirection));
        }

        world.spawnParticle(Particle.BLOCK_DUST, nextLocation, 1, 1, 0.1, 0.1, Material.GOLD_BLOCK.createBlockData());
        for (Block block : getBlocks(player, nextBlock)) {
            world.spawnParticle(Particle.BLOCK_DUST, block.getLocation(), 1, 1, 0.1, 0.1, Material.GOLD_BLOCK.createBlockData());
        }

        new BukkitRunnable() {
            private int ticks = -1;

            @Override
            public void run() {
                ticks++;

                if (ticks >= 64) {
                    cancel();
                    return;
                }

                Collection<Entity> damaged = new ArrayList<>();
                for(Object rawLoc : possibleBlocks.toArray()) {
                    if(rawLoc instanceof Location) {
                        Location location = (Location) rawLoc;
                        world.spawnParticle(Particle.BLOCK_DUST, location, 1, 1, 0.1, 0.1, Material.GOLD_BLOCK.createBlockData());
                        for(Entity entity : world.getNearbyEntities(location, 1, 1, 1)) {
                            if(entity.hasMetadata("skyblockEntityId") && !damaged.contains(entity)) {
                                damaged.add(entity);
                                SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(entity);

                                long damage = Calculator.damage(sbPlayer, 32000, 0.3);
                                Calculator.damage(sbEntity, damage, true);
                            }
                        }

                        for (Block block : getBlocks(player, location.getBlock())) {
                            world.spawnParticle(Particle.BLOCK_DUST, block.getLocation(), 1, 1, 0.1, 0.1, Material.GOLD_BLOCK.createBlockData());
                            for(Entity entity : world.getNearbyEntities(block.getLocation(), 1, 1, 1)) {
                                if(entity.hasMetadata("skyblockEntityId") && !damaged.contains(entity)) {
                                    damaged.add(entity);
                                    SandboxEntity sbEntity = SandboxEntity.getSandboxEntity(entity);

                                    long damage = Calculator.damage(sbPlayer, 32000, 0.3);
                                    Calculator.damage(sbEntity, damage, true);
                                }
                            }
                        }
                    }
                }


            }
        }.runTaskTimer(SkyblockSandbox.getInstance(), 2L, 1L);
    }

    private Collection<Block> getBlocks(Player player, Block center) {
        Collection<Block> blocks = new ArrayList<>();
        blocks.add(getBlockFrom(player, center, Directions.LEFT));
        blocks.add(getBlockFrom(player, center, Directions.RIGHT));

        return blocks;
    }

    private Block getBlockFrom(Player player, Block centerBlock, Directions direction) {
        BlockFace facing = fromDirection(player, centerBlock);
        switch(facing) {
            default:
            case NORTH:
            case NORTH_EAST:
            case NORTH_WEST:
                if(direction == Directions.LEFT) {
                    return centerBlock.getRelative(-1, 0, 0);
                } else {
                    return centerBlock.getRelative(1, 0, 0);
                }
            case SOUTH:
            case SOUTH_EAST:
            case SOUTH_WEST:
                if(direction == Directions.LEFT) {
                    return centerBlock.getRelative(1, 0, 0);
                } else {
                    return centerBlock.getRelative(-1, 0, 0);
                }

            case EAST:
            case EAST_NORTH_EAST:
            case EAST_SOUTH_EAST:
                if(direction == Directions.LEFT) {
                    return centerBlock.getRelative(0, 0, -1);
                } else {
                    return centerBlock.getRelative(0, 0, 1);
                }
            case WEST:
            case WEST_NORTH_WEST:
            case WEST_SOUTH_WEST:
                if(direction == Directions.LEFT) {
                    return centerBlock.getRelative(0, 0, 1);
                } else {
                    return centerBlock.getRelative(0, 0, -1);
                }
        }
    }

    private BlockFace fromDirection(Player player, Block targetBlock) {
        float direction = (float) Math.toDegrees(Math.atan2(player.getLocation().getBlockX() - targetBlock.getX(), targetBlock.getZ() - player.getLocation().getBlockZ()));
        direction = direction % 360;

        if(direction < 0)
            direction += 360;

        direction = Math.round(direction / 45);

        switch((int) direction){
            default:
            case 0:
                return BlockFace.WEST;
            case 1:
                return BlockFace.NORTH_WEST;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.NORTH_EAST;
            case 4:
                return BlockFace.EAST;
            case 5:
                return BlockFace.SOUTH_EAST;
            case 6:
                return BlockFace.SOUTH;
            case 7:
                return BlockFace.SOUTH_WEST;
        }
    }

    private enum Directions {
        LEFT,
        RIGHT
    }
}
