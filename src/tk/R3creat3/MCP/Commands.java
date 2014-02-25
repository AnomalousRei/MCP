package tk.R3creat3.MCP;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.R3creat3.MCP.handlers.TypeHandler;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.Pokemon;
import tk.R3creat3.MCP.object.Trainer;
import tk.R3creat3.economy.EconUtility;

import java.sql.SQLException;
import java.util.ArrayList;

public class Commands {
    MCP plugin;

    public Commands(MCP pl) {
        plugin = pl;
    }

    @Command(aliases = {"test"},
    desc = "Testing command")
    public void test(CommandContext args, CommandSender sender){
        ArrayList<Move> valuesList = new ArrayList<Move>(MCP.getMoves().values());
        for(Move m : valuesList){
            sender.sendMessage(m.getMoveName());
        }
    }

    @Command(aliases = {"trainerinfo", "tinfo", "view"},
            desc = "View an online trainer's info",
            usage = "<player>",
            min = 1,
            max = 1)
    public void trainerinfo(CommandContext args, CommandSender sender) {
        String bottomFormat = ChatColor.YELLOW + "##################################";
        if (Bukkit.getPlayer(args.getString(0)) == null) {
            sender.sendMessage(ChatColor.RED + "That person is not online!");
            return;
        }
        //I have to do it this way to prevent errors if I type /view Anomalous
        Trainer t = Trainer.getTrainer(Bukkit.getPlayer(args.getString(0)));
        int tempCheck = t.getName().length();
        while (tempCheck > 0) {
            tempCheck--;
            bottomFormat = bottomFormat + ChatColor.YELLOW + "#";
        }
        sender.sendMessage(ChatColor.YELLOW + "########## Information on " + t.getName() + " ##########");
        sender.sendMessage(ChatColor.GREEN + "Is formally known as '" + t.title + "' " + t.getName());
        sender.sendMessage(ChatColor.GOLD + "Battles with a total of " + t.getPokemon().size() + " pokemon");
        sender.sendMessage(ChatColor.RED + "Holds a grand total of " + EconUtility.getBalance(t.getName()) + " pokè");
        sender.sendMessage(bottomFormat);
    }

    @Command(aliases = {"party", "pokemon"},
            desc = "Check your party",
            usage = "<player>",
            max = 1,
            min = 0)
    public void testing(CommandContext args, CommandSender sender) throws SQLException {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "Console cannot do this!");
            return;
        }
        if (args.argsLength() == 1 && (plugin.getServer().matchPlayer(args.getString(0)).size() == 0)) {
            sender.sendMessage(ChatColor.RED + "That person is not online!");
            return;
        }
        Inventory i = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Current pokemon party");
        if (args.argsLength() == 0) {
            Trainer t = MCP.getTrainers().get(sender.getName());
            Player pl = Bukkit.getPlayer(sender.getName());
            for (Pokemon p : t.getPokemon()) {
                ItemStack it = new ItemStack(Material.STONE, 1);
                ItemMeta im = it.getItemMeta();
                im.setDisplayName(TypeHandler.parseColor(p.getType()) + p.getName());
                ArrayList<String> movesLore = new ArrayList<String>();
                for (Move m : p.getMoves()) {
                    movesLore.add(ChatColor.AQUA + m.getMoveName());
                }
                im.setLore(movesLore);
                it.setItemMeta(im);
                i.addItem(it);
            }

            pl.openInventory(i);
        } else {
            Player pl = Bukkit.getPlayer(args.getString(0));
            Trainer t = MCP.getTrainers().get(pl.getName());
            for (Pokemon p : t.getPokemon()) {
                ItemStack it = new ItemStack(Material.STONE, 1);
                ItemMeta im = it.getItemMeta();
                im.setDisplayName(TypeHandler.parseColor(p.getType()) + p.getName());
                ArrayList<String> movesLore = new ArrayList<String>();
                for (Move m : p.getMoves()) {
                    movesLore.add(ChatColor.AQUA + m.getMoveName());
                }
                im.setLore(movesLore);
                it.setItemMeta(im);
                i.addItem(it);
            }

            Player s = (Player) sender;
            s.openInventory(i);
        }
    }
}
