package seabattleWicketPackage;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 11.08.13
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */

public class GameSession extends WebSession {
    private static final long serialVersionUID = 1L;

    private String username;
    private int playerId;
    private int gold;

    private static int playerCounter = 0;
    static private List<GameSession> playersArray = new ArrayList<GameSession>();


    public GameSession(Request request) {
        super(request);
    }

    public static GameSession get() {
        return (GameSession) WebSession.get();
    }

    public String getUsername() {
        return username;
    }

    public int GetPlayerId() {
        return playerId;
    }

    public void setUsername(String username) {
        this.username = username;
        this.playerId = playerCounter;
        this.gold = 300;
        playersArray.add(this.playerId, this);
        playerCounter++;
    }


    public Boolean Consume(int price) {
        if (gold >= price) {
            gold = gold - price;
            return true;
        }
        return false;
    }

    public static String getPlayerNameById(int playerId) {
        return playersArray.get(playerId).getUsername();

    }

    public int getGold() {
        return gold;
    }

}