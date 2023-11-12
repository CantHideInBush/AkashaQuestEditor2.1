package pl.canthideinbush.akashaquesteditor.app.dynamic;

import pl.canthideinbush.akashaquesteditor.app.Application;

import javax.swing.*;
import java.awt.*;

public class ConversationComposer extends JLayeredPane {

    public ConversationComposer() {
        initialize();
    }

    private void initialize() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setBackground(new Color(37, 41, 51));
        setOpaque(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int h = 10; h <= getHeight() - 10; h+= 40) {
            for (int w = 10; w <= getWidth() - 10; w+=40) {
                g2d.setColor(new Color(73, 79, 85));
                g2d.fillRect(w, h, 3, 3);
            }
        }
    }
}
