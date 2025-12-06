import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

/**
 * MainPage class to handle main page content
 * 
 * @author white
 */
public class MainPage extends BorderPane {
    private final ScrollPane centerScroll;

    public MainPage() {
        centerScroll = new ScrollPane();
        centerScroll.setFitToWidth(true);
        centerScroll.setFitToHeight(true);

        setCenter(centerScroll);
        setCenterContent(new LoginSection(this));
    }

    public void setCenterContent(Node content) {
        centerScroll.setContent(content);
    }
}
