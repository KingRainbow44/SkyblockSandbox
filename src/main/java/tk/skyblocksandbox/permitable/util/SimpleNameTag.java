package tk.skyblocksandbox.permitable.util;

import org.bukkit.ChatColor;

public final class SimpleNameTag implements NameTag {

    private final String text;

    SimpleNameTag(String text) {
        this.text = ChatColor.translateAlternateColorCodes('&', text);
    }

    public String getText() {
        return this.text;
    }
}
