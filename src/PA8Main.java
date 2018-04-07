
/*
 * TODO: Write a file header!
 */
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PA8Main extends Application {

    // variables that will be read in from file
    private static double delay = 2;

    // constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 20;

    // temporary constants for starter code
    private final static int SIZE_ACROSS = 100;
    private final static int SIZE_DOWN = 200;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // PA8 TODO: change SIZE_ACROSS to something like
        // num_cols * plot_size * CELLSIZE and SIZE_DOWN
        // to something like num_rows * plot_size * CELLSIZE
        TextArea command = new TextArea();
        GraphicsContext gc = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN,
                command);

        primaryStage.show();
        simulateGarden(gc, command);
    }

    /**
     * TODO: PA8 Rewrite this method. Take out the given placeholder code
     * (and comments), implement the provided pseudocode, and make the
     * method more succinct so it is less than 30 lines.
     * 
     * @param gc
     *            GraphicsContext for drawing garden to.
     * @param command
     *            Reference to text area to show simulation commands.
     */
    private void simulateGarden(GraphicsContext gc, TextArea command) {
        //------- Placeholder code (REMOVE once you have the above code)
        // This count object is taking the place of the Scanner.
        // Below you will want to pass a reference to your Scanner into
        // your "read in the next command" code. The Scanner object
        // keeps track of where it is in the file. For the placeholder
        // code we are providing here, there is no Scanner. Instead
        // there is this Counter object that the lambda function will
        // have a reference to.
        final int NUM_STEPS = 10;
        Counter countref = new Counter(NUM_STEPS);

        // ========== Keep code, but might reduce comments
        // Update GUI based on value of delay(seconds to wait)
        // The below code constructs a PauseTransition and then
        // passes a lambda function into the setOnFinished method.
        // A lambda function is an unnamed function.  Here, the
        // unnamed function takes a parameter it names "e"
        // and then parses the next command out of the file,
        // applies the command to the Garden, and then tells the
        // wait thread to pause for the delay again.
        PauseTransition wait = new PauseTransition(Duration.seconds(delay));
        wait.setOnFinished((e) -> {

            //==== Code that should be executed after each delay goes in here.
            // read in the next command
            // if there was a command left in the file:
            // * apply that command to the garden
            // * draw to the canvas by calling your gardenDraw(gc)
            // * append the command to the text field, command.appendText(...);
            // * call wait.playFromStart();
            // else:
            // * call wait.stop();

            //---- Placeholder code (REMOVE once you have the above code)
            // This place holder code will draw a rectangle that
            // moves down the screen count times.
            if (countref.count > 0) {
                drawTileDebug(command, gc, "RED", SIZE_ACROSS / 2,
                        countref.count * RECT_SIZE, RECT_SIZE);
                countref.count--;
                wait.playFromStart();
            } else {
                wait.stop();
            }

        });

        // Now that the PauseTransition thread is setup, get it going.
        wait.play();
    }

    /**
     * Sets up the whole application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
     * PA8 Notes: You shouldn't need to modify this method.
     * 
     * @param primaryStage
     *            Reference to the stage passed to start().
     * @param canvas_width
     *            Width to draw the canvas.
     * @param canvas_height
     *            Height to draw the canvas.
     * @param command
     *            Reference to a TextArea that will be setup.
     * @return Reference to a GraphicsContext for drawing on.
     */
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width,
            int canvas_height, TextArea command) {
        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Canvas(pixels across, pixels down)
        // Note this is opposite order of parameters of the Garden in PA6.
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);

        // Place the canvas and command output areas in pane.
        p.setCenter(canvas);
        p.setBottom(command);

        // Title the stage and place the pane into the scene into the stage.
        primaryStage.setTitle("Garden");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }

    // Helper method to draw a rectangular tile and output info in given
    // text area.
    private void drawTileDebug(TextArea command, GraphicsContext gc,
            String colorname, int x, int y, int size) {
        command.appendText("drawTileDebug: x=" + x + ", y = " + y + "\n");
        Color c = Color.valueOf(colorname);
        gc.setFill(c);
        gc.fillRect(x, y, size, size);
    }

}
