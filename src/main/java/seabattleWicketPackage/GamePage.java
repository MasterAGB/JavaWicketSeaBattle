package seabattleWicketPackage;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 11.08.13
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.time.Duration;

import java.util.LinkedList;


public class GamePage extends WebPage implements IHeaderContributor {
    private static final long serialVersionUID = 1L;

    private static final int MAX_MESSAGES = 5;
    static private final LinkedList<GameCommand> messages = new LinkedList<GameCommand>();


    private MarkupContainer messagesContainer;
    private GameMapComponent gameMapComponent;
    public static GameMap gameMap;


    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderJavaScriptReference(new PackageResourceReference(GamePage.class, "script.js"));
        response.renderCSSReference(new PackageResourceReference(GamePage.class, "style.css"));


    }

    public GamePage() {

        if (gameMap == null) {
            System.out.println("...Start game page!");
            gameMap = new GameMap();
        }


        add(buildUsernameLabel());


        add(buildMessagesContainer());
        add(initGameMap());
        add(buildForm());
        add(footer());



        //this.modelChanging();
        add(buildGoldLabel());
        add(buildIncomeLabel());
    }


    @Override
    protected void onConfigure() {
        super.onConfigure();

        String username = GameSession.get().getUsername();
        if (username == null) {
            throw new RestartResponseAtInterceptPageException(ConnectToGamePage.class);
        }
    }

    private Component footer() {
        return new FooterBottom("footer");
    }

    private Component buildForm() {
        final TextField<String> textField = new TextField<String>("message", new Model<String>());


        final TextField<String> textFieldX = new TextField<String>("x", new Model<String>());
        final TextField<String> textFieldY = new TextField<String>("y", new Model<String>());


        textField.setOutputMarkupId(true);


        Form<String> form = new Form<String>("form");


        //knopka ujti
        Button button = new Button("leave") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit() {
                super.onSubmit();
                //uhodim iz igri
                GameSession.get().setUsername(null);
            }
        };


        Component submit = new AjaxSubmitLink("submit") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                int playerId = GameSession.get().GetPlayerId();
                String commandText = textField.getModelObject();

                int command = 0;


                try {
                    if (commandText != "") {
                        command = Integer.parseInt(commandText);
                    }
                    int targetX = Integer.parseInt(textFieldX.getModelObject());
                    int targetY = Integer.parseInt(textFieldY.getModelObject());

                    GameCommand gameCommand = new GameCommand(playerId, command, targetX, targetY);

                    synchronized (messages) {
                        if (messages.size() >= MAX_MESSAGES) {
                            messages.removeLast();
                        }

                        messages.addFirst(gameCommand);
                    }

                    textField.setModelObject(Integer.toString(command));
                    target.add(messagesContainer, textField);
                    target.add(gameMapComponent);
                } catch (NumberFormatException e) {
                    System.out.println("Failed to parse command");
                    return;
                }
            }

            @Override
            protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        form.add(textField, textFieldX, textFieldY, submit, button);

        return form;
    }

    private Component buildUsernameLabel() {
        String username = GameSession.get().getUsername();
        Model<String> model = new Model<String>(username);
        Label usernameLabel = new Label("username", model);
        return usernameLabel;
    }

    private Component buildIncomeLabel() {
        Label label = new Label("income", new PropertyModel<String>(this, "income"));
        return label;
    }

    private Component buildGoldLabel() {
        Label label = new Label("gold", new PropertyModel<String>(this, "gold"));
        return label;
    }

    public int getGold() {
        return GameSession.get().getGold();
    }

    public int getIncome() {
        return gameMap.getIncome();
    }



    private Component buildMessagesContainer() {
        messagesContainer = new WebMarkupContainer("messages");

        final ListView<GameCommand> listView = new ListView<GameCommand>("message", messages) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<GameCommand> item) {
                this.modelChanging();



                GameCommand message = item.getModelObject();

                //budet vizivatj getPlayerName metod
                Label from = new Label("playerLabel", new PropertyModel<String>(message, "playerName"));
                item.add(from);

                //budet vizivatj getCommand method
                Label text = new Label("commandLabel", new PropertyModel<String>(message, "command"));
                item.add(text);





            }
        };

        messagesContainer.setOutputMarkupId(true);
        messagesContainer.add(listView);


        AjaxSelfUpdatingTimerBehavior ajaxBehavior = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5));
        messagesContainer.add(ajaxBehavior);


        return messagesContainer;
    }

    private Component initGameMap() {

        gameMapComponent = new GameMapComponent("gameMap");

        return gameMapComponent;
    }
}