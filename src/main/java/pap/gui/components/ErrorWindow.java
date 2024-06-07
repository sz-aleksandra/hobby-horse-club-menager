package pap.gui.components;
import javax.swing.*;
import java.util.List;

public class ErrorWindow {

    public ErrorWindow(List<Integer> errors){
        String errorMessage = makeErrorMessage(errors);
        createAndShowGUI(errorMessage);
    }

    private static void createAndShowGUI(String errorMessage) {
            showErrorDialog(errorMessage);

    }

    private static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    private String makeErrorMessage(List<Integer> errors){
        String message = "";
        for (int error: errors) {
            if (error == 1) {message += "Offer not Active.\n";}
            else if (error == 2) {message += "Date not Available.\n";}
            else if (error == 3) {message += "Ending Date cannot be before Start Date.\n";}
            else if (error == 4) {message += "You have to choose payment option.\n";}
        }
        return message;
    }
}
