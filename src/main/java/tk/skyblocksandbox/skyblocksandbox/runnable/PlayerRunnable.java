package tk.skyblocksandbox.skyblocksandbox.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tk.skyblocksandbox.skyblocksandbox.item.BukkitSandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;
import tk.skyblocksandbox.skyblocksandbox.item.SandboxItemStack;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class PlayerRunnable implements Runnable {

    private int ticks = -1;

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        ticks++;

        for(Player bukkitPlayer : Bukkit.getOnlinePlayers()) {
            SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(bukkitPlayer);
            PlayerInventory inventory = sbPlayer.getBukkitPlayer().getInventory();

            if(inventory.getHelmet() != null) {
                ItemStack item = inventory.getHelmet();
                if(SandboxItemStack.isSandboxItem(item)) {
                    SandboxItem sbItem = SandboxItemStack.toSandboxItem(item);
                    sbItem.armorAbility(sbPlayer);
                }
            }

            if(inventory.getChestplate() != null) {
                ItemStack item = inventory.getChestplate();
                if(SandboxItemStack.isSandboxItem(item)) {
                    SandboxItem sbItem = SandboxItemStack.toSandboxItem(item);
                    sbItem.armorAbility(sbPlayer);
                }
            }

            if(inventory.getLeggings() != null) {
                ItemStack item = inventory.getLeggings();
                if(SandboxItemStack.isSandboxItem(item)) {
                    SandboxItem sbItem = SandboxItemStack.toSandboxItem(item);
                    sbItem.armorAbility(sbPlayer);
                }
            }

            if(inventory.getBoots() != null) {
                ItemStack item = inventory.getBoots();
                if(SandboxItemStack.isSandboxItem(item)) {
                    SandboxItem sbItem = SandboxItemStack.toSandboxItem(item);
                    sbItem.armorAbility(sbPlayer);
                }
            }
        }

        if(ticks % 20 == 0) { // Every second, run code:

            for(Player bukkitPlayer : Bukkit.getOnlinePlayers()) {
                SkyblockPlayer sbPlayer = SkyblockPlayer.getSkyblockPlayer(bukkitPlayer);

                sbPlayer.getScoreboard().updateScoreboard();

                if(sbPlayer.getPlayerData().getAbsorptionHealth() == 0) sbPlayer.getPlayerData().heal(
                        (int) (Math.round(1.5 + (sbPlayer.getPlayerData().getFinalMaxHealth() * 0.01)) / 2)
                );

                sbPlayer.getPlayerData().addMana( sbPlayer.getPlayerData().getFinalIntelligence() / 50 );

                sbPlayer.updateHud();
                if(sbPlayer.getBukkitPlayer().getInventory().getItemInMainHand().getType() != Material.AIR && !SandboxItemStack.isSandboxItem(sbPlayer.getBukkitPlayer().getInventory().getItemInMainHand())) {
                    sbPlayer.getBukkitPlayer().getInventory().setItemInMainHand(new BukkitSandboxItem(sbPlayer.getBukkitPlayer().getInventory().getItemInMainHand()).create());
                }

                float speed = (sbPlayer.getPlayerData().speed * 2) / 1000f;
                if(speed > 1.0) speed = 1.0f;
                if(speed < 0.0) {
                    speed = 0.2f;
                    sbPlayer.getPlayerData().speed = 100;
                }

                bukkitPlayer.setWalkSpeed(speed);
            }
        }
    }
}
