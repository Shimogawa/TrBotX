package academy.littlewitch.bot.config;

import academy.littlewitch.bot.config.innerconfig.GlobalCommandConfig;
import academy.littlewitch.bot.config.innerconfig.HireMPCommandConfig;
import academy.littlewitch.bot.config.innerconfig.VersionCommandConfig;
import academy.littlewitch.bot.config.innerconfig.VoteForJinyanConfig;
import academy.littlewitch.bot.config.innerconfig.monitorconfig.MonitorConfig;
import academy.littlewitch.bot.config.innerconfig.monitorconfig.SuperCommandConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class Configuration {
    public static Configuration config;

    public static final String CONFIG_FILE = "config.json";

    @SerializedName("超级管理员")
    public HashSet<Long> superManagers = new HashSet<>();

    @SerializedName("泰拉群组号")
    public HashSet<Long> trGroups = new HashSet<>();

    @SerializedName("全局指令设定")
    public GlobalCommandConfig globalCommandConfig = new GlobalCommandConfig();

    @SerializedName("投票设定")
    public VoteForJinyanConfig voteForJinyanConfig = new VoteForJinyanConfig();

    @SerializedName("版本设定")
    public VersionCommandConfig versionCommandConfig = new VersionCommandConfig();

    @SerializedName("群管理设定")
    public MonitorConfig monitorConfig = new MonitorConfig();

    @SerializedName("发起联机设定")
    public HireMPCommandConfig hireConfig = new HireMPCommandConfig();

//    @SerializedName("加群验证设定")
//    public AuthConfig authConfig = new AuthConfig();

    @SerializedName("超级指令设定")
    public SuperCommandConfig superCommandConfig = new SuperCommandConfig();

    @SerializedName("备份间隔")
    public long backupInterval = 43200;

    public boolean debugMode = false;

    private Configuration() {}

    public static void getConfig() throws JsonSyntaxException {
        System.out.println("[Info]: Reading config...");
        try {
            if (newConfig())
                return;
            String json = new String(
                    Files.readAllBytes(Paths.get(CONFIG_FILE)), StandardCharsets.UTF_8);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            config = g.fromJson(json, Configuration.class);
        } catch (IOException e) {
            System.out.println("[Error]: " + e.getMessage());
//            System.out.println("Saving new settings...");
//            config = new Configuration();
//            saveConfig();
            System.exit(-1);
        }
    }

    public static boolean newConfig() {
        File file = new File(CONFIG_FILE);
        if (file.exists())
            return false;
        try {
            file.createNewFile();
            config = new Configuration();
            saveConfig();
        } catch (IOException e) {
            System.out.println("[Error]: " + e.getMessage());
            System.exit(-1);
        }
        return true;
    }

    public static void saveConfig() {
        System.out.println("[Info]: Saving config...");
        try {
            File file = new File(CONFIG_FILE);
            if (!file.exists()) throw new IOException("File not found");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String s = g.toJson(config);
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), StandardCharsets.UTF_8))) {
                bw.write(s);
            }
        } catch (IOException e) {
            System.out.println("[Error]: " + e.getMessage());
        } catch (Exception ex) {
            System.out.println("[Error]: " + ex.getMessage());
        }
    }
}
