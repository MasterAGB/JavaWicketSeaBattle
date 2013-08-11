package test005;

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
import org.apache.wicket.util.time.Duration;

import java.util.LinkedList;



public class GamePage extends WebPage {
    private static final long serialVersionUID = 1L;

    private static final int MAX_MESSAGES = 50;
    static private final LinkedList<GameCommand> messages = new LinkedList<GameCommand>();

    private MarkupContainer messagesContainer;

    public GamePage() {
        add(buildUsernameLabel(), buildMessagesContainer(), buildForm());
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        String username = GameSession.get().getUsername();
        if (username == null) {
            throw new RestartResponseAtInterceptPageException(ConnectToGamePage.class);
        }
    }

    private Component buildForm() {
        final TextField<String> textField = new TextField<String>("message", new Model<String>());
        textField.setOutputMarkupId(true);


        Form<String> form = new Form<String>("form");


        Button button = new Button("leave") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit() {
                super.onSubmit();

                GameSession.get().setUsername(null);
            }
        };

        form.add(textField);
        form.add(button);


        Component submit = new AjaxSubmitLink("submit") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                String playerName = GameSession.get().getUsername();
                String command = textField.getModelObject();

                GameCommand gameCommand = new GameCommand(playerName, command);

                synchronized (messages) {
                    if (messages.size() >= MAX_MESSAGES) {
                        messages.removeLast();
                    }

                    messages.addFirst(gameCommand);
                }

                textField.setModelObject("");
                target.add(messagesContainer, textField);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                throw new UnsupportedOperationException("nor errors allowed :)");
            }
        };

        form.add(textField, submit);

        return form;
    }

    private Component buildUsernameLabel() {
        String username = GameSession.get().getUsername();
        Model<String> model = new Model<String>(username);
        Label usernameLabel = new Label("username", model);
        return usernameLabel;
    }

    private Component buildMessagesContainer() {
        messagesContainer = new WebMarkupContainer("messages");

        final ListView<GameCommand> listView = new ListView<GameCommand>("message", messages) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<GameCommand> item) {
                this.modelChanging();

                GameCommand message = item.getModelObject();

                Label from = new Label("from", new PropertyModel<String>(message, "from"));
                item.add(from);

                Label text = new Label("text", new PropertyModel<String>(message, "text"));
                item.add(text);
            }
        };

        messagesContainer.setOutputMarkupId(true);
        messagesContainer.add(listView);

        AjaxSelfUpdatingTimerBehavior ajaxBehavior = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5));
        messagesContainer.add(ajaxBehavior);

        return messagesContainer;
    }
}