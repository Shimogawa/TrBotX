package academy.littlewitch.bot.commands.groupcommands;

import academy.littlewitch.bot.TrBotX;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.utils.Util;
import academy.littlewitch.utils.net.HttpUtils;
import academy.littlewitch.utils.net.httpobj.ResponseInfo;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cn.hutool.core.io.IoUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherInfoCommand implements GroupCommand {

    private static final String CITY_FILE = "city.json";

    private static final String API_URL = "https://www.tianqiapi.com/api/?version=v1&cityid=%1$s";

    private static final String RESULT_FORMAT = "【%1$s】（今天）\n日期：%2$s\n天气：%3$s\n" +
            "当前气温：%4$s\n当日气温：%5$s / %6$s\n湿度：%7$s%%\n风向：%8$s（%9$s）\n紫外线指数：%10$s\n" +
            "空气污染指数：%11$s（%12$s）\n更新时间：%13$s";

    private static JsonParser jsonParser = new JsonParser();

    public static HashMap<String, CityInfo> cityChineseDict;

    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.globalCommandConfig.weatherInfoCommand.enabled)
            return null;
        if (cityChineseDict == null) {
            if (!readCityInfo(eventGroupMessage)) {
                return null;
            }
        }
        if (arrayList.size() != 1)
            return null;
        String cityName = arrayList.get(0);
        if (!cityChineseDict.containsKey(cityName)) {
            return null;
        }
        new Thread(() -> {
            ResponseInfo resp =
                    HttpUtils.sendGetHttp(String.format(API_URL, cityChineseDict.get(cityName).id));
            String r = resp.getResponseText();
            JsonObject jobj = jsonParser.parse(r).getAsJsonObject();
            String city = jobj.get("city").getAsString();
//            String country = jobj.get("country").getAsString();
            JsonObject dataObj = jobj.get("data").getAsJsonArray().get(0).getAsJsonObject();
            String date = dataObj.get("date").getAsString();
            String wea = dataObj.get("wea").getAsString();
            String tem = dataObj.get("tem").getAsString();
            String temHigh = dataObj.get("tem1").getAsString();
            String temLow = dataObj.get("tem2").getAsString();
            String windDir = dataObj.get("win").getAsJsonArray().get(0).getAsString();
            String windSpd = dataObj.get("win_speed").getAsString();
            String uvLvl = dataObj.get("index").getAsJsonArray().get(0).getAsJsonObject().get("level").getAsString();
            String airQ = dataObj.get("air").getAsString();
            String airLevel = dataObj.get("air_level").getAsString();
            String hum = dataObj.get("humidity").getAsString();
            String updTime = jobj.get("update_time").getAsString();
            String finalStr = String.format(
                    RESULT_FORMAT,
                    city, date, wea, tem, temHigh, temLow, hum, windDir, windSpd, uvLvl, airQ, airLevel, updTime
            );
            eventGroupMessage.getHttpApi().sendGroupMsg(group.getId(), finalStr);
        }).start();
        return null;
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.weatherInfoCommand.commandProperties;
    }

    private static boolean readCityInfo(EventGroupMessage event) {
        try {
            InputStream is = TrBotX.class.getClassLoader().getResourceAsStream(CITY_FILE);
            String json = Util.readAll(is);
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(is, StandardCharsets.UTF_8));
//            String json = new String(
//                    Files.readAllBytes(Paths.get(CITY_FILE)), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            CityInfo[] cityInfos = gson.fromJson(json, CityInfo[].class);
            cityChineseDict = new HashMap<>();
            for (int i = 0; i < cityInfos.length; i++) {
                cityChineseDict.put(cityInfos[i].cityZh, cityInfos[i]);
            }
        } catch (Exception e) {
            HttpUtils.sendToSupermanagers(event, e.getLocalizedMessage());
            cityChineseDict = null;
            return false;
        }
        return true;
    }
}
