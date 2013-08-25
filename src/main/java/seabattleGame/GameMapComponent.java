package seabattleGame;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;
import seabattleWicketPackage.GameCommand;
import seabattleWicketPackage.GameSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GameMapComponent extends Panel {

    private String map;

    public GameMapComponent(String id) {
        super(id);

        List<String> data = new ArrayList<String>();
        data.add("first row");
        data.add("second row");
        data.add(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        data.add("third row");
        data.add(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));


        final ListView listView = new ListView("gameMapRow", data) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem item) {
                this.modelChanging();

                item.add(new Label("gameMapCell", item.getModel()));

            }
        };

        this.setOutputMarkupId(true);
        this.add(listView);

        AjaxSelfUpdatingTimerBehavior ajaxBehavior = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5));
        this.add(ajaxBehavior);


        this.add(new AjaxFallbackLink("incrementer") {
            @Override
            public void onClick(AjaxRequestTarget target) {

                if (target != null) {
                    target.addComponent(this);
                }
            }
        });


    }


    public String getMap() {
        StringBuilder map = new StringBuilder(1024);
        map.append("Map 1 for player: " + GameSession.get().getUsername() + " \n");
        for (int y = 0; y <= 8; y++) {
            for (int x = 0; x <= 8; x++) {
                map.append("| ");

            }
            map.append("|\n");
        }
        map.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        return map.toString();
    }


/*
    @Override
    public String getObject() {
        return student.getName();
    }
    @Override
    public void setObject(String name) {
        student.setName(name);
    }*/
}