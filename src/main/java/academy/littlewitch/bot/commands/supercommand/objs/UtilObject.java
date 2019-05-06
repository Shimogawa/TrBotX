package academy.littlewitch.bot.commands.supercommand.objs;

import academy.littlewitch.bot.TrBotX;

public class UtilObject {

    public ListUtil Lists = new ListUtil();

    public SystemUtil system = new SystemUtil();

    public CollectionsUtil Collections = new CollectionsUtil();

    public Class<Integer> Integer = Integer.class;

    public Class<String> String = String.class;

    public Class<Double> Double = Double.class;

    public Class<Float> Float = Float.class;


    public void reset() {
        TrBotX.resetJsEngine();
    }

    @Override
    public String toString() {
        return "global";
    }
}
