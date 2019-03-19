package academy.littlewitch.steam.auth;

public class SteamException extends Exception {
    public SteamException() {
        super();
    }

    public SteamException(String reason) {
        super(reason);
    }
}
