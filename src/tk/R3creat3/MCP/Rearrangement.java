package tk.R3creat3.MCP;

import org.bukkit.Bukkit;
import tk.R3creat3.MCP.handlers.SQLHandler;
import tk.R3creat3.MCP.object.Pokemon;
import tk.R3creat3.MCP.object.Trainer;

import java.util.ArrayList;

public class Rearrangement {

    public static void rearrangePokemon(Trainer t) {
        Pokemon p1 = null;
        Pokemon p2 = null;
        Pokemon p3 = null;
        Pokemon p4 = null;
        Pokemon p5 = null;
        Pokemon p6 = null;
        ArrayList<Pokemon> temp = new ArrayList<Pokemon>();
        for (Pokemon p : t.getPokemon()) {
            if (p != null) {
                temp.add(p);
            }
        }
        int orderCount = 1;
        for (Pokemon p : temp) {
            SQLHandler.changeOrder(p.ID, orderCount);
            orderCount++;
        }
        String s = t.getName();
        MCP.getTrainers().remove(s);
        Trainer.craftTrainer(Bukkit.getPlayer(s));
    }

    //What the hell was I drinking when I made this
    @Deprecated
    public static synchronized void rearrange(Trainer t) {
        if (t.p1 == null) {
            if (t.p2 != null) {
                SQLHandler.changeOrder(t.p2.ID, 1);
            }
            if (t.p2 == null && t.p3 != null) {
                SQLHandler.changeOrder(t.p3.ID, 1);
            }
            if (t.p2 == null && t.p3 == null && t.p4 != null) {
                SQLHandler.changeOrder(t.p4.ID, 1);
            }
            if (t.p2 == null && t.p3 == null && t.p4 == null && t.p5 != null) {
                SQLHandler.changeOrder(t.p5.ID, 1);
            }
            if (t.p2 == null && t.p3 == null && t.p4 == null && t.p5 == null && t.p6 != null) {
                SQLHandler.changeOrder(t.p6.ID, 1);
            }
        }
        if (t.p2 == null) {
            if (t.p3 != null) {
                SQLHandler.changeOrder(t.p3.ID, 2);
            }
            if (t.p3 == null && t.p4 != null) {
                SQLHandler.changeOrder(t.p4.ID, 2);
            }
            if (t.p3 == null && t.p4 == null && t.p5 != null) {
                SQLHandler.changeOrder(t.p5.ID, 2);
            }
            if (t.p3 == null && t.p4 == null && t.p5 == null && t.p6 != null) {
                SQLHandler.changeOrder(t.p6.ID, 2);
            }
            if (t.p3 == null) {
                if (t.p4 != null) {
                    SQLHandler.changeOrder(t.p4.ID, 3);
                }
                if (t.p4 == null && t.p5 != null) {
                    SQLHandler.changeOrder(t.p5.ID, 3);
                }
                if (t.p4 == null && t.p5 == null && t.p6 != null) {
                    SQLHandler.changeOrder(t.p6.ID, 3);
                }
            }
            if (t.p4 == null) {
                if (t.p5 != null) {
                    SQLHandler.changeOrder(t.p5.ID, 4);
                }
                if (t.p5 == null && t.p6 != null) {
                    SQLHandler.changeOrder(t.p6.ID, 4);
                }
            }
            if (t.p5 == null) {
                if (t.p6 != null) {
                    SQLHandler.changeOrder(t.p6.ID, 5);
                }
            }
        }
    }
}