package academy.littlewitch.bot.config;

import academy.littlewitch.bot.config.innerconfig.GlobalCommandConfig;
import academy.littlewitch.bot.config.innerconfig.VersionCommandConfig;
import academy.littlewitch.bot.config.innerconfig.VoteForJinyanConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configuration {
    public static Configuration config;

    public static final String CONFIG_FILE = "config.json";

    public GlobalCommandConfig globalCommandConfig = new GlobalCommandConfig();
    public VoteForJinyanConfig voteForJinyanConfig = new VoteForJinyanConfig();
    public VersionCommandConfig versionCommandConfig = new VersionCommandConfig();

    public static void getConfig() {
        System.out.println("[Info]: Reading config...");
        try {
            File file = new File(CONFIG_FILE);
            if (!file.exists()) {
                file.createNewFile();
                config = new Configuration();
                saveConfig();
                return;
            }
            String json = new String(
                    Files.readAllBytes(Paths.get(CONFIG_FILE)), StandardCharsets.UTF_8);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            config = g.fromJson(json, Configuration.class);
        } catch (IOException e) {
            System.out.println("[Error]: " + e.getMessage());
        }
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
