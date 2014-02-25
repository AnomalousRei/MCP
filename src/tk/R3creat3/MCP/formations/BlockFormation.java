package tk.R3creat3.MCP.formations;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashMap;

public abstract class BlockFormation {

    public BlockFormation(){
        super();
    }

    HashMap<Location, Material> blocks = new HashMap<Location, Material>();

    public void craftBlockFormation(World world) {
        if (blocks.size() == 0) return;
        for (Location l : blocks.keySet()) {
            Material m = blocks.get(l);
            Block b = world.getBlockAt(new Location(world, l.getX(), l.getY(), l.getZ()));
            b.setType(m);
        }
    }

    public HashMap<Location, Material> getBlocks(){
        return blocks;
    }

    protected void setBlocks(HashMap<Location, Material> b){
        blocks = b;
    }

}
