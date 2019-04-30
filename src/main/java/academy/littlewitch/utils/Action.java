package academy.littlewitch.utils;

public interface Action {

    void exec();

    static Action join(Action a1, Action a2) {
        return () -> {
            a1.exec();
            a2.exec();
        };
    }

    default Action join(Action another) {
        return join(this, another);
    }
}
