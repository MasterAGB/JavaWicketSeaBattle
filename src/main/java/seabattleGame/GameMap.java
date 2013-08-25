package seabattleGame;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import seabattleWicketPackage.GameSession;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 25.08.13
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class GameMap implements Serializable {


    private static final long serialVersionUID = 1L;

    private String map;

    public GameMap() {

    }

    public String getMap(){
        StringBuilder map=new StringBuilder(1024);
        map.append("Map 1 for player: "+ GameSession.get().getUsername()+" \n");
        for (int y=0;y<=8;y++){
        for (int x=0;x<=8;x++){
            map.append("| ");

        }
            map.append("|\n");
        }
        map.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        return map.toString();
    }
}
