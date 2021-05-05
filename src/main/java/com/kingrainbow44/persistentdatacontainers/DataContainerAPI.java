package com.kingrainbow44.persistentdatacontainers;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class DataContainerAPI {

    /**
     * A quick-check for both DataContainerAPI#valid() and DataContainerAPI#has().
     * @param itemStack ItemStack
     * @param plugin JavaPlugin
     * @param stringKey String
     * @param type PersistentDataType
     * @return boolean
     */
    public static boolean validityCheck(ItemStack itemStack, JavaPlugin plugin, String stringKey, PersistentDataType<?, ?> type) {
        if(!valid(itemStack)) return false;
        return has(itemStack, plugin, stringKey, type);
    }

    /**
     * Checks if an ItemStack instance has an item meta.
     * @param itemStack ItemStack
     * @return boolean
     */
    public static boolean valid(ItemStack itemStack) {
        return itemStack.getItemMeta() != null;
    }

    /**
     * A user-friendly PersistentDataContainer#has() method.
     * Returns the value of PersistentDataContainer#has() using the NamespacedKey.
     * @param itemStack ItemStack
     * @param plugin JavaPlugin
     * @param stringKey String
     * @param type PersistentDataType
     * @return boolean
     */
    public static boolean has(ItemStack itemStack, JavaPlugin plugin, String stringKey, PersistentDataType<?, ?> type) {
        NamespacedKey key = new NamespacedKey(plugin, stringKey);
        if(!valid(itemStack)) return false;

        return itemStack.getItemMeta().getPersistentDataContainer().has(key, type);
    }

    /**
     * A user-friendly PersistentDataContainer#has() method.
     * Returns the value of PersistentDataContainer#has() using the NamespacedKey.
     * @param entity Entity
     * @param plugin JavaPlugin
     * @param stringKey String
     * @param type PersistentDataType
     * @return boolean
     */
    public static boolean has(Entity entity, JavaPlugin plugin, String stringKey, PersistentDataType<?, ?> type) {
        NamespacedKey key = new NamespacedKey(plugin, stringKey);
        return entity.getPersistentDataContainer().has(key, type);
    }

    /**
     * A user-friendly PersistentDataContainer#set() method.
     * Returns false if the data wasn't a type of String, Integer, Byte, Long, Short, Double, or Float.
     * @param dataContainer PersistentDataContainer
     * @param plugin JavaPlugin
     * @param stringKey String
     * @param data Object
     */
    public static boolean set(PersistentDataContainer dataContainer, JavaPlugin plugin, String stringKey, Object data) {
        NamespacedKey key = new NamespacedKey(plugin, stringKey);

        if(data instanceof String) {
            dataContainer.set(key, PersistentDataType.STRING, (String) data);
        } else if (data instanceof Integer) {
            dataContainer.set(key, PersistentDataType.INTEGER, (Integer) data);
        } else if (data instanceof Byte) {
            dataContainer.set(key, PersistentDataType.BYTE, (Byte) data);
        } else if (data instanceof Short) {
            dataContainer.set(key, PersistentDataType.SHORT, (Short) data);
        } else if (data instanceof Long) {
            dataContainer.set(key, PersistentDataType.LONG, (Long) data);
        } else if (data instanceof Double) {
            dataContainer.set(key, PersistentDataType.DOUBLE, (Double) data);
        } else if (data instanceof Float) {
            dataContainer.set(key, PersistentDataType.FLOAT, (Float) data);
        } else {
            return false;
        }

        return true;
    }

    /**
     * A user-friendly PersistentDataContainer#get() method.
     * Returns false if the data was null.
     * @param dataContainer PersistentDataContainer
     * @param plugin JavaPlugin
     * @param stringKey String
     * @param dataType PersistentDataType
     * @return Object
     */
    public static Object get(PersistentDataContainer dataContainer, JavaPlugin plugin, String stringKey, PersistentDataType<?, ?> dataType) {
        NamespacedKey key = new NamespacedKey(plugin, stringKey);

        Object finalData = dataContainer.get(key, dataType);
        if(finalData == null) {
            return false;
        }

        return finalData;
    }

}
