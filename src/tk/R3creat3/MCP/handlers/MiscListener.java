package tk.R3creat3.MCP.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.Utility;

public class MiscListener implements Listener {

    MCP plugin;

    public MiscListener(MCP pl) {
        plugin = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void chatListener(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith("~") && event.getPlayer().hasPermission("MCP.rank.mod")) {
            Utility.sendToOnlineStaff(event.getMessage().replaceFirst("~", ""), getPrefix(event.getPlayer()));
            event.setCancelled(true);
            return;
        }
        event.getPlayer().setDisplayName(getPrefix(event.getPlayer()));
        event.setFormat("[" + ChatColor.GREEN + "C" + ChatColor.WHITE + "] " +
                getPrefix(event.getPlayer()) + ChatColor.WHITE + ": " + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
    }

    public String getPrefix(Player p) {
        String prefix = p.getName();

        if (p.hasPermission("MCP.rank.admin")) {
            prefix = ChatColor.RED + prefix;
            return prefix;
        }

        if (p.hasPermission("MCP.rank.mod")) {
            prefix = ChatColor.BLUE + prefix;
            return prefix;
        }
        if (p.hasPermission("MCP.rank.donator")) {
            prefix = ChatColor.GREEN + prefix;
            return prefix;
        }

        return prefix;
    }

    @EventHandler
    public void disablePartyInteract(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(ChatColor.GREEN + "Current pokemon party")) {
            if (e.getCurrentItem() != null)
                e.getCurrentItem().setAmount(0);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void hideCertainCommands(PlayerCommandPreprocessEvent event) {
        String placeholderMessage = "Unknown command. Type \"/help\" for help.";

        if (event.getMessage().equalsIgnoreCase("/plugins") && !event.getPlayer().isOp() || event.getMessage().equalsIgnoreCase("/pl") && !event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Plugins (2): " + ChatColor.GREEN + "MCP" + ChatColor.WHITE + ", " + ChatColor.GREEN + "MCP-Economy");
        }
        if (event.getMessage().equalsIgnoreCase("/?") && !event.getPlayer().isOp() || event.getMessage().equalsIgnoreCase("/help") && !event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(placeholderMessage);
        }
    }

    @EventHandler
    public void colorsign(SignChangeEvent s) {
        s.setLine(0, ChatColor.translateAlternateColorCodes('&', s.getLine(0)));
        s.setLine(1, ChatColor.translateAlternateColorCodes('&', s.getLine(1)));
        s.setLine(2, ChatColor.translateAlternateColorCodes('&', s.getLine(2)));
        s.setLine(3, ChatColor.translateAlternateColorCodes('&', s.getLine(3)));
    }

    @EventHandler
    public void serverPing(ServerListPingEvent e) {
    }
}
