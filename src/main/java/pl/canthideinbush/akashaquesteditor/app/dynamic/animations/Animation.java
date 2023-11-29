package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

public interface Animation {

    int interval();

    void revert();

    void complete(boolean reverse);

    boolean isComplete(boolean reverse);

    void progressAnimation(boolean reverse);

    Thread getAnimationThread();

    void setAnimationThread(Thread thread);

    default void start(boolean reverse) {
        if (getAnimationThread() != null) {
            getAnimationThread().interrupt();
        }
        setCancelled(false);
        Thread thread = new Thread(() -> {
            setComplete(false);
            boolean interrupted = false;
            while (!interrupted) {
                if (isCancelled()) {
                    break;
                }
                if (isComplete(reverse)) {
                    complete(reverse);
                    break;
                }
                else progressAnimation(reverse);
                try {
                    Thread.sleep(interval());
                } catch (
                        InterruptedException ignored) {
                    interrupted = true;
                }
            }
            setComplete(true);
        });
        setAnimationThread(thread);
        thread.start();
    }

    void setCancelled(boolean cancelled);
    boolean isCancelled();

    boolean isComplete();

    void setComplete(boolean b);

}
