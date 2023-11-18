package pl.canthideinbush.akashaquesteditor.app.dynamic.popups;

import pl.canthideinbush.akashaquesteditor.app.Application;

import javax.swing.*;
import java.awt.*;

public class Popups {


    public static String createShortTextPopup(String title, String message, String initial) {
        return (String) JOptionPane.showInputDialog(Application.instance, message, title, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Application.icon.getScaledInstance(64, 64,  Image.SCALE_SMOOTH)), null, initial);
    }

}
