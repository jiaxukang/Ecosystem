package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

/**
 * AUTHOR: Jiaxu Kang
 * FILE: PA9Main.java
 * ASSIGNMENT: Programming Assignment 9 - CrisprGUI
 * COURSE: CSc 210; Section C; FALL 2018
 * PURPOSE: This program reads argument to create the size of system.
 * Then user input command to run, that show on GUI. Program
 * can be created, moved, or reproduced.
 * 
 * USAGE:
 * java PA9Main size of grid
 * and user should input the command.
 * 
 * where argument in the following format
 * ----------- EXAMPLE INPUT -------------
 * 10 10
 * -------------------------------------------
 * where command in the following format
 * ----------- EXAMPLE INPUT -------------
 * CREATE (1,7) warbler female 2
 * CREATE (7,1) mosquito female false true false
 * PRINT
 * MOVE
 * REPORDUCE
 * -------------------------------------------
 * 
 * The commands shown above are all of the commands that are supported
 * by this program. It is assumed that (except for some specific errors),
 * the input is well-formed, and matches the format shown above.
 */
public class PA9Main extends Application {

    // variables that will be read in argument and pa6main.
    private static int row;
    private static int col;
    private static EcosySystem system;

    // create the variable in GUI.
    private GraphicsContext gc;
    private TextField cmd_in = new TextField();
    private TextArea print = new TextArea();
    private Button button = new Button("Simulation Step");

    // constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 20;

    public static void main(String[] args) {
        launch(args);
    }

    // Going of launch().
    // parameter:primaryStage
    // Reference to the stage passed to start().
    @Override
    public void start(Stage primaryStage) {
        // get information from argument
        List<String> fileName = getParameters().getRaw();
        row = 20;
        col = 30;
        
        // setup stage.
        system = new EcosySystem(col, row);
        gc = setupStage(primaryStage, col * RECT_SIZE, row * RECT_SIZE, print);
        
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, col * RECT_SIZE, row * RECT_SIZE);
        
        primaryStage.show();
    }

    /**
     * Sets up the whole application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
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

        final int num_items = 3;
        HBox input_box = new HBox(num_items);
        Label cmd = new Label("Insert Command Here -->");
        input_box.getChildren().add(cmd);
        input_box.getChildren().add(cmd_in);
        input_box.getChildren().add(button);

        Canvas canvas = new Canvas(canvas_width, canvas_height);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);

        setupEvent();

        // Place the canvas, command and user input output areas in pane.
        BorderPane.setAlignment(canvas, Pos.CENTER);
        p.setTop(canvas);
        p.setCenter(input_box);
        p.setBottom(print);

        // Title the stage and place the pane into the scene into the stage.
        primaryStage.setTitle("Ecosystem");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }



    // how to set up processing input from a text area
    private void setupEvent() {
        button.setOnAction(new HandleTextInput(cmd_in));

    }

    // read the user command, and process it, then show on the GUI.
    class HandleTextInput implements EventHandler<ActionEvent> {
        private HashMap<String, String> colorMap;
        private String colorName;
        public HandleTextInput(TextField cmd_in) {
            this.cmd_in = cmd_in;
            colorMap = new HashMap<>();
        }

        // when user click the bottom, process following thing.
        @Override
        public void handle(ActionEvent event) {
            HashMap<String, String> types = PA6Main.type();

            // update the system
            system = PA6Main.judge(cmd_in.getText().toLowerCase().split(" "),
                    types, system);
            // print what the user input
            print.appendText(cmd_in.getText() + "\n");

            // map the color.
            colorMap = givenColor();

            // drawing on the GUI
            drawing();
        }

        public void drawing() {
            // loop the system.
            for (int i = 0; i < system.grid.length; i++) {
                for (int j = 0; j < system.grid[i].length; j++) {
                    if (system.grid[i][j].size() == 0) {
                        // drawing.
                        drawTileDebug(cmd_in, gc, "GREEN", j * RECT_SIZE,
                                i * RECT_SIZE, RECT_SIZE);
                    } else {
                        String let = system.grid[i][j].get(0).toString();
                        colorName = colorMap.get(let);
                        drawTileDebug(cmd_in, gc, colorName, j * RECT_SIZE,
                                i * RECT_SIZE, RECT_SIZE);
                    }
                }
            }

        }

        // give different color.
        public HashMap givenColor() {
            colorMap.put("e", "ALICEBLUE");
            colorMap.put("r", "ANTIQUEWHITE");
            colorMap.put("l", "AQUA");
            colorMap.put("c", "AQUAMARINE");
            colorMap.put("g", "BEIGE");
            colorMap.put("z", "BLUE");
            colorMap.put("t", "BLUEVIOLET");
            colorMap.put("o", "CHOCOLATE");
            colorMap.put("w", "BLACK");
            colorMap.put("d", "CRIMSON");
            colorMap.put("s", "CORNFLOWERBLUE");
            colorMap.put("f", "DARKGRAY");
            colorMap.put("b", "DARKMAGENTA");
            colorMap.put("m", "DEEPPINK");
            colorMap.put("h", "FUCHSIA");
            colorMap.put("a", "YELLOW");
            colorMap.put("x", "RED");
            return colorMap;
        }

        // drawing one thing.
        private void drawTileDebug(TextField cmd_in2, GraphicsContext gc,
                String colorName, int x, int y, int size) {
            Color c = Color.valueOf(colorName);
            gc.setFill(c);
            gc.fillRect(x, y, size, size);
        }

        private TextField cmd_in;
    }

}
