package academy.littlewitch.bot;

import academy.littlewitch.bot.commands.groupcommands.HireMultiplayerCommand;
import academy.littlewitch.bot.commands.groupcommands.WeatherInfoCommand;
import academy.littlewitch.bot.commands.help.HelpCommand;
import academy.littlewitch.bot.commands.jinyan.StartVoteJinyanCommand;
import academy.littlewitch.bot.commands.math.CalculatorCommand;
import academy.littlewitch.bot.commands.math.MathCommand;
import academy.littlewitch.bot.commands.supercommand.*;
import academy.littlewitch.bot.commands.version.VersionCommand;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.test.TestCommand;
import academy.littlewitch.bot.test.TestListener;
import academy.littlewitch.bot.listeners.qungui.RepeatControlListener;
import academy.littlewitch.bot.listeners.qungui.ShuapinControlListener;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.PicqConstants;
import cc.moecraft.logger.environments.ColorSupportLevel;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.cli.*;

public class TrBotX {
    public static final String version = "v0.4.8.19";

    private static PicqConfig botConfig;

    private static PicqBotX bot;

    private static int botPort = 31092;
    private static int apiPort = 31091;

    public static void main(String[] args) {
        if (!parseArgs(args)) {
            return;
        }

        if (!readConfiguration()) {
            return;
        }
        prepreparation();
        preparation();
        Runtime.getRuntime().addShutdownHook(new Thread(TrBotX::onShutdown));
        start();
    }

    public static PicqBotX getBot() {
        return bot;
    }

    private static boolean parseArgs(String[] args) {
        Options options = new Options();
        Option o = new Option("h", "help", false, "Get help");
        options.addOption(o);

        o = new Option("v", "version", false, "Version info");
        options.addOption(o);

        o = new Option("c", false, "Check the config file (will create if not exist)");
        options.addOption(o);

        o = new Option("g", false, "Generates the config file (won't generate if exists)");
        options.addOption(o);

        o = new Option("u", false, "Update the config file");
        options.addOption(o);

        o = new Option(null, "port-bot", true, "Bot's port (default 31092)");
        options.addOption(o);

        o = new Option(null, "port-api", true, "Http api's port (default 31091)");
        options.addOption(o);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("TrBotX [options]", options);
            return false;
        }

        if (cmd.hasOption('h')) {
            System.out.println("TrBotX " + version);
            formatter.printHelp("TrBotX [options]", options);
            return false;
        }
        if (cmd.hasOption('v')) {
            System.out.println("TrBotX " + version);
            return false;
        }
        if (cmd.hasOption('c')) {
            try {
                Configuration.getConfig();
                System.out.println("No error found. Config file correct.");
            } catch (JsonSyntaxException e) {
                System.out.print("There is syntax error: ");
                System.out.println(e.getMessage());
            }
            return false;
        }
        if (cmd.hasOption('g')) {
            System.out.println(Configuration.newConfig() ?
                    "Config file created." : "Config file already exist.");
            return false;
        }
        if (cmd.hasOption('u')) {
            try {
                Configuration.getConfig();
            } catch (JsonSyntaxException e) {
                System.out.println("There is error in config file. Use -c to check.");
                return false;
            }
            Configuration.saveConfig();
            return false;
        }
        if (cmd.hasOption("port-bot")) {
            try {
                botPort = Integer.parseInt(cmd.getOptionValue("port-bot"));
            } catch (NumberFormatException e) {
                formatter.printHelp("TrBotX [options]", options);
                return false;
            }
        }
        if (cmd.hasOption("port-api")) {
            try {
                apiPort = Integer.parseInt(cmd.getOptionValue("port-api"));
            } catch (NumberFormatException e) {
                formatter.printHelp("TrBotX [options]", options);
                return false;
            }
        }
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
        PicqConstants.HTTP_API_VERSION_DETECTION = ".*";
    }

    private static void preparation() {
        botConfig = new PicqConfig(botPort);
        botConfig.setMultiAccountOptimizations(false);
        botConfig.setApiAsync(true);
        botConfig.setCommandArgsSplitRegex("\\s");
        botConfig.setColorSupportLevel(ColorSupportLevel.OS_DEPENDENT);
        botConfig.setNoVerify(true);
    }

    private static void start() {
//        PicqBotX bot = new PicqBotX("127.0.0.1", 31091, 31092,
//                false, ColorSupportLevel.OS_DEPENDENT, "logs", "PicqBotX-Log");
//        bot.setHttpApiVersionDetection(".*");
//        bot.setMultiAccountOptimizations(false);
        bot = new PicqBotX(botConfig);
        bot.addAccount("Bot01", "127.0.0.1", apiPort);

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
                        new GroupSendCommand(),
                        new SendAnnouncementCommand(),
                        new PrivateSendCommand()
                );

        bot.startBot();
    }

    private static void onShutdown() {
        Configuration.saveConfig();
    }
}
