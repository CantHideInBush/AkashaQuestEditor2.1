package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

import javax.swing.*;
import java.awt.*;

public class ResizeAnimationContainer extends JPanel {

    private final Component component;
    private final Dimension originalSize;

    public ResizeAnimationContainer(Component component, Dimension originalSize) {
        setLayout(null);
        this.originalSize = originalSize;
        this.component = component;
        setPreferredSize(originalSize);
        add(component);
    }

    protected ResizeAnimation resizeAnimation;

    public void setResizeAnimation(ResizeAnimation animation) {
        this.resizeAnimation = animation;
    }

    public Component getResizeComponent() {
        return component;
    }


    //TODO: Find cause why this method is even needed
    public void centerComponent() {
        component.setSize(originalSize);
        component.setLocation(getPreferredSize().width / 2 - component.getWidth() / 2, getPreferredSize().height / 2 - component.getHeight() / 2);
    }

}
