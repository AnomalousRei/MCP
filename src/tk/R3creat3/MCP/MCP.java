package tk.R3creat3.MCP;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tk.R3creat3.MCP.handlers.*;
import tk.R3creat3.MCP.object.ControllerObject;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.Trainer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

public class MCP extends JavaPlugin {

    public static Logger logger = Logger.getLogger("Minecraft");
    public static ControllerObject controller = new ControllerObject();
    public static HashMap<String, Trainer> trainers = new HashMap<String, Trainer>();
    public static HashMap<String, Move> moves = new HashMap<String, Move>();
    public static MCP plugin;

    //SQL stuff
    public static String storageType = null;
    public static int storagePort = 0;
    public static String storageHostname = null;
    public static String storageUsername = null;
    public static String storagePassword = null;
    public static String storageDatabase = null;

    public void onEnable() {
        plugin = this;

        new MiscListener(this);
        new AsyncPlayerSQLListener(this);
        new InGameHandler(this);
        new TutorialHandler(this);

        createConfig();

        // SQL stuff, before anything.
        storagePort = getConfig().getInt("database.port");
        storageHostname = getConfig().getString("database.hostname");
        storageUsername = getConfig().getString("database.username");
        storagePassword = getConfig().getString("database.password");
        storageDatabase = getConfig().getString("database.database");

        logger.info("Loading core API...");

        logger.info("Loading instances...");

        StartupHandler.initiateMoves();

        registerCommands();

        logger.info("Checking SQL tables...");
        SQLHandler.createTables();
        try {
            SQLHandler.createNewUser("Testing");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createNullWorld("tutorial");
        createNullWorld("mall");

    }

    public void onDisable() {

    }

    public void createNullWorld(String s){
        WorldCreator wc = new WorldCreator(s);
        wc.generator(new NullChunkGenerator());
        Bukkit.createWorld(wc);
    }

    public static MCP getInstance() {
        return plugin;
    }

    private void createConfig() {
        boolean exists = new File("plugins/MCP/config.yml").exists();
        if (!exists) {
            new File("plugins/MCP").mkdir();
            getConfig().options().header("MCP, made by R3creat3!");
            getConfig().set("database.hostname", "some_pointless_hostname");
            getConfig().set("database.port", 3306);
            getConfig().set("database.username", "user");
            getConfig().set("database.password", "banana");
            getConfig().set("database.database", "MCP");
            try {
                getConfig().save("plugins/MCP/config.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HashMap<String, Trainer> getTrainers() {
        return trainers;
    }

    public static HashMap<String, Move> getMoves() {
        return moves;
    }


    public static void registerEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, MCP.getInstance());
    }

    /**
     * *******************************************************************
     * Code to use for sk89q's command framework goes below this comment! *
     * ********************************************************************
     */

    private CommandsManager<CommandSender> commands;
    private boolean opPermissions;

    private void registerCommands() {
        final MCP plugin = this;
        // Register the commands that we want to use
        commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender player, String perm) {
                return plugin.hasPermission(player, perm);
            }
        };
        commands.setInjector(new SimpleInjector(this));
        final CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);

        cmdRegister.register(Commands.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "You need to enter a number!");
            } else {
                sender.sendMessage(ChatColor.RED + "Error occurred, contact developer.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    public boolean hasPermission(CommandSender sender, String perm) {
        if (!(sender instanceof Player)) {
            if (sender.hasPermission(perm)) {
                return ((sender.isOp() && (opPermissions || sender instanceof ConsoleCommandSender)));
            }
        }
        return hasPermission(sender, ((Player) sender).getWorld(), perm);
    }

    public boolean hasPermission(CommandSender sender, World world, String perm) {
        if ((sender.isOp() && opPermissions) || sender instanceof ConsoleCommandSender || sender.hasPermission(perm)) {
            return true;
        }

        return false;
    }

    public void checkPermission(CommandSender sender, String perm)
            throws CommandPermissionsException {
        if (!hasPermission(sender, perm)) {
            throw new CommandPermissionsException();
        }
    }

    public void checkPermission(CommandSender sender, World world, String perm)
            throws CommandPermissionsException {
        throw new CommandPermissionsException();
    }
}
