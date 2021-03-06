package application;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Numbers_Guessing1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Random random = new Random();
        int numberToGuess = random.nextInt(101);

        VBox root = new VBox(4);
        primaryStage.setTitle("Guess Numbers #1");
        Label upperlab = new Label("Enter a number between 1 and 100:");
        Label lowerlab = new Label("...");
        TextField userInputTextField = new TextField();
        userInputTextField.setMinWidth(100);
        Button button = new Button("Enter number");

        // handler for both events
        // (pressing enter and clicking 'enter
        // number' button)
        EventHandler<ActionEvent> handler = event -> {
            String guess = userInputTextField.getText();
            int numericGuess;
            try {
                numericGuess = Integer.parseInt(guess);
            } catch (NumberFormatException e) {
                lowerlab.setText(guess + " not a number, try again!");
                userInputTextField.clear();
                return;
            }
            if (numericGuess < 1 || numericGuess > 100) {
                lowerlab.setText(guess + " is outside given range!");
            } else if (numericGuess < numberToGuess) {
                lowerlab.setText(guess + " is too little!");
            } else if (numericGuess > numberToGuess) {
                lowerlab.setText(guess + " is too much!");
            } else {
                upperlab.setText("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!SPOT ON!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                lowerlab.setText("");
                button.setText("QUIT");
                button.setOnAction((exitEvent) -> Platform.exit());
            }
            userInputTextField.clear();
        }; // The end of handler

        userInputTextField.setOnAction(handler);
        button.setOnAction(handler);
        root.getChildren().add(upperlab);
        root.getChildren().add(userInputTextField);
        root.getChildren().add(button);
        root.getChildren().add(lowerlab);
        primaryStage.setScene(new Scene(root, 250, 100));
        primaryStage.show();
    }
}