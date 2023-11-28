package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

import java.awt.*;

public class ResizeAnimation implements Animation {

    private final Component component;
    private final Dimension goal;
    private final double duration;
    private final Point originalLocation;
    private final Dimension originalDimension;
    private int interval;
    private double widthIncremental;
    private double heightIncremental;
    private double width;
    private double height;

    public ResizeAnimation(Component component, Dimension goal, int duration) {
        this.component = component;
        this.goal = goal;
        this.duration = duration;
        originalDimension = component.getPreferredSize();
        originalLocation = component.getLocation();
        setFields();
    }

    private void setFields() {
        width = originalDimension.width;
        height = originalDimension.height;
        interval = 1;
        widthIncremental = (interval / duration * (goal.width - originalDimension.width));
        heightIncremental = (interval / duration * (goal.height - originalDimension.height));
    }

    @Override
    public int interval() {
        return interval;
    }

    @Override
    public void cancel() {
        width = originalDimension.width;
        height = originalDimension.height;

        component.setSize(originalDimension);
        thread.interrupt();
    }

    @Override
    public void complete() {
        component.setPreferredSize(goal);
    }

    @Override
    public boolean isComplete() {
        return height >= goal.height && width >= goal.width;
    }

    @Override
    public void progressAnimation() {
        width += widthIncremental;
        height += heightIncremental;
        component.setSize(new Dimension((int) width, (int) height));
        component.getParent().invalidate();
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
