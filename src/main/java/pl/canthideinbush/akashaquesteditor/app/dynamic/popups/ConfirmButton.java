package pl.canthideinbush.akashaquesteditor.app.dynamic.popups;

import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class ConfirmButton extends JPanel {

    private final ImageIcon icon;
    private final ImageIcon iconStatic;
    public boolean animated = false;

    public ConfirmButton() {
        icon = new ImageIcon(Popups.tick.getImage());
        iconStatic = new ImageIcon(Popups.tickStatic.getImage());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g.create());

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform stretch = new AffineTransform();
        stretch.scale((double) getWidth() / icon.getIconWidth(), (double) getHeight() / icon.getIconHeight());
        g2d.setTransform(stretch);


        g2d.drawImage(animated ? icon.getImage() : iconStatic.getImage(), 0, 0, this);
        g2d.dispose();
    }

}
