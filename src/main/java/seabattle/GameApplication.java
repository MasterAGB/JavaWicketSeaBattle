package seabattle;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 11.08.13
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */


public class GameApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();

        mountPage("/connect", ConnectToGamePage.class);
        mountPage("/game", GamePage.class);
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new GameSession(request);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return GamePage.class;
    }




}