package seabattleWicketPackage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 11.08.13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */


public class GameCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private String playerName;
    private int playerId;
    private CommandTypes command;
    private Date commandDate;

    public enum CommandTypes {
        AUTO(0), DESTROY_OWN(1), DESTROY_ENEMY(2), SET_OWN(3), SET_ENEMY(4), SET_ISLAND(5), DESTROY_ISLAND(6), SET_BARREL(7);

        private final int value;

        private CommandTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String ToString() {
            switch (value) {
                case 1:
                    return "Streljajem po svoim";
                case 2:
                    return "Streljajem po vragu";
                case 3:
                    return "Stavim novij svoj korablj";
                case 4:
                    return "Stavim novij vrazheskij korablj";
                case 5:
                    return "Stavim ostrov";
                case 6:
                    return "Ubirajem ostrov";
                default:
                    return "Avtomat";

            }
        }
    }

    public GameCommand(int playerId, int command, int x, int y) {
        super();

        switch (command) {
            case 1:
                this.command = CommandTypes.DESTROY_OWN;
                break;
            case 2:
                this.command = CommandTypes.DESTROY_ENEMY;
                break;
            case 3:
                this.command = CommandTypes.SET_OWN;
                break;
            case 4:
                this.command = CommandTypes.SET_ENEMY;
                break;
            case 5:
                this.command = CommandTypes.SET_ISLAND;
                break;
            case 6:
                this.command = CommandTypes.DESTROY_ISLAND;
                break;
            default:
                this.command = CommandTypes.AUTO;
                break;

        }


        System.out.println("playerName " + playerId + ": command: " + command);
        this.x = x;
        this.y = y;
        this.playerId = playerId;
        this.playerName = GameSession.getPlayerNameById(playerId);
        this.commandDate = new Date();


        GamePage.gameMap.setMap(this.x, this.y, this.command, this.playerId);
    }


    //eti metodi vizivajem iz  populateItem  - new PropertyModel<String>(message, "playerName"));
    //avtomatom budet vizivatj getPlayerName
    public String getPlayerName() {
        System.out.println("PropertyModel getPlayerName method: playerName " + playerName);
        return "[" + new SimpleDateFormat("HH:mm:ss").format(commandDate) + "] " + playerId + ") " + playerName;
    }

    public String getCommand() {
        System.out.println("PropertyModel getCommand for playerName " + playerName + "method: command: " + command.ToString());
        return command.ToString();
    }

}