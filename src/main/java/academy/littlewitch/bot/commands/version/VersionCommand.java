package academy.littlewitch.bot.commands.version;

import academy.littlewitch.bot.TrBotX;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.utils.Version;
import academy.littlewitch.utils.net.HttpUtils;
import academy.littlewitch.utils.net.httpobj.ResponseInfo;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class VersionCommand implements EverywhereCommand {

    private static JsonParser jsonParser = new JsonParser();

    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        new Thread(() -> {
            ResponseInfo ri = HttpUtils.sendGetHttp("https://api.github.com/repos/Shimogawa/TrBotX/releases");
            String respTxt = ri.getResponseText();
            if (StringUtils.isEmpty(respTxt)) {
                eventMessage.respond(getBotVersion());
                return;
            }
            String postfix = "";
            JsonArray jarr = jsonParser.parse(ri.getResponseText()).getAsJsonArray();
            JsonObject jobj = jarr.get(0).getAsJsonObject();
            Version v = Version.parse(jobj.get("tag_name").getAsString());
            if (v.compareTo(TrBotX.version) == 0) {
                postfix = "（最新版）";
            } else if (v.compareTo(TrBotX.version) > 0) {
                postfix = "\n（更新可用：" + v.versionString() + "）";
                postfix += "\nhttps://github.com/Shimogawa/TrBotX";
            } else {
                postfix = "（先行版）";
            }
            eventMessage.respond(getBotVersion() + postfix);
        }).start();
        return null;
    }

    public static String getBotVersion() {
        return String.format(Configuration.config.versionCommandConfig.version, TrBotX.version.versionString());
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.versionCommand.commandProperties;
    }
}
