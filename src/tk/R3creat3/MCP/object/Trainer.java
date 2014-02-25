package tk.R3creat3.MCP.object;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R1.CraftServer;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.handlers.SQLHandler;
import tk.R3creat3.MCP.Type;

import java.sql.SQLException;
import java.util.ArrayList;

public class Trainer extends CraftPlayer {

    public Trainer(final Player p) {
        super((CraftServer) Bukkit.getServer(), ((CraftPlayer) p).getHandle());
        instance = this;
        Bukkit.getScheduler().runTaskAsynchronously(MCP.getInstance(), new Runnable() {
            public void run() {
                try {
                    asyncRefreshTrainerInfo(p);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Trainer instance;
    public Pokemon p1;
    public Pokemon p2;
    public Pokemon p3;
    public Pokemon p4;
    public Pokemon p5;
    public Pokemon p6;

    public String title;

    /**
     * Sets a trainer's pokemon.
     *
     * @param p     A pokemon
     * @param range What place you put it in
     */
    public void setPokemon(Pokemon p, int range) {
        if (range > 6 || range < 1) return;
        if (range == 1) p1 = p;
        if (range == 2) p2 = p;
        if (range == 3) p3 = p;
        if (range == 4) p4 = p;
        if (range == 5) p5 = p;
        if (range == 6) p6 = p;
    }

    /**
     * Gets a trainer's pokemon
     *
     * @param range What place to get it from
     */
    public Pokemon getPokemon(int range) {
        if (range > 6 || range < 1) return new Pokemon("Unidentified", Type.NONE, Type.NONE, null, null, null, null, 0, 1);
        if (range == 1) return p1;
        if (range == 2) return p2;
        if (range == 3) return p3;
        if (range == 4) return p4;
        if (range == 5) return p5;
        if (range == 6) return p6;
        return new Pokemon("Unidentified", Type.NONE, Type.NONE, null, null, null, null, 0, 1);
    }

    /**
     * Get ALL of the trainer's pokemon
     */
    public ArrayList<Pokemon> getPokemon() {
        ArrayList<Pokemon> p = new ArrayList<Pokemon>();
        if (p1 != null) p.add(p1);
        if (p2 != null) p.add(p2);
        if (p3 != null) p.add(p3);
        if (p4 != null) p.add(p4);
        if (p5 != null) p.add(p5);
        if (p6 != null) p.add(p6);
        return p;
    }

    /**
     * Gets a player's Trainer object form their username.
     */
    public static Trainer getTrainer(String name) {
        return MCP.getInstance().getTrainers().get(name);
    }

    /**
     * Gets a player's Trainer object from their player instance.
     */
    public static Trainer getTrainer(Player p) {
        return MCP.getInstance().getTrainers().get(p.getName());
    }

    public void replace(Pokemon newPokemon, int range){
        if(range > 6 || range < 1) return;
        if(range == 1){
            p1 = null;
            p1 = newPokemon;
            return;
        }
        if(range == 2){
            p2 = null;
            p2 = newPokemon;
            return;
        }
        if(range == 3){
            p3 = null;
            p3 = newPokemon;
            return;
        }
        if(range == 4){
            p4 = null;
            p4 = newPokemon;
            return;
        }
        if(range == 5){
            p5 = null;
            p5 = newPokemon;
            return;
        }
        if(range == 6){
            p6 = null;
            p6 = newPokemon;
            return;
        }
    }

    public void asyncRefreshTrainerInfo(final Player p) throws SQLException {
        Bukkit.getScheduler().runTaskAsynchronously(MCP.getInstance(), new Runnable() {
            public void run() {
                String t = null;
                try {
                    t = SQLHandler.getTitle(p.getName());
                    SQLHandler.autoHandlePokemon(instance);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                title = t;
            }
        });
    }

    /**
     * Creates a Trainer instance for a player.
     *
     * @param p A Player
     */
    public static void craftTrainer(Player p) {
        Trainer t = new Trainer(p);
        MCP.getInstance().getTrainers().put(p.getName(), t);
    }

    public static void removeTrainer(Player p) {
        MCP.getInstance().getTrainers().remove(p.getName());
    }

    public static void removeTrainer(String p) {
        MCP.getInstance().getTrainers().remove(p);
    }

    public boolean hasPermisson(String perm) {
        return Bukkit.getPlayer(getName()).hasPermission(perm);
    }


    // *********** Deprecated shit thanks to Bukkit's over-mapped thing ***********

    public double getLastDamage() {
        return super.getLastDamage();
    }

    public double getMaxHealth() {
        return super.getMaxHealth();
    }

    public double getHealth() {
        return super.getHealth();
    }

    @Deprecated
    public void setMaxHealth(int amount) {
        super.setMaxHealth((double) amount);
    }

    @Deprecated
    public void setHealth(int amount) {
        // This is a dud. Use setHealth(double)
    }

    @Deprecated
    public void damage(int amount) {
        super.damage((double) amount);
    }

    @Deprecated
    public void damage(int amount, org.bukkit.entity.Entity entity) {
        super.damage((double) amount, entity);
    }

    @Deprecated
    public void setLastDamage(int amount) {
        super.damage((double) amount);
    }

}