package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

import java.awt.*;

public class ResizeAnimation implements Animation {

    private final Component component;
    public final Dimension goal;
    private final ResizeAnimationContainer container;
    private boolean complete;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    private final double duration;
    public final Dimension originalDimension;
    private int interval;
    private double widthIncremental;
    private double heightIncremental;
    public double width;
    public double height;
    private boolean cancelled;

    public ResizeAnimation(ResizeAnimationContainer container, Dimension goal, int duration) {
        this.container = container;
        this.component = container.getResizeComponent();
        this.goal = goal;
        this.duration = duration;
        originalDimension = new Dimension(component.getSize());
        setFields();
        initializeContainer();
    }

    private void initializeContainer() {
        container.setPreferredSize(goal);
        container.setResizeAnimation(this);
    }

    private void setFields() {
        width = originalDimension.width;
        height = originalDimension.height;
        interval = 50;
        widthIncremental = (interval / duration * (goal.width - originalDimension.width));
        heightIncremental = (interval / duration * (goal.height - originalDimension.height));
    }

    @Override
    public int interval() {
        return interval;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public void setComplete(boolean b) {
        complete = b;
    }

    @Override
    public void revert() {
        width = originalDimension.width;
        height = originalDimension.height;


        component.setSize(originalDimension);
        component.setLocation(container.getWidth() / 2 - component.getWidth() / 2, container.getHeight() / 2 - component.getHeight() / 2);
    }

    @Override
    public void complete(boolean reverse) {
        component.setSize(reverse ? originalDimension : goal);
    }

    @Override
    public boolean isComplete(boolean reverse) {
        return reverse ? height <= originalDimension.height && width <= originalDimension.width : height >= goal.height && width >= goal.width;
    }

    @Override
    public void progressAnimation(boolean reverse) {
        if (!reverse) {
            width += widthIncremental;
            height += heightIncremental;
        }
        else {
            width -= widthIncremental;
            height -= heightIncremental;
        }
        component.setSize(new Dimension((int) width, (int) height));
        component.setLocation(container.getWidth() / 2 - component.getWidth() / 2, container.getHeight() / 2 - component.getHeight() / 2);
    }

    Thread thread;

    @Override
    public Thread getAnimationThread() {
        return thread;
    }

    @Override
    public void setAnimationThread(Thread thread) {
        this.thread = thread;
    }
}
