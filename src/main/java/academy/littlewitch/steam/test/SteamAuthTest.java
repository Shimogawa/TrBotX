package academy.littlewitch.steam.test;

import academy.littlewitch.steam.auth.Auth;
import academy.littlewitch.steam.auth.SteamException;

public class SteamAuthTest {
    public static void main(String[] args) {
        try {
            String s = Auth.getSteamID64("714026292");
            System.out.println(s);
            int hour = Auth.getGameHoursTerraria(s);
            System.out.println(hour);
        } catch (SteamException e) {
            e.printStackTrace();
        }
    }
}
