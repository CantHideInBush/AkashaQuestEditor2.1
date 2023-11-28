package pl.canthideinbush.akashaquesteditor.app.dynamic.popups;

import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class ConfirmButton extends ResizeAnimationContainer {

    private JPanel confirmButton;

    public ConfirmButton() {
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(null);
        confirmButton = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = ((Graphics2D) g);
                AffineTransform transform = new AffineTransform();

                g2d.setTransform(transform);


                g2d.drawImage(Popups.tick.getImage(), 0, 0, this);

            }

        };

        confirmButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));
        add(confirmButton);
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        confirmButton.setPreferredSize(preferredSize);
    }

    @Override
    public void setSize(@NotNull Dimension d) {
        super.setSize(d);
        confirmButton.setSize(d);
    }

    @Override
    protected void layoutComponents() {
        confirmButton.setLocation(getWidth() / 2 - confirmButton.getWidth() / 2, getHeight() / 2 - confirmButton.getHeight() / 2);
    }
}
