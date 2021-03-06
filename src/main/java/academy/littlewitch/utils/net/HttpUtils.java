package academy.littlewitch.utils.net;

import academy.littlewitch.bot.TrBotX;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.utils.net.httpobj.ResponseInfo;
import academy.littlewitch.utils.KeyValuePair;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.utils.NetUtils;
import cc.moecraft.utils.MapBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

public class HttpUtils {
    private static final String USER_AGENT = "Mozilla/5.0";

    private HttpUtils() {}

    public static void sendToAll(Collection<Long> id, String message) {
        sendToAll(
                TrBotX  .getBot()
                        .getAccountManager()
                        .getAccounts()
                        .get(0)
                        .getHttpApi(),
                id,
                message
        );
    }

    public static void sendToAll(EventMessage em, Collection<Long> id, String message) {
        sendToAll(em.getHttpApi(), id, message);
    }

    public static void sendToAll(IcqHttpApi api, Collection<Long> id, String message) {
        for (long l : id) {
            api.sendPrivateMsg(l, message);
        }
    }

    public static void sendToSupermanagers(EventMessage em, String message) {
        sendToAll(em.getHttpApi(), Configuration.config.superManagers, message);
    }

    @Deprecated
    public static RawReturnData sendThroughApi(String api, Object... params) {
        Map map = MapBuilder.build(String.class, Object.class, params);
        String host = TrBotX.getBot().getAccountManager().getAccounts().get(0).getPostUrl();
        int port = TrBotX.getBot().getAccountManager().getAccounts().get(0).getPostPort();
        String url = NetUtils.url(host, port) + api;
        HttpRequest request = HttpRequest.post(url).body(new JSONObject(map)).timeout(5000);
        if (!TrBotX.getBot().getConfig().getAccessToken().isEmpty()) {
            request.header("Authorization", "Bearer " + TrBotX.getBot().getConfig().getAccessToken());
        }
        JsonElement je = (new JsonParser()).parse(request.execute().body());
        return (new Gson()).fromJson(je, RawReturnData.class);
    }

    public static ResponseInfo sendGetHttp(String url) {
        URL urlObj = formURL(url);
        try {
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", USER_AGENT);

            return getRespFromCon(conn);

        } catch (IOException e) {
            System.out.println("[Error]: " + e.getMessage());
        } catch (NullPointerException e2) {
            System.out.println("[Error] Connection failed.");
        }
        return null;
    }

    public static ResponseInfo sendPostHttp(String url, KeyValuePair<String, String> ...params) {
        URL urlObj = formURL(url);

        try {
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);
            StringJoiner sj = new StringJoiner("&");
            for(KeyValuePair<String,String> entry : params)
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            con.setFixedLengthStreamingMode(length);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            con.connect();
            try(OutputStream os = con.getOutputStream()) {
                os.write(out);
            }

            return getRespFromCon(con);

        } catch (IOException e) {
            System.out.println("[Error]: " + e.getMessage());
        }

        return null;
    }

    public static ResponseInfo sendPostHttp(String url, String json) {
        URL urlObj = formURL(url);

        byte[] out = json.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        try {
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);

            con.setFixedLengthStreamingMode(length);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            con.connect();
            try(OutputStream os = con.getOutputStream()) {
                os.write(out);
            }

            return getRespFromCon(con);

        } catch (IOException e) {
            System.out.println("[Error]: " + e.getMessage());
        }
        return null;
    }

    private static URL formURL(String url) {
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("[Error] " + e.getMessage());
        }
        return urlObj;
    }

    private static ResponseInfo getRespFromCon(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return new ResponseInfo(responseCode, sb.toString());
        } catch (Exception e) {
            return new ResponseInfo(responseCode, null);
        }
    }
}
