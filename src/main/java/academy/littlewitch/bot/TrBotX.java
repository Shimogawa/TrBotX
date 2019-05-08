package academy.littlewitch.bot;

import academy.littlewitch.bot.commands.groupcommands.HireMultiplayerCommand;
import academy.littlewitch.bot.commands.groupcommands.WeatherInfoCommand;
import academy.littlewitch.bot.commands.help.HelpCommand;
import academy.littlewitch.bot.commands.jinyan.StartVoteJinyanCommand;
import academy.littlewitch.bot.commands.math.MathCommand;
import academy.littlewitch.bot.commands.supercommand.*;
import academy.littlewitch.bot.commands.supercommand.objs.UtilObject;
import academy.littlewitch.bot.commands.version.VersionCommand;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.listeners.LCommandListener;
import academy.littlewitch.bot.test.TestCommand;
import academy.littlewitch.bot.test.TestListener;
import academy.littlewitch.bot.listeners.qungui.RepeatControlListener;
import academy.littlewitch.bot.listeners.qungui.ShuapinControlListener;
import academy.littlewitch.utils.Updater;
import academy.littlewitch.utils.Version;
import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.PicqConstants;
import cc.moecraft.logger.environments.ColorSupportLevel;
import com.google.gson.JsonSyntaxException;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.apache.commons.cli.*;

import javax.script.ScriptEngine;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TrBotX {
    public static final Version version;

    public static final long god = 714026292L;

    public static long startTime = System.currentTimeMillis();

    public static Properties properties;

    private static PicqConfig botConfig;

    private static PicqBotX bot;

    private static ScriptEngine jsEngine;

    private static int botPort = 31092;
    private static int apiPort = 31091;

    static {
        try {
            properties = new Properties();
            InputStream is = TrBotX.class
                    .getClassLoader().getResourceAsStream("trbotx.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        version = Version.parse(properties.getProperty("application.version"));
    }

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
        postpreparation();
    }

    public static PicqBotX getBot() {
        return bot;
    }

    public static ScriptEngine getJsEngine() {
        return jsEngine;
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

        o = new Option("a",false, "Update the config file");
        options.addOption(o);

        o = new Option("u", false, "Update the jar (program)");
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
            System.out.println("TrBotX " + version.versionString());
            formatter.printHelp("TrBotX [options]", options);
            return false;
        }
        if (cmd.hasOption('v')) {
            System.out.println("TrBotX " + version.versionString());
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
        if (cmd.hasOption('a')) {
            try {
                Configuration.getConfig();
            } catch (JsonSyntaxException e) {
                System.out.println("There is error in config file. Use -c to check.");
                return false;
            }
            Configuration.saveConfig();
            return false;
        }
        if (cmd.hasOption('u')) {
            Updater.update();
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
        newEngine();
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
                        new RepeatControlListener(),
                        new LCommandListener()
                );
        bot.enableCommandManager(Configuration.config.globalCommandConfig.commandPrefix);
        bot.getCommandManager();
        bot.getCommandManager()
                .registerCommands(
                        new HelpCommand(),
                        new StartVoteJinyanCommand(),
                        new UltimateCommand(),
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

    private static void postpreparation() {
        putEngineGlobalVariables();
    }

    private static void putEngineGlobalVariables() {
        jsEngine.put("config", Configuration.config);
        jsEngine.put("bot", bot);
        jsEngine.put("global", new UtilObject());
    }

    private static void newEngine() {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        jsEngine = factory.getScriptEngine();
    }

    public static void resetJsEngine() {
        System.gc();
        newEngine();
        putEngineGlobalVariables();
    }

    private static void onShutdown() {
        Configuration.saveConfig();
    }
}
