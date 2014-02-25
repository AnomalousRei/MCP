package tk.R3creat3.MCP.handlers;

import org.bukkit.ChatColor;
import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.Type;

public class TypeHandler {

    MCP plugin = MCP.getInstance();

    /**
     * Returns the effectiveness of an attack.
     *
     * @param type1      A pokemon's first type.
     * @param type2      A pokemon's second type, if applicable.
     * @param attackType The move type.
     */
    public static double getEffectiveness(Type type1, Type type2, Type attackType) {
        if (attackType == Type.NONE) return 0;
        double effectiveness1 = getEffectivenessForType(type1, attackType);
        double effectiveness2 = 1;
        if (type2 != Type.NONE) {
            effectiveness2 = getEffectivenessForType(type2, attackType);
        }
        if (effectiveness1 == 0 || effectiveness2 == 0) {
            return 0;
        }
        if (effectiveness1 == 1 && effectiveness2 == 1) {
            return 1;
        }
        if (effectiveness1 == 2 && effectiveness2 == 2) {
            return 4;
        }
        if (effectiveness1 == 1 && effectiveness2 == 2) {
            return 2;
        }
        if (effectiveness1 == 2 && effectiveness2 == 1) {
            return 2;
        }
        if (effectiveness1 == 2 && effectiveness2 == 0.5) {
            return 1;
        }
        if (effectiveness1 == 0.5 && effectiveness2 == 2) {
            return 1;
        }
        if (effectiveness1 == 0.5 && effectiveness2 == 0.5) {
            return 0.25;
        }
        return 1;
    }

    private static double getEffectivenessForType(Type type1, Type attackType) {
        if (type1 == Type.NONE) return 800; //'none' types with immunities would be stupid.
        if (type1 == Type.NORMAL) {
            if (attackType == Type.FIGHTING) return 2;
            return 1;
        }
        if (type1 == Type.FIGHTING) {
            if (attackType == Type.FLYING) return 2;
            if (attackType == Type.FAIRY) return 2;
            if (attackType == Type.PSYCHIC) return 2;
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.ROCK) return 0.5;
            return 1;
        }
        if (type1 == Type.FLYING) {
            if (attackType == Type.ELECTRIC) return 2;
            if (attackType == Type.ICE) return 2;
            if (attackType == Type.ROCK) return 2;
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.FIGHTING) return 0.5;
            if (attackType == Type.GRASS) return 0.5;
            if (attackType == Type.GROUND) return 0;
            return 1;
        }
        if (type1 == Type.STEEL) {
            if (attackType == Type.POISON) return 0;
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.FAIRY) return 0.5;
            if (attackType == Type.STEEL) return 0.5;
            if (attackType == Type.ROCK) return 0.5;
            if (attackType == Type.PSYCHIC) return 0.5;
            if (attackType == Type.NORMAL) return 0.5;
            if (attackType == Type.ICE) return 0.5;
            if (attackType == Type.GRASS) return 0.5;
            if (attackType == Type.FLYING) return 0.5;
            if (attackType == Type.DRAGON) return 0.5;
            if (attackType == Type.FIRE) return 2;
            if (attackType == Type.GROUND) return 2;
            if (attackType == Type.FIGHTING) return 2;
            return 1;
        }
        if (type1 == Type.ROCK) {
            if (attackType == Type.NORMAL) return 0.5;
            if (attackType == Type.FIRE) return 0.5;
            if (attackType == Type.FLYING) return 0.5;
            if (attackType == Type.POISON) return 0.5;
            if (attackType == Type.FIGHTING) return 2;
            if (attackType == Type.GRASS) return 2;
            if (attackType == Type.GROUND) return 2;
            if (attackType == Type.STEEL) return 2;
            if (attackType == Type.WATER) return 2;
            return 1;
        }
        if (type1 == Type.GROUND) {
            if (attackType == Type.POISON) return 0.5;
            if (attackType == Type.ROCK) return 0.5;
            if (attackType == Type.WATER) return 2;
            if (attackType == Type.GRASS) return 2;
            if (attackType == Type.ICE) return 2;
            if (attackType == Type.ELECTRIC) return 0;
            return 1;
        }
        if (type1 == Type.ELECTRIC) {
            if (attackType == Type.ELECTRIC) return 0.5;
            if (attackType == Type.FLYING) return 0.5;
            if (attackType == Type.STEEL) return 0.5;
            if (attackType == Type.GROUND) return 0;
            return 1;
        }
        if (type1 == Type.WATER) {
            if (attackType == Type.FIRE) return 0.5;
            if (attackType == Type.ICE) return 0.5;
            if (attackType == Type.STEEL) return 0.5;
            if (attackType == Type.WATER) return 0.5;
            if (attackType == Type.ELECTRIC) return 2;
            if (attackType == Type.GRASS) return 2;
            return 1;
        }
        if (type1 == Type.FIRE) {
            if (attackType == Type.FAIRY) return 0.5;
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.FIRE) return 0.5;
            if (attackType == Type.GRASS) return 0.5;
            if (attackType == Type.ICE) return 0.5;
            if (attackType == Type.STEEL) return 0.5;
            if (attackType == Type.WATER) return 2;
            if (attackType == Type.ROCK) return 2;
            if (attackType == Type.GROUND) return 2;
            return 1;
        }
        if (type1 == Type.PSYCHIC) {
            if (attackType == Type.FIGHTING) return 0.5;
            if (attackType == Type.PSYCHIC) return 0.5;
            if (attackType == Type.DARK) return 2;
            if (attackType == Type.BUG) return 2;
            if (attackType == Type.GHOST) return 2;
            return 1;
        }
        if (type1 == Type.DARK) {
            if (attackType == Type.FIGHTING) return 2;
            if (attackType == Type.BUG) return 2;
            if (attackType == Type.FAIRY) return 2;
            if (attackType == Type.PSYCHIC) return 0;
            if (attackType == Type.DARK) return 0.5;
            if (attackType == Type.GHOST) return 0.5;
            return 1;
        }
        if (type1 == Type.DRAGON) {
            if (attackType == Type.ELECTRIC) return 0.5;
            if (attackType == Type.GRASS) return 0.5;
            if (attackType == Type.FIRE) return 0.5;
            if (attackType == Type.WATER) return 0.5;
            if (attackType == Type.DRAGON) return 0.5;
            if (attackType == Type.ICE) return 0.5;
            if (attackType == Type.FAIRY) return 0.5;
            return 1;
        }
        if (type1 == Type.GHOST) {
            if (attackType == Type.DARK) return 2;
            if (attackType == Type.GHOST) return 2;
            if (attackType == Type.NORMAL) return 0;
            if (attackType == Type.FIGHTING) return 0;
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.POISON) return 0.5;
            return 1;
        }
        if (type1 == Type.FAIRY) {
            if (attackType == Type.DARK) return 0.5;
            if (attackType == Type.FIGHTING) return 0.5;
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.POISON) return 2;
            if (attackType == Type.STEEL) return 2;
            if (attackType == Type.DRAGON) return 0;
            return 1;
        }
        if (type1 == Type.POISON) {
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.FIGHTING) return 0.5;
            if (attackType == Type.POISON) return 0.5;
            if (attackType == Type.FAIRY) return 0.5;
            if (attackType == Type.GRASS) return 0.5;
            if (attackType == Type.GROUND) return 2;
            if (attackType == Type.PSYCHIC) return 2;
            return 1;
        }
        if (type1 == Type.BUG) {
            if (attackType == Type.FIGHTING) return 0.5;
            if (attackType == Type.GRASS) return 0.5;
            if (attackType == Type.GROUND) return 0.5;
            if (attackType == Type.FIRE) return 2;
            if (attackType == Type.FLYING) return 2;
            if (attackType == Type.ROCK) return 2;
            return 1;
        }
        if (type1 == Type.GRASS) {
            if (attackType == Type.DARK) return 0.5;
            if (attackType == Type.FIGHTING) return 0.5;
            if (attackType == Type.BUG) return 0.5;
            if (attackType == Type.POISON) return 2;
            if (attackType == Type.STEEL) return 2;
            if (attackType == Type.DRAGON) return 0;
            return 1;
        }
        return 1;
    }

    public static Type parseType(String s) {
        if (s.equalsIgnoreCase("normal")) return Type.NORMAL;
        if (s.equalsIgnoreCase("fighting")) return Type.FIGHTING;
        if (s.equalsIgnoreCase("flying")) return Type.FLYING;
        if (s.equalsIgnoreCase("steel")) return Type.STEEL;
        if (s.equalsIgnoreCase("rock")) return Type.ROCK;
        if (s.equalsIgnoreCase("ground")) return Type.GROUND;
        if (s.equalsIgnoreCase("electric")) return Type.ELECTRIC;
        if (s.equalsIgnoreCase("water")) return Type.WATER;
        if (s.equalsIgnoreCase("fire")) return Type.FIRE;
        if (s.equalsIgnoreCase("psychic")) return Type.PSYCHIC;
        if (s.equalsIgnoreCase("dark")) return Type.DARK;
        if (s.equalsIgnoreCase("dragon")) return Type.DRAGON;
        if (s.equalsIgnoreCase("ghost")) return Type.GHOST;
        if (s.equalsIgnoreCase("fairy")) return Type.FAIRY;
        if (s.equalsIgnoreCase("poison")) return Type.POISON;
        if (s.equalsIgnoreCase("bug")) return Type.BUG;
        if (s.equalsIgnoreCase("grass")) return Type.GRASS;
        if (s.equalsIgnoreCase("ice")) return Type.ICE;
        return Type.NONE;
    }

    public static ChatColor parseColor(Type t) {
        if (t == Type.BUG) return ChatColor.DARK_GREEN;
        if (t == Type.DARK) return ChatColor.DARK_PURPLE;
        if (t == Type.DRAGON) return ChatColor.DARK_BLUE;
        if (t == Type.ELECTRIC) return ChatColor.YELLOW;
        if (t == Type.FAIRY) return ChatColor.LIGHT_PURPLE;
        if (t == Type.FIGHTING) return ChatColor.DARK_RED;
        if (t == Type.FIRE) return ChatColor.RED;
        if (t == Type.FLYING) return ChatColor.DARK_AQUA;
        if (t == Type.GHOST) return ChatColor.DARK_GRAY;
        if (t == Type.GRASS) return ChatColor.GREEN;
        if (t == Type.GROUND) return ChatColor.GOLD;
        if (t == Type.ICE) return ChatColor.AQUA;
        if (t == Type.NONE) return ChatColor.WHITE;
        if (t == Type.NORMAL) return ChatColor.WHITE;
        if (t == Type.POISON) return ChatColor.DARK_PURPLE;
        if (t == Type.PSYCHIC) return ChatColor.LIGHT_PURPLE;
        if (t == Type.ROCK) return ChatColor.GOLD;
        if (t == Type.STEEL) return ChatColor.GRAY;
        if (t == Type.WATER) return ChatColor.BLUE;
        return ChatColor.WHITE;
    }
}
