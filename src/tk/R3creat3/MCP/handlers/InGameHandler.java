package tk.R3creat3.MCP.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.Utility;

public class InGameHandler implements Listener {

    MCP plugin;

    public InGameHandler(MCP pl) {
        plugin = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Utility.giveDefaultTools(p);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();
        ItemStack hand = p.getItemInHand();

        //Party stone
        if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)) {
            if (hand.getType().equals(Material.EMERALD)) {
                if (hand.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Pokèmon party")) {
                    p.performCommand("party");
                } else return;
            }
        }
    }
}
