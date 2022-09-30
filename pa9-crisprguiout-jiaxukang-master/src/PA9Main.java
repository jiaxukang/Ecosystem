import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class PA9Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // ===== set up the scene with a text box and button for input
        BorderPane p = new BorderPane();
        TextField cmd_in = new TextField();
        Button button = new Button("Process");
        final int num_items = 2;
        HBox input_box = new HBox(num_items);
        input_box.getChildren().add(cmd_in);
        input_box.getChildren().add(button);
        p.setBottom(input_box);

        // === Example of how to set up processing input from a text area
        button.setOnAction(new HandleTextInput(cmd_in));

        // Alternative: using an anonymous class
        // button.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // System.out.println(cmd_in.getText());
        // }
        // });

        // Alternative: use lambda function
        // button.setOnAction((event) -> {
        // System.out.println(cmd_in.getText());
        // });

        // Connect the border pane into the scene and show the window.
        primaryStage.setTitle("Sample input field");
        primaryStage.setScene(new Scene(p));
        primaryStage.show();
    }

    class HandleTextInput implements EventHandler<ActionEvent> {
        public HandleTextInput(TextField cmd_in) {
            this.cmd_in = cmd_in;
        }

        @Override
        public void handle(ActionEvent event) {
            System.out.println(cmd_in.getText());
        }

        private TextField cmd_in;
    }

}
