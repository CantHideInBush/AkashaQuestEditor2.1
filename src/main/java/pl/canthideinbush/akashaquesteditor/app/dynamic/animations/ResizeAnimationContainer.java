package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ResizeAnimationContainer extends JPanel {

    private final Component component;

    public ResizeAnimationContainer(Component component) {
        setLayout(null);
        this.component = component;
        add(component);
    }

    protected ResizeAnimation resizeAnimation;

    public void setResizeAnimation(ResizeAnimation animation) {
        this.resizeAnimation = animation;
    }

    public Component getResizeComponent() {
        return component;
    }

}
