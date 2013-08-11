package seabattleWicketPackage;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

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

    public GameSession(Request request) {
        super(request);
    }

    public static GameSession get() {
        return (GameSession) WebSession.get();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}