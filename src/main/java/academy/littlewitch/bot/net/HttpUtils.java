package academy.littlewitch.bot.net;

import academy.littlewitch.bot.net.httpobj.ResponseInfo;
import academy.littlewitch.bot.utils.KeyValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

public class HttpUtils {
    private static final String USER_AGENT = "Mozilla/5.0";

    private HttpUtils() {}

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
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return new ResponseInfo(responseCode, sb.toString());
        }
    }
}
