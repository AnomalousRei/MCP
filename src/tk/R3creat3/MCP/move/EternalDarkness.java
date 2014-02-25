package tk.R3creat3.MCP.move;

import tk.R3creat3.MCP.Type;
import tk.R3creat3.MCP.object.IMove;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.MoveObject;
import tk.R3creat3.MCP.object.MoveType;

@MoveObject
public class EternalDarkness extends Move implements IMove {
    public EternalDarkness() {
        super.initiate("Eternal Darkness", Type.DARK, 160, 50);
    }

    public MoveType getMoveType(){
        return MoveType.PHYSICAL;
    }
}
