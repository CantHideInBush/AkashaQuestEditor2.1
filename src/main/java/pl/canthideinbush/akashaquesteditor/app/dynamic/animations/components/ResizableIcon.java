package pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components;

import pl.canthideinbush.akashaquesteditor.app.dynamic.popups.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class ResizableIcon extends JPanel implements Animate {

    public ResizableIcon(ImageIcon iStatic, ImageIcon iAnimated) {
        this.iconStatic = iStatic;
        this.iconAnimated = iAnimated;
        setOpaque(true);
        setBackground(Color.WHITE);
    }

    private final ImageIcon iconStatic;
    private final ImageIcon iconAnimated;
    public boolean animated = false;

    public boolean firstTick = true;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g.create());

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        AffineTransform stretch = new AffineTransform();
        stretch.scale((double) getWidth() / iconStatic.getIconWidth(), (double) getHeight() / iconStatic.getIconHeight());
        g2d.setTransform(stretch);



        if (firstTick) {
            g2d.drawImage(iconAnimated.getImage(), 0, 0, this);
            firstTick = false;
        }
        else g2d.drawImage(animated ? iconAnimated.getImage() : iconStatic.getImage(), 0, 0, this);

        g2d.dispose();
    }

    @Override
    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

}
