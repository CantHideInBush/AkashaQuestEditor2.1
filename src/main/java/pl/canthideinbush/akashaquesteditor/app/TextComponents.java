package pl.canthideinbush.akashaquesteditor.app;

import javax.swing.text.JTextComponent;
import java.awt.*;

public class TextComponents {

    public static void disableSelection(JTextComponent textComponent) {
        textComponent.setEditable(false);
        textComponent.getCaret().setBlinkRate(0);
        textComponent.getCaret().setVisible(false);
        textComponent.setHighlighter(null);
    }

}
