package academy.littlewitch.bot.utils;

public class TriValue<T1, T2, T3> {
    public T1 elem1;
    public T2 elem2;
    public T3 elem3;

    public TriValue(T1 e1, T2 e2, T3 e3) {
        elem1 = e1;
        elem2 = e2;
        elem3 = e3;
    }
}
