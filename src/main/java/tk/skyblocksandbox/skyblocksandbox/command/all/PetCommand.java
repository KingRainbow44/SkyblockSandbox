package tk.skyblocksandbox.skyblocksandbox.command.all;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.command.SkyblockCommand;
import tk.skyblocksandbox.skyblocksandbox.menu.MenuFactory;
import tk.skyblocksandbox.skyblocksandbox.pet.SkyblockPetInstance;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

public final class PetCommand extends SkyblockCommand {

    public PetCommand() {
        super("pet", new String[]{"modifypet", "changepet"}, "Change and Modify your Pets!");
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if(isConsoleCommandSender(commandSender)) {
            commandSender.sendMessage(ChatColor.RED + "Execute this in-game!");
            return true;
        }

        SkyblockPlayer sbPlayer = getSkyblockPlayer(commandSender);
        Player player = sbPlayer.getBukkitPlayer();

        ItemStack pet = player.getInventory().getItemInMainHand();

        switch(args.length) {
            default:
            case 0:
                sbPlayer.sendMessages("&cInvalid argument(s). Usage: &e/pet {info|modify}");
                return true;

            case 1:
                switch(args[0]) {
                    default:
                        sbPlayer.sendMessages("&cInvalid argument(s). Usage: &e/pet {info|modify}");
                        return true;
                    case "info":
                        if(!SkyblockPetInstance.isPet(pet)) {
                            sbPlayer.sendMessages("&cPlease hold a pet!");
                            return true;
                        }

                        SkyblockPetInstance petInstance = new SkyblockPetInstance(pet);
                        sbPlayer.sendMessages(
                                "&aPet Name: &e" + petInstance.getPetName(),
                                "&aPet Level: &e" + petInstance.getPetLevel(),
                                "&aPet Experience: &e" + petInstance.getPetExperience()
                        );
                        return true;
                    case "modify":
                        if(!SkyblockPetInstance.isPet(pet)) {
                            sbPlayer.sendMessages("&cPlease hold a pet!");
                            return true;
                        }

                        SkyblockSandbox.getMenuFactory().serveMenu(sbPlayer, MenuFactory.MenuList.SKYBLOCK_MENU_PET_CREATOR);
                        return true;
                }

        }
    }
}
