package academy.littlewitch.bot;

import academy.littlewitch.bot.commands.groupcommands.HireMultiplayerCommand;
import academy.littlewitch.bot.commands.help.HelpCommand;
import academy.littlewitch.bot.commands.jinyan.StartVoteJinyanCommand;
import academy.littlewitch.bot.commands.math.CalculatorCommand;
import academy.littlewitch.bot.commands.math.MathCommand;
import academy.littlewitch.bot.commands.supercommand.ChangeConfigCommand;
import academy.littlewitch.bot.commands.version.VersionCommand;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.test.TestCommand;
import academy.littlewitch.bot.test.TestListener;
import academy.littlewitch.bot.listeners.qungui.RepeatControlListener;
import academy.littlewitch.bot.listeners.qungui.ShuapinControlListener;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;

public class TrBotX {
    public static final String version = "v0.4.0";

    private static PicqConfig botConfig;

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
        botConfig = new PicqConfig(31092);
        botConfig.setMultiAccountOptimizations(false);
        botConfig.setApiAsync(true);
        botConfig.setCommandArgsSplitRegex("\\s");
    }

    private static void start() {
//        PicqBotX bot = new PicqBotX("127.0.0.1", 31091, 31092,
//                false, ColorSupportLevel.OS_DEPENDENT, "logs", "PicqBotX-Log");
//        bot.setHttpApiVersionDetection(".*");
//        bot.setMultiAccountOptimizations(false);
        PicqBotX bot = new PicqBotX(botConfig);
        bot.addAccount("Bot01", "127.0.0.1", 31091);

        // Debug Mode
        if (Configuration.config.debugMode) {
            bot.getEventManager().registerListener(new TestListener());
            bot.enableCommandManager(Configuration.config.globalCommandConfig.commandPrefix);
            bot.getCommandManager().registerCommand(new TestCommand());
            bot.startBot();
            return;
        }

        // EventListeners
        bot.getEventManager()
                .registerListeners(
                        new ShuapinControlListener(),
                        new RepeatControlListener()
                );
        bot.enableCommandManager(Configuration.config.globalCommandConfig.commandPrefix);
        bot.getCommandManager();
        bot.getCommandManager()
                .registerCommands(
                        new HelpCommand(),
                        new StartVoteJinyanCommand(),
                        new CalculatorCommand(),
                        new MathCommand(),
                        new ChangeConfigCommand(),
                        new VersionCommand(),
                        new ChangeConfigCommand(),
                        new HireMultiplayerCommand()
                );

        bot.startBot();
    }

    private static void onShutdown() {
//        Configuration.saveConfig();
    }
}
