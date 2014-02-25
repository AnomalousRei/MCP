package tk.R3creat3.MCP.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.Rearrangement;
import tk.R3creat3.MCP.object.Trainer;

import java.sql.SQLException;

public class AsyncPlayerSQLListener implements Listener {

    MCP plugin;

    public AsyncPlayerSQLListener(MCP pl) {
        plugin = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        e.getPlayer().setDisplayName(getPrefix(e.getPlayer()));

        //ASYNCHRONOUSLY refresh trainer data.
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    //This will create new user's data is it doesn't exist.
                    SQLHandler.createNewUser(p.getName());
                } catch (SQLException ex) {
                    System.out.println("ERROR IN SQL!");
                    ex.printStackTrace(System.err);
                }
            }
        });

        Trainer.craftTrainer(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLowJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        //ASYNCHRONOUSLY re-order pokemon to their correct order.
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Rearrangement.rearrangePokemon(Trainer.getTrainer(p));
            }
        });

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws SQLException {
        Player p = e.getPlayer();

        //Remove instances and references
        Trainer.removeTrainer(p);
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
}
