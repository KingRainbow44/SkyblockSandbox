package tk.skyblocksandbox.skyblocksandbox.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class SkyblockPlayerPermissions {

    private final SkyblockPlayer sbPlayer;

    public SkyblockPlayerPermissions(SkyblockPlayer player) {
        sbPlayer = player;
    }

    /*
     * Permission Flags
     */
    public boolean buildingAllowed = false;
    public boolean buildingEnabled = false;

    /*
     * Command Permissions
     */
    public boolean commandDebug = false;
    public boolean commandItem = false;
    public boolean commandSummon = false;
    public boolean commandSetBlock = false;

    /*
     * Additional Permissions
     */
    private final Map<String, PermissionAttachment> additionalPermissions = new HashMap<>();

    /*
     * Data Import/Export
     */

    public String exportData() {
        JsonObject permissions = new JsonObject();

        permissions.addProperty("buildingAllowed", buildingAllowed);
        permissions.addProperty("buildingEnabled", buildingEnabled);

        permissions.addProperty("commandDebug", commandDebug);
        permissions.addProperty("commandItem", commandItem);
        permissions.addProperty("commandSummon", commandSummon);
        permissions.addProperty("commandSetBlock", commandSetBlock);

        return Base64.getUrlEncoder().encodeToString(permissions.toString().getBytes());
    }

    public void importData(Object previousData) {
        String rawData;
        if(previousData.toString() == null || previousData.toString().equals("{}")) {
            return;
        } else {
            rawData = previousData.toString();
        }

        byte[] decodedBytes = Base64.getUrlDecoder().decode(rawData);
        String data = new String(decodedBytes);

        JsonElement decodedData = new JsonParser().parse(data);
        JsonObject arrayData = decodedData.getAsJsonObject();

        buildingAllowed = arrayData.get("buildingAllowed").getAsBoolean();
        buildingEnabled = arrayData.get("buildingEnabled").getAsBoolean();

        commandDebug = arrayData.get("commandDebug").getAsBoolean();
        commandItem = arrayData.get("commandItem").getAsBoolean();
        commandSummon = arrayData.get("commandSummon").getAsBoolean();
        commandSetBlock = arrayData.get("commandSetBlock").getAsBoolean();
    }

    public void grantPermission(String permissionNode) {
        Player player = sbPlayer.getBukkitPlayer();

        PermissionAttachment attachment = player.addAttachment(SkyblockSandbox.getInstance());
        additionalPermissions.put(permissionNode, attachment);

        attachment.setPermission(permissionNode, true);
    }

    public void revokePermission(String permissionNode) {
        PermissionAttachment attachment = additionalPermissions.getOrDefault(permissionNode, null);
        if(attachment == null) return;

        attachment.unsetPermission(permissionNode);
    }

}
