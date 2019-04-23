package academy.littlewitch.steam.auth;

import academy.littlewitch.bot.net.HttpUtils;
import academy.littlewitch.bot.net.httpobj.ResponseInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Auth {
    private static final String GET_ID64_URL = "https://steamcommunity.com/id/%1$s?xml=1";

    private static final String GET_OWNED_GAME_URL = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=%1$s&input_json={\"steamid\":%2$s,\"appids_filter\":[%3$s]}&format=json";
    private static final String MY_KEY = "54A3A9D4946E23BA732CD4F388301791";

    private static final String TERRARIA = "105600";

    public static String getSteamID64(String id) throws SteamException {
        ResponseInfo resp = HttpUtils.sendGetHttp(String.format(GET_ID64_URL, id));
        if (resp == null)
            throw new SteamException("Connection failed.");
        String respTxt = resp.getResponseText();
//        System.out.println(respTxt);
        if (!respTxt.contains("<profile>")) {
            throw new SteamException("No such person");
        }
        int start = respTxt.indexOf("<steamID64>") + 11;
        int end = respTxt.indexOf("</steamID64>");
        String s = respTxt.substring(start, end);
        return s;
    }

    /**
     * Gives the game hours of the specific user with specific game
     * @param userId the user's id, NOT SteamID64.
     * @param gameId
     * @return game hours if has the game, -1 with all other situations.
     */
    public static int getGameHours(String userId, String gameId) {
        ResponseInfo response = HttpUtils.sendGetHttp(String.format(
                GET_OWNED_GAME_URL,
                MY_KEY, userId, gameId
        ));
        if (response == null) return -1;
        String responseText = response.getResponseText();
        JsonParser parser = new JsonParser();
        JsonElement e = parser.parse(responseText);
        JsonObject responseObj = e.getAsJsonObject().get("response").getAsJsonObject();
        int count = responseObj.get("game_count").getAsInt();
        if (count == 0) return -1;

        int playtime = responseObj.get("games")
                                  .getAsJsonArray()
                                  .get(0)
                                  .getAsJsonObject()
                                  .get("playtime_forever")
                                  .getAsInt();
        return playtime / 60;
    }

    public static int getGameHoursTerraria(String userId) {
        return getGameHours(userId, TERRARIA);
    }
}
