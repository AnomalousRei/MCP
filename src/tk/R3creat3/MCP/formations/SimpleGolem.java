package tk.R3creat3.MCP.formations;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;

public class SimpleGolem extends BlockFormation {
    public SimpleGolem() {
        super.setBlocks(b);
    }

    HashMap<Location, Material> b = new HashMap<Location, Material>();
}
