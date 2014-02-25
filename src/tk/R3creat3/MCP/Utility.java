package tk.R3creat3.MCP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utility {

    static MCP plugin = MCP.getInstance();

    public static void sendToOnlineStaff(String message, String sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("MCP.rank.mod")) {
                p.sendMessage(ChatColor.AQUA + "[Staff-chat] " + sender + ChatColor.AQUA + " " + message);
            }
        }
    }

    public static void giveDefaultTools(Player p) {
        Inventory i = p.getInventory();
        i.clear();
        i.addItem(partyTool());
    }

    private static ItemStack partyTool() {
        ItemStack i = new ItemStack(Material.EMERALD, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Pokèmon party");
        i.setItemMeta(im);
        return i;
    }

    public static void nameItem(ItemStack i, String name){
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
    }
}
