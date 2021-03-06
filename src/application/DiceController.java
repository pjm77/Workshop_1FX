package application;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class DiceController extends GridPane {

    @FXML
    public Label infoLabel;
    @FXML
    public TextField expressionTF;
    public Label rollResultLabel;
    public Button rollButton;
    @FXML
    public ToggleGroup diceTypeTG;
    public Label multiplierL;
    public Label diceTypeL;
    public Label modifierL;
    public TextField multiplierTF;
    public Button multiplierPlusB;
    public Button multiplierMinusB;
    public TextField modifierTF;
    public Button modifierPlusB;
    public Button modifierMinusB;
    public ToggleButton tBD3;
    public ToggleButton tBD4;
    public ToggleButton tBD6;
    public ToggleButton tBD8;
    public ToggleButton tBD10;
    public ToggleButton tBD12;
    public ToggleButton tBD20;
    public ToggleButton tBD100;

    @FXML
    void initialize() {
        multiplierL.setUserData((long) 1); // setting initial values for multiplier, modifier and dice type
        modifierL.setUserData((long) 0);
        diceTypeL.setUserData(0);
        tBD3.setUserData(3); // setting user data for dice type toggle buttons
        tBD4.setUserData(4);
        tBD6.setUserData(6);
        tBD8.setUserData(8);
        tBD10.setUserData(10);
        tBD12.setUserData(12);
        tBD20.setUserData(20);
        tBD100.setUserData(100);
        // listener to detect change in
// multiplier text field
        multiplierTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                handleMultiplierTF();
            }
        });
        // listener to detect change in
// modifier textfield
        modifierTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                handleModifierTF();
            }
        });
    }

    private long calculateResult(long multiplier, int diceType, long modifier) { // method to calculate dice roll
        Random rand = new Random();
        long finalResult = 0;
        for (long l = 0; l < multiplier; l++) {
            long result = rand.nextInt(diceType) + 1;
            finalResult = finalResult + result;
        }
        finalResult = finalResult + modifier;
        return finalResult;
    }

    public void handleRollButton() { // handler for the Roll button
        String expression = expressionTF.getText(); // detect if expression has been manually entered
        String comment = "";
        if (expression.matches("^\\d*d([3468]|100|12|20|10)\\s*([+\\-])\\s*\\d*$")) {
            long multiplier = 0L;
            int diceType = 0;
            long modifier = 0L;
            Pattern multiplierPattern = Pattern.compile("\\d*d");
            Matcher multiplierMatcher = multiplierPattern.matcher(expression);
            if(multiplierMatcher.find()){
                multiplier = Long.parseLong(multiplierMatcher.group(0).replaceAll("d$", ""));
            }
            Pattern diceTypePattern = Pattern.compile("d([3468]|100|12|20|10)");
            Matcher diceTypeMatcher = diceTypePattern.matcher(expression);
            if(diceTypeMatcher.find()){
                diceType = Integer.parseInt(diceTypeMatcher.group(0).replaceAll("^d", ""));
            }
            Pattern modifierPattern = Pattern.compile("([+\\-])+\\s*\\d*$");
            Matcher modifierMatcher = modifierPattern.matcher(expression);
            if(modifierMatcher.find()){
                modifier = Long.parseLong(modifierMatcher.group(0).replaceAll("^([+\\-])+\\s*", ""));
            }
            rollResultLabel.setText("" + calculateResult(multiplier, diceType, modifier));
        } else { // if not, or expression was invalid - use UI selected parameters
            if (!expression.equals(null) && !expression.equals("")) {
                comment = "Invalid expression, using parameters!\n";
            }
            try {
                long multiplier = (long) multiplierL.getUserData();
                int diceType = (int) diceTypeL.getUserData();
                long modifier = (long) modifierL.getUserData();
                rollResultLabel.setText(comment + calculateResult(multiplier, diceType, modifier));
            } catch (Exception e) {
                e.printStackTrace();
                rollResultLabel.setText("No expression entered!\nInvalid parameters!");
            }
        }
    }

    public void handleMultiplierTF() { // multiplier text field handler
        try {
            long multiplier = Long.parseLong(multiplierTF.getText());
            if (multiplier < 1) {
                multiplierTF.setText("1");
                multiplierL.setUserData(1);
            } else {
                multiplierL.setUserData(multiplier);
            }
        } catch (Exception e) {
            multiplierTF.setText("1");
            multiplierL.setUserData((long) 1);
        }
    }

    public void handleMultiplierPlusB() { // multiplier plus button handler
        long multiplier = (long) multiplierL.getUserData();
        multiplier++;
        multiplierTF.setText("" + multiplier);
        multiplierL.setUserData(multiplier);
    }

    public void handleMultiplierMinusB() { // multiplier minus button handler
        long multiplier = (long) multiplierL.getUserData();
        if (multiplier > 1) {
            multiplier--;
            multiplierTF.setText("" + multiplier);
            multiplierL.setUserData(multiplier);
        }
    }

    public void handleModifierTF() { // modifier text field handler
        try {
            long modifier = Long.parseLong(modifierTF.getText());
            modifierL.setUserData(modifier);
        } catch (Exception e) {
            modifierTF.setText("0");
            modifierL.setUserData((long) 0);
        }
    }

    public void handleModifierPlusB() { // modifier plus button handler
        long modifier = (long) modifierL.getUserData();
        modifier++;
        modifierTF.setText("" + modifier);
        modifierL.setUserData(modifier);
    }

    public void handleModifierMinusB() { // modifier minus button handler
        long modifier = (long) modifierL.getUserData();
        modifier--;
        modifierTF.setText("" + modifier);
        modifierL.setUserData(modifier);
    }

    public void handleToggleButtons() { // toggle buttons handler
        if (diceTypeTG.getSelectedToggle() == null) {
            diceTypeL.setUserData(0);
        } else {
            diceTypeL.setUserData(diceTypeTG.getSelectedToggle().getUserData());
        }
    }
}