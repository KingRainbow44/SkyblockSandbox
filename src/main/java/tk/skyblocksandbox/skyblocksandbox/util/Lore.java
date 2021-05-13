package tk.skyblocksandbox.skyblocksandbox.util;

import tk.skyblocksandbox.skyblocksandbox.item.SandboxItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class Lore {

    /*
     * Lore:
     * Used for generating item lores.
     * Unlike most Utility classes, Lore is meant to be instanced.
     */

    private Object insertAt;
    private String[] extraLore;

    private boolean hasExtraLore = false;

    public Lore(Object insertAt, String... extraLore) {
        this.insertAt = insertAt;
        this.extraLore = extraLore;

        hasExtraLore = true;
    }

    public Lore() {

    }

    public Collection<String> genericLore(SandboxItem item) {

        /*
         * genericLore:
         * - Used in Skyblock Item
         * - Used for creating a generic lore for basic items.
         */

        int insertLocation = 0;
        if(hasExtraLore) {
            if(insertAt instanceof Integer) {
                insertLocation = (Integer)  insertAt;
            } else if (insertAt instanceof String) {
                switch(insertAt.toString()) {
                    default:
                    case "ending":
                        insertLocation = -1;
                        break;
                    case "start":
                        insertLocation = 0;
                        break;
                }
            }
        }

        ArrayList<String> finalLore = new ArrayList<>();

        int line = 0;
        if(hasExtraLore && insertLocation == 0) {
            finalLore.addAll(Arrays.asList(extraLore));
            line = (extraLore.length - 1);
        }

        /*
         * Lore Generation: Percentages
         */
        boolean addBreak1 = false;
        if(item.getItemData().isDungeonItem && !item.getItemData().isMaterial) {
            finalLore.add(line, Utility.colorize("&7Gear Score: &d--- &8(---)"));
            line++; addBreak1 = true;
        }

        if(item.getItemData().baseDamage > 0) {
            finalLore.add(line, Utility.colorize("&7Damage: &c+" + item.getItemData().finalDamage()));
            line++; addBreak1 = true;
        }

        if(item.getItemData().baseStrength > 0) {
            finalLore.add(line, Utility.colorize("&7Strength: &c+" + item.getItemData().finalStrength()));
            line++; addBreak1 = true;
        }

        if(item.getItemData().baseCriticalStrikeChance > 0) {
            finalLore.add(line, Utility.colorize("&7Crit Chance: &c+" + item.getItemData().finalCritChance() + "%"));
            line++; addBreak1 = true;
        }

        if(item.getItemData().baseCriticalDamage > 0) {
            finalLore.add(line, Utility.colorize("&7Crit Damage: &c+" + item.getItemData().finalCritDamage() + "%"));
            line++; addBreak1 = true;
        }

        if(item.getItemData().baseBonusAttackSpeed > 0) {
            finalLore.add(line, Utility.colorize("&7Bonus Attack Speed: &c+" + item.getItemData().finalAttackSpeed() + "%"));
            line++; addBreak1 = true;
        }

        if(item.getItemData().baseSeaCreatureChance > 0) {
            finalLore.add(line, Utility.colorize("&7Sea Creature Chance: &c+" + item.getItemData().finalSeaCreatureChance() + "%"));
            line++; addBreak1 = true;
        }

        if(item.getItemData().baseAbilityDamage > 0) {
            finalLore.add(line, Utility.colorize("&7Ability Damage: &c+" + item.getItemData().finalAbilityDamage() + "%"));
            line++; addBreak1 = true;
        }


        // Break
        if(line > 1 && addBreak1) {
            finalLore.add(line, " ");
            line++;
        }
        // Break

        /*
         * Lore Generation: Integers
         */
        boolean addBreak2 = false;
        if(item.getItemData().baseHealth > 0) {
            finalLore.add(line, Utility.colorize("&7Health: &a+" + item.getItemData().finalHealth() + " HP"));
            line++; addBreak2 = true;
        }

        if(item.getItemData().baseDefense > 0) {
            finalLore.add(line, Utility.colorize("&7Defense: &a+" + item.getItemData().finalDefense()));
            line++; addBreak2 = true;
        }

        if(item.getItemData().baseSpeed > 0) {
            finalLore.add(line, Utility.colorize("&7Speed: &a+" + item.getItemData().finalSpeed()));
            line++; addBreak2 = true;
        }

        if(item.getItemData().baseIntelligence > 0) {
            finalLore.add(line, Utility.colorize("&7Intelligence: &a+" + item.getItemData().finalIntelligence()));
            line++; addBreak2 = true;
        }

        if(item.getItemData().baseTrueDefense > 0) {
            finalLore.add(line, Utility.colorize("&7True Defense: &a+" + item.getItemData().finalTrueDefense()));
            line++; addBreak2 = true;
        }

        if(item.getItemData().baseMagicFind > 0) {
            finalLore.add(line, Utility.colorize("&7Magic Find: &a+" + item.getItemData().finalMagicFind()));
            line++; addBreak2 = true;
        }

        if(item.getItemData().basePetLuck > 0) {
            finalLore.add(line, Utility.colorize("&7Pet Luck: &a+" + item.getItemData().finalPetLuck()));
            line++; addBreak2 = true;
        }

        if(item.getItemData().baseFerocity > 0) {
            finalLore.add(line, Utility.colorize("&7Ferocity: &a+" + item.getItemData().finalFerocity()));
            line++; addBreak2 = true;
        }


        // Break
        if(line > 1 && addBreak2) {
            finalLore.add(line, " ");
            line++;
        }
        // Break

        /*
         * Abilities
         */
        if(item.getItemData().hasAbility) {
            String[] lines = item.getItemData().abilityDescription.split("\n");
            switch(item.getItemData().abilityTrigger) {
                case 0:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName)); line++;
                    break;
                case 1:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName + " &e&lLEFT CLICK")); line++;
                    break;
                case 2:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName + " &e&lRIGHT CLICK")); line++;
                    break;
                case 3:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName + " &e&lSNEAK")); line++;
                    break;
                case 4:
                    finalLore.add(line, Utility.colorize("&6Full Set Bonus: " + item.getItemData().abilityName)); line++;
                    break;
            }

            for(String loreLine : lines) {
                finalLore.add(line, Utility.colorize("&7" + loreLine));
                line++;
            }

            if(item.getItemData().abilityCost > 0) {
                finalLore.add(line, Utility.colorize("&8Mana cost: &9" + item.getItemData().abilityCost)); line++;
            }
            if(item.getItemData().abilityCooldown > 0) {
                finalLore.add(line, Utility.colorize("&8Cooldown: &a" + item.getItemData().abilityCooldown + "&as")); line++;
            }
        }

        if(item.getItemData().hasSecondAbility) {
            finalLore.add(line, " "); line++;

            String[] lines = item.getItemData().abilityDescription2.split("\n");
            switch(item.getItemData().abilityTrigger2) {
                case 0:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName2)); line++;
                    break;
                case 1:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName2 + " &e&lLEFT CLICK")); line++;
                    break;
                case 2:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName2 + " &e&lRIGHT CLICK")); line++;
                    break;
                case 3:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName2 + " &e&lSNEAK")); line++;
                    break;
            }

            for(String loreLine : lines) {

                finalLore.add(line, Utility.colorize("&7" + loreLine));
                line++;
            }

            if(item.getItemData().abilityCost2 > 0) {
                finalLore.add(line, Utility.colorize("&8Mana cost: &9" + item.getItemData().abilityCost2)); line++;
            }
            if(item.getItemData().abilityCooldown2 > 0) {
                finalLore.add(line, Utility.colorize("&8Cooldown: &a" + item.getItemData().abilityCooldown2 + "&as")); line++;
            }
        }

        if(item.getItemData().hasThirdAbility) {
            finalLore.add(line, " "); line++;

            String[] lines = item.getItemData().abilityDescription3.split("\n");
            switch(item.getItemData().abilityTrigger3) {
                case 0:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName3)); line++;
                    break;
                case 1:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName3 + " &e&lLEFT CLICK")); line++;
                    break;
                case 2:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName3 + " &e&lRIGHT CLICK")); line++;
                    break;
                case 3:
                    finalLore.add(line, Utility.colorize("&6Item Ability: " + item.getItemData().abilityName3 + " &e&lSNEAK")); line++;
                    break;
            }

            for(String loreLine : lines) {

                finalLore.add(line, Utility.colorize("&7" + loreLine));
                line++;
            }

            if(item.getItemData().abilityCost3 > 0) {
                finalLore.add(line, Utility.colorize("&8Mana cost: &9" + item.getItemData().abilityCost3)); line++;
            }
            if(item.getItemData().abilityCooldown3 > 0) {
                finalLore.add(line, Utility.colorize("&8Cooldown: &a" + item.getItemData().abilityCooldown3 + "&as")); line++;
            }
        }

        // Break
        if(line > 2) {
            finalLore.add(line, " ");
            line++;
        }
        // Break

        /*
         * Reforge/Brewing Ingredient
         */
        if(item.getItemData().itemType == SandboxItem.BREWING_INGREDIENT) {
            finalLore.add(line, Utility.colorize("&8Brewing Ingredient")); line++;
        } else if (item.getItemData().canReforge) {
            finalLore.add(line, Utility.colorize("&8This item can be reforged!")); line++;
        }

        if(item.getItemData().isMaterial) {
            finalLore.add(line, Utility.colorize("&eRight-click to view recipes!")); line++;
            finalLore.add(line, ""); line++;
        }

        String color;
        if(item.getItemData().isDungeonItem) {
            switch(item.getItemData().rarity) {
                default:
                case SandboxItem.COMMON:
                    color = Utility.colorize("&f&lCOMMON DUNGEON");
                    break;
                case SandboxItem.UNCOMMON:
                    color = Utility.colorize("&a&lUNCOMMON DUNGEON");
                    break;
                case SandboxItem.RARE:
                    color = Utility.colorize("&9&lRARE DUNGEON");
                    break;
                case SandboxItem.EPIC:
                    color = Utility.colorize("&5&lEPIC DUNGEON");
                    break;
                case SandboxItem.LEGENDARY:
                    color = Utility.colorize("&6&lLEGENDARY DUNGEON");
                    break;
                case SandboxItem.MYTHIC:
                    color = Utility.colorize("&d&lMYTHIC DUNGEON");
                    break;
                case SandboxItem.SPECIAL:
                    color = Utility.colorize("&c&lSPECIAL DUNGEON");
                    break;
                case SandboxItem.VERY_SPEICAL:
                    color = Utility.colorize("&c&lVERY SPECIAL DUNGEON");
                    break;
                case SandboxItem.SUPREME:
                    color = Utility.colorize("&4&lSUPREME DUNGEON");
                    break;
            }
        } else {
            switch(item.getItemData().rarity) {
                default:
                case SandboxItem.COMMON:
                    color = Utility.colorize("&f&lCOMMON");
                    break;
                case SandboxItem.UNCOMMON:
                    color = Utility.colorize("&a&lUNCOMMON");
                    break;
                case SandboxItem.RARE:
                    color = Utility.colorize("&9&lRARE");
                    break;
                case SandboxItem.EPIC:
                    color = Utility.colorize("&5&lEPIC");
                    break;
                case SandboxItem.LEGENDARY:
                    color = Utility.colorize("&6&lLEGENDARY");
                    break;
                case SandboxItem.MYTHIC:
                    color = Utility.colorize("&d&lMYTHIC");
                    break;
                case SandboxItem.SPECIAL:
                    color = Utility.colorize("&c&lSPECIAL");
                    break;
                case SandboxItem.VERY_SPEICAL:
                    color = Utility.colorize("&c&lVERY SPECIAL");
                    break;
                case SandboxItem.SUPREME:
                    color = Utility.colorize("&4&lSUPREME");
                    break;
            }
        }

        switch(item.getItemData().itemType) {
            default:
            case SandboxItem.BREWING_INGREDIENT:
            case SandboxItem.OTHER:
                finalLore.add(line, Utility.colorize(color));
                break;
            case SandboxItem.ITEM:
                finalLore.add(line, Utility.colorize(color + " ITEM"));
                break;
            case SandboxItem.SWORD:
                finalLore.add(line, Utility.colorize(color + " SWORD"));
                break;
            case SandboxItem.BOW:
                finalLore.add(line, Utility.colorize(color + " BOW"));
                break;
            case SandboxItem.PICKAXE:
                finalLore.add(line, Utility.colorize(color + " PICKAXE"));
                break;
            case SandboxItem.AXE:
                finalLore.add(line, Utility.colorize(color + " AXE"));
                break;
            case SandboxItem.SHOVEL:
                finalLore.add(line, Utility.colorize(color + " SHOVEL"));
                break;
            case SandboxItem.HOE:
                finalLore.add(line, Utility.colorize(color + " HOE"));
                break;
            case SandboxItem.SHEARS:
                finalLore.add(line, Utility.colorize(color + " SHEARS"));
                break;
            case SandboxItem.ACCESSORY:
                finalLore.add(line, Utility.colorize(color + " ACCESSORY"));
                break;
            case SandboxItem.REFORGE_STONE:
                finalLore.add(line, Utility.colorize(color + " REFORGE STONE"));
                break;
            case SandboxItem.FISHING_ROD:
                finalLore.add(line, Utility.colorize(color + " FISHING ROD"));
                break;
            case SandboxItem.HELMET:
                finalLore.add(line, Utility.colorize(color + " HELMET"));
                break;
            case SandboxItem.CHESTPLATE:
                finalLore.add(line, Utility.colorize(color + " CHESTPLATE"));
                break;
            case SandboxItem.LEGGINGS:
                finalLore.add(line, Utility.colorize(color + " LEGGINGS"));
                break;
            case SandboxItem.BOOTS:
                finalLore.add(line, Utility.colorize(color + " BOOTS"));
                break;
        }

        if(hasExtraLore && insertLocation > 0) {
            finalLore.addAll(insertLocation, Arrays.asList(extraLore));
        }

        return finalLore;
    }

    public Collection<String> advancedLore(SandboxItem item) {
        return null;
    }

}
