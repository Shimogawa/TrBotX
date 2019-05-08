package academy.littlewitch.bot.commands.supercommand.objs;

import java.io.PrintStream;

public class SystemUtil {

    public boolean exit(int status) {
        System.exit(status);
        return true;
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
