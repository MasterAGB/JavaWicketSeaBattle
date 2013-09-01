package seabattleWicketPackage;

import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import seabattleWicketPackage.GamePage;

/**
 * Created with IntelliJ IDEA.
 * User: AGB
 * Date: 25.08.13
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class FooterBottom extends Panel {

    public FooterBottom(String id) {
        super(id);
        add(new FeedbackPanel("feedback"));
    }
}
