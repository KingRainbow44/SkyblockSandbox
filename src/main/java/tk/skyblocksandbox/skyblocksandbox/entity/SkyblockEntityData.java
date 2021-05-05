package tk.skyblocksandbox.skyblocksandbox.entity;

public final class SkyblockEntityData {

    /*
     * Stats
     */
    public long health = 100;
    public float defense = 0;
    public int damage = 0;
    public int speed = 100;

    public int level = 1;

    public int vanillaHealth = 1;

    /*
     * Flags
     */
    public boolean isNpc = false;
    public boolean isBoss = false;
    public boolean isUndead = false;
    public boolean isArthropod = false;

    public boolean canTakeKnockback = true;

    /*
     * Variables
     */
    public String entityName = "";

}