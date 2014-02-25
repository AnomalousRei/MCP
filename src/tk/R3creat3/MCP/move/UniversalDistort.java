package tk.R3creat3.MCP.move;

import tk.R3creat3.MCP.Type;
import tk.R3creat3.MCP.object.IMove;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.MoveObject;
import tk.R3creat3.MCP.object.MoveType;

@MoveObject
public class UniversalDistort extends Move implements IMove {
    public UniversalDistort() {
        super.initiate("Universal Distort", Type.DARK, 150, 80);
    }

    public MoveType getMoveType(){
        return MoveType.PHYSICAL;
    }
}
