package tk.R3creat3.MCP.handlers;

import org.reflections.Reflections;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.MoveObject;
import tk.R3creat3.MCP.object.MoveType;

import java.util.Set;

public class StartupHandler {

    public static void initiateMoves() {
        Reflections reflections = new Reflections("tk.R3creat3.MCP.move");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MoveObject.class);
        for (Class<?> classs : classes) {
            try {
                Object move = classs.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}