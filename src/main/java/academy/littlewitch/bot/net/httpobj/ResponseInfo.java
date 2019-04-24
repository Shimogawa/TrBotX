package academy.littlewitch.bot.net.httpobj;

public class ResponseInfo {
    private int responseCode;

    private String responseText;

    public ResponseInfo(int responseCode, String responseText) {
        this.responseCode = responseCode;
        this.responseText = responseText;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    @Override
    public String toString() {
        return responseText;
    }
}
