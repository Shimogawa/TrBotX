package academy.littlewitch.bot;

import academy.littlewitch.bot.commands.groupcommands.HireMultiplayerCommand;
import academy.littlewitch.bot.commands.groupcommands.WeatherInfoCommand;
import academy.littlewitch.bot.commands.help.HelpCommand;
import academy.littlewitch.bot.commands.jinyan.StartVoteJinyanCommand;
import academy.littlewitch.bot.commands.math.CalculatorCommand;
import academy.littlewitch.bot.commands.math.MathCommand;
import academy.littlewitch.bot.commands.supercommand.ChangeConfigCommand;
import academy.littlewitch.bot.commands.supercommand.GroupSendCommand;
import academy.littlewitch.bot.commands.supercommand.ServerMonitorCommand;
import academy.littlewitch.bot.commands.version.VersionCommand;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.test.TestCommand;
import academy.littlewitch.bot.test.TestListener;
import academy.littlewitch.bot.listeners.qungui.RepeatControlListener;
import academy.littlewitch.bot.listeners.qungui.ShuapinControlListener;
import academy.littlewitch.utils.GoodStrBuilder;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import com.google.gson.JsonSyntaxException;

public class TrBotX {
    public static final String version = "v0.4.6";

    private static PicqConfig botConfig;

    public static void main(String[] args) {
        if (parseArgs(args)) {
            return;
        }
        if (!readConfiguration()) {
            return;
        }
        prepreparation();
        Runtime.getRuntime().addShutdownHook(new Thread(TrBotX::onShutdown));
        start();
    }

    private static boolean parseArgs(String[] args) {
        if (args.length == 0)
            return false;
        if ("-h".equals(args[0].toLowerCase())) {
            GoodStrBuilder sb = new GoodStrBuilder();
            sb.append("TrBotX ")
                    .append(version).newLine()
                    .append("-h\t| help").newLine()
                    .append("-v\t| version info").newLine()
                    .append("-c\t| Check the config file (will create if not exist)").newLine()
                    .append("-g\t| Generates the config file (won't generate if exists)").newLine();
            System.out.println(sb.toString());
            return true;
        }
        if ("-v".equals(args[0].toLowerCase())) {
            System.out.println("TrBotX " + version);
            return true;
        }
        if ("-c".equals(args[0].toLowerCase())) {
            try {
                Configuration.getConfig();
                System.out.println("No error found. Config file correct.");
            } catch (JsonSyntaxException e) {
                System.out.print("There is syntax error: ");
                System.out.println(e.getMessage());
            }
            return true;
        }
        if ("-g".equals(args[0].toLowerCase())) {
            boolean b = Configuration.newConfig();
            System.out.println(b ? "Config file created." : "Config file already exist.");
            return true;
        }
        System.out.println("Incorrect command. -h for help.");
        return true;
    }

    private static boolean readConfiguration() {
        try {
            Configuration.getConfig();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void prepreparation() {
//        Configuration.saveConfig();
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
                        new HireMultiplayerCommand(),
                        new WeatherInfoCommand(),
                        new ServerMonitorCommand(),
                        new GroupSendCommand()
                );

        bot.startBot();
    }

    private static void onShutdown() {
        Configuration.saveConfig();
    }
}
