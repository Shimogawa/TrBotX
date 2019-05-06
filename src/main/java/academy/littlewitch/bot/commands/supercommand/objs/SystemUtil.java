package academy.littlewitch.bot.commands.supercommand.objs;

import java.io.PrintStream;

public class SystemUtil {

    public void exit(int status) {
        System.exit(status);
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public PrintStream getOut() {
        return System.out;
    }

    public void gc() {
        System.gc();
    }

}
