package seabattle;

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


    //eti metodi vizivajem iz  populateItem  - new PropertyModel<String>(message, "player"));
    //avtomatom budet vizivatj getPlayer
    public String getPlayer() {
        System.out.println("PropertyModel getPlayer method: player "+player);
        return player;
    }

    public String getCommand() {
        System.out.println("PropertyModel getCommand method: command: "+command);
        return command;
    }

}