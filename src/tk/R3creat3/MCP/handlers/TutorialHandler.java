package tk.R3creat3.MCP.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.Utility;

public class TutorialHandler implements Listener {

    MCP plugin;

    public TutorialHandler(MCP pl) {
        plugin = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        boolean worldcheck = !e.getPlayer().getWorld().getName().equals("tutorial");

        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack hand = p.getItemInHand();
        Block b = null;
        if (a.equals(Action.RIGHT_CLICK_BLOCK)) b = e.getClickedBlock();

        if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (b.getType().equals(Material.PISTON_MOVING_PIECE)) {
                e.setCancelled(true);
            }

            if (b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN)) {
                BlockState state = b.getState();
                Sign s = (Sign) state;

                //Sign stuff
                if (s.getLine(0).contains(ChatColor.DARK_BLUE + "[MCPTutorial]") && s.getLine(2).contains("[Advance]") && !p.getInventory().contains(Material.MAGMA_CREAM)) {
                    ItemStack i = new ItemStack(Material.MAGMA_CREAM);
                    Utility.nameItem(i, ChatColor.BLUE + "" + ChatColor.BOLD + "Right click to propel where you are looking!");
                    p.getInventory().addItem(i);
                    p.sendMessage(ChatColor.YELLOW + "You've been given 1 Boost Jump by " + ChatColor.LIGHT_PURPLE + "MCP");
                }

                if (s.getLine(0).contains(ChatColor.DARK_BLUE + "[MCPTutorial]") && s.getLine(2).contains("[Start]")) {
                    p.teleport(new Location(Bukkit.getWorld("tutorial"), -1.5, 39.5, 5.5));
                    p.sendMessage(ChatColor.GRAY + "(From " + ChatColor.LIGHT_PURPLE + "MCP" + ChatColor.GRAY + "):" + ChatColor.WHITE + " Weclome to the MCP tutorial! To start, please walk ahead! Good luck!");
                }
            }
        }

        if (hand.getType().equals(Material.MAGMA_CREAM)) {
            ItemStack i = new ItemStack(Material.MAGMA_CREAM);
            Utility.nameItem(i, ChatColor.BLUE + "" + ChatColor.BOLD + "Right click to propel where you are looking!");
            p.getInventory().removeItem(i);
            p.updateInventory();
            p.setVelocity(p.getLocation().getDirection().setX(p.getLocation().getX()));
            p.setVelocity(p.getLocation().getDirection().setY(p.getLocation().getY()));
            p.setVelocity(p.getLocation().getDirection().setZ(p.getLocation().getZ()));
            p.setVelocity(p.getLocation().getDirection().multiply(1.2));
        }
    }
}
