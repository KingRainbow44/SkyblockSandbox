package tk.skyblocksandbox.permitable;

import org.bukkit.permissions.PermissionAttachment;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.player.SkyblockPlayer;

import java.util.HashMap;
import java.util.Map;

public final class PermissionManager {

    private static final Map<SkyblockPlayer, PermissionAttachment> playerPermissions = new HashMap<>();

    public void addPermissionAttachment(SkyblockPlayer sbPlayer) {
        playerPermissions.put(
                sbPlayer,
                sbPlayer.getBukkitPlayer().addAttachment(SkyblockSandbox.getInstance())
        );
    }

    public void addPermission(SkyblockPlayer sbPlayer, String permission) {
        PermissionAttachment permissionAttachment = getPermissions(sbPlayer);
        permissionAttachment.setPermission(permission, true);
    }

    public void removePermission(SkyblockPlayer sbPlayer, String permission) {
        PermissionAttachment permissionAttachment = getPermissions(sbPlayer);
        permissionAttachment.unsetPermission(permission);
    }

    public PermissionAttachment getPermissions(SkyblockPlayer sbPlayer) {
        return playerPermissions.getOrDefault(
                sbPlayer,
                sbPlayer.getBukkitPlayer().addAttachment(SkyblockSandbox.getInstance())
        );
    }

}
