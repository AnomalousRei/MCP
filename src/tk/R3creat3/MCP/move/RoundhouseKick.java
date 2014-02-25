package tk.R3creat3.MCP.move;

import tk.R3creat3.MCP.Type;
import tk.R3creat3.MCP.object.IMove;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.MoveObject;
import tk.R3creat3.MCP.object.MoveType;

@MoveObject
public class RoundhouseKick extends Move implements IMove {
    public RoundhouseKick() {
        super.initiate("Roundhouse Kick", Type.FIGHTING, 120, 70);
    }

    public MoveType getMoveType(){
        return MoveType.PHYSICAL;
    }
}
