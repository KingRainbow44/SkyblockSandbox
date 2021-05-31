package tk.skyblocksandbox.skyblocksandbox.util;

import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class ItemSerializer {

    /**
     * @param item - The ItemStack to be converted into a string
     * @return The String that contains the ItemStack (will return null if anything goes wrong)
     */
    public static String convertItemStackToString(ItemStack item) {
        String serializedObject;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(item);
            so.flush();
            serializedObject = bo.toString();
            return serializedObject;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * @param data - The String to be converted into an ItemStack
     * @return The ItemStack Array obtained from the string (will return void should anything go wrong)
     */
    public static ItemStack convertStringToItemStack(String data) {
        try {
            byte b[] = data.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            ItemStack obj = (ItemStack) si.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
