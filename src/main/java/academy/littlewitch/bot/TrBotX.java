package academy.littlewitch.bot;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.listeners.TestListener;
import academy.littlewitch.bot.listeners.qungui.ShuapinControlListener;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.logger.environments.ColorSupportLevel;

public class TrBotX {
    public static void main(String[] args) {
        if (args.length == 1) {
            Configuration.getConfig();
            Configuration.saveConfig();
            return;
        }
        setConfiguration();
        prepreparation();
        Runtime.getRuntime().addShutdownHook(new Thread(TrBotX::onShutdown));
        start();
    }

    private static void setConfiguration() {
        Configuration.getConfig();
    }

    private static void prepreparation() {
        Configuration.saveConfig();
    }

    private static void start() {
        PicqBotX bot = new PicqBotX("127.0.0.1", 31091, 31092,
                false, ColorSupportLevel.OS_DEPENDENT, "logs", "PicqBotX-Log");
        bot.setHttpApiVersionDetection(".*");
        try {
            bot.getEventManager()
                    .registerListener(new TestListener())
                    .registerListener(new ShuapinControlListener());
            bot.enableCommandManager(Configuration.config.globalCommandConfig.commandPrefix);

            bot.startBot();
        } catch (HttpServerStartFailedException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void onShutdown() {
//        Configuration.saveConfig();
    }
}
