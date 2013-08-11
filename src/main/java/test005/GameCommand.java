package test005;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 11.08.13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */


public class GameCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private String player;
    private String command;


    public GameCommand(String player, String command) {
        super();
        System.out.println("player "+player+": command: "+command);
        this.player = player;
        this.command = command;
    }

    public String getFrom() {
        return player;
    }

    public String getCommand() {
        return command;
    }

}