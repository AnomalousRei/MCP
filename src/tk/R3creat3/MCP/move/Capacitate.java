package tk.R3creat3.MCP.move;

import tk.R3creat3.MCP.Type;
import tk.R3creat3.MCP.object.IMove;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.MoveObject;
import tk.R3creat3.MCP.object.MoveType;

@MoveObject
public class Capacitate extends Move implements IMove {
    public Capacitate() {
        super.initiate("Capacitate", Type.ELECTRIC, 120, 100);
    }

    public MoveType getMoveType(){
        return MoveType.PHYSICAL;
    }
}
