package tk.R3creat3.MCP.object;

import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.Type;

public class Move {
    public Move() {
        //Moves can only be initiated from things that implement it, we just need an instance for now!
    }

    private int power;
    private int accuracy;
    private String name;
    private Type type;
    private int recoil;

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public Type getType() {
        return type;
    }

    public String getMoveName() {
        return name;
    }

    public void setRecoilDamage(int amount) {
        recoil = amount;
    }

    public int getRecoilDamage() {
        return recoil;
    }

    protected void initiate(String iname, Type itype, int ipower, int iaccuracy) {
        power = ipower;
        accuracy = iaccuracy;
        name = iname;
        type = itype;
        MCP.getInstance().getMoves().put(iname, this);
    }
}
