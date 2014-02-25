package tk.R3creat3.MCP.object;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.handlers.SQLHandler;
import tk.R3creat3.MCP.Type;

import java.sql.SQLException;
import java.util.ArrayList;

public class Pokemon {
    public Pokemon(String name, Type type1, Type type2, Move move1, Move move2,
                   Move move3, Move move4, long ID, int pos) {
        this.name = name;
        this.type = type1;
        this.type2 = type2;
        this.move = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
        if (!name.equals("Temp"))
            refreshStats(ID);
        instance = this;
        this.ID = ID;
        this.pos = pos;
    }

    Pokemon instance;
    String name;
    Player owner;
    Type type;
    Type type2;
    Move move;
    Move move2;
    Move move3;
    Move move4;
    int pos;
    public long ID;

    //Statistics- CANNOT be altered.
    private int STAT_HP;
    private int STAT_ATTACK;
    private int STAT_DEFENSE;
    private int STAT_SPATK;
    private int STAT_SPDEF;
    private int STAT_SPEED;

    //Current state
    private boolean fainted = false;
    private int CURRENT_HP;

    public String getName() {
        return name;
    }

    public void setStats(int HP, int ATK, int DEF, int SPATK, int SPDEF, int SPEED) {
        fainted = false;
        CURRENT_HP = HP;
        STAT_ATTACK = ATK;
        STAT_DEFENSE = DEF;
        STAT_SPATK = SPATK;
        STAT_SPDEF = SPDEF;
        STAT_SPEED = SPEED;
    }

    public int getPosition() {
        return pos;
    }

    public void heal() {
        CURRENT_HP = STAT_HP;
    }

    /**
     * Gets a pokemon's move
     *
     * @param range What place to get it from
     */
    public Move getMove(int range) {
        if (range > 4 || range < 1) return null;
        if (range == 1) return move;
        if (range == 2) return move2;
        if (range == 3) return move3;
        if (range == 4) return move4;
        return null;
    }

    public void refreshStats(final long ID) {
        Bukkit.getScheduler().runTaskAsynchronously(MCP.getInstance(), new Runnable() {
            public void run() {
                try {
                    SQLHandler.refreshStats(instance, ID);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Get ALL of the pokemon's moves
     */
    public ArrayList<Move> getMoves() {
        ArrayList<Move> p = new ArrayList<Move>();
        if (move != null) p.add(move);
        if (move2 != null) p.add(move2);
        if (move3 != null) p.add(move3);
        if (move4 != null) p.add(move4);
        return p;
    }

    private boolean isDualType() {
        if (type2.equals(Type.NONE)) {
            return true;
        }
        return false;
    }

    public Type getType() {
        return type;
    }

    public Type getSecondType() {
        if (isDualType()) return Type.NONE;
        return type2;
    }
}
