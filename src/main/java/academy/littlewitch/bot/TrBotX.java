package academy.littlewitch.bot;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.listeners.TestListener;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.logger.environments.ColorSupportLevel;

public class TrBotX {
    public static void main(String[] args) {
        setConfiguration();
        Runtime.getRuntime().addShutdownHook(new Thread(TrBotX::onShutdown));
        start();
    }

    private static void setConfiguration() {
        Configuration.getConfig();
    }

    private static void start() {
        PicqBotX bot = new PicqBotX("127.0.0.1", 31091, 31092,
                false, ColorSupportLevel.OS_DEPENDENT, "logs", "PicqBotX-Log");
        bot.setHttpApiVersionDetection(".*");
        try {
//            bot.getEventManager()
//                    .registerListener(new TestListener());
            bot.enableCommandManager(Configuration.config.globalCommandConfig.commandPrefix);

            bot.startBot();
        } catch (HttpServerStartFailedException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void onShutdown() {
        Configuration.saveConfig();
    }
}
