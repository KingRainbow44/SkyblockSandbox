package tk.skyblocksandbox.skyblocksandbox.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import tk.skyblocksandbox.permitable.PermissionManager;
import tk.skyblocksandbox.permitable.PermissionModule;
import tk.skyblocksandbox.permitable.rank.PermitableRank;
import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;
import tk.skyblocksandbox.skyblocksandbox.util.Utility;

import java.nio.charset.StandardCharsets;
import java.util.*;

public final class SkyblockPlayerPermissions {

    private final SkyblockPlayer player;

    public SkyblockPlayerPermissions(SkyblockPlayer sbPlayer) {
        player = sbPlayer;
        PermissionModule.getPermissionManager().addPermissionAttachment(sbPlayer);
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
     * Extra Permissions
     */
    public Collection<String> permissions = new ArrayList<>();

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

        JsonArray permissionsArray = new JsonArray();
        for(String permission : this.permissions) {
            permissionsArray.add(permission);
        }
        permissions.add("extraPermissions", permissionsArray);

        return Base64.getUrlEncoder().encodeToString(permissions.toString().getBytes(StandardCharsets.UTF_8));
    }

    public void importData(Object previousData) {
        String rawData;
        if(previousData.toString() == null || previousData.toString().equals("{}")) {
            return;
        } else {
            rawData = StringEscapeUtils.unescapeJava(previousData.toString()
                    .substring(1)
                    .substring(0, previousData.toString().length() - 2));
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

        JsonArray permissionsArray = (JsonArray) getOrDefault("extraPermissions", arrayData, JsonArray.class);
        if(permissionsArray != null) {
            for (JsonElement jsonElement : permissionsArray) {
                grantPermission(jsonElement.getAsString());
            }
        }
    }

    public Object getOrDefault(String constant, JsonObject array, Class<?> type) {
        if(array.get(constant) == null) {
            if(type == String.class) {
                return "";
            } else if (type == Integer.class) {
                return 0;
            } else if (type == JsonArray.class) {
                return new JsonArray();
            }
        }

        if (type == String.class) {
            return array.get(constant).getAsString();
        } else if (type == Integer.class) {
            return array.get(constant).getAsInt();
        } else if (type == JsonArray.class) {
            return array.get(constant).getAsJsonArray();
        } else {
            return array.get(constant);
        }
    }

    public void grantPermission(String permissionNode) {
        PermissionManager manager = PermissionModule.getPermissionManager();
        manager.addPermission(player, permissionNode);

        permissions.add(permissionNode);
    }

    public void revokePermission(String permissionNode) {
        PermissionManager manager = PermissionModule.getPermissionManager();
        manager.removePermission(player, permissionNode);

        permissions.remove(permissionNode);
    }

    public void addPermissions(PermitableRank.AvailableRanks rank) {
        for(String permission : PermitableRank.getRankByEnum(rank).getPermissions()) {
            grantPermission(permission);
        }
    }

}
