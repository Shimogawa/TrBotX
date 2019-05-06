package academy.littlewitch.bot.listeners;

import academy.littlewitch.bot.TrBotX;
import academy.littlewitch.utils.GoodStrBuilder;
import academy.littlewitch.utils.Util;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventMessage;

public class LCommandListener extends IcqListener {

    @EventHandler
    public void control(EventMessage e) {
        if (e.senderId != TrBotX.god || !e.rawMessage.startsWith("!@stat"))
            return;
        GoodStrBuilder gsb = new GoodStrBuilder();
        gsb     .append("TrBotX ").append(TrBotX.version.versionString())
                .newLine()
                .append("Has been running for ")
                .append(Util.toDateString(System.currentTimeMillis() - TrBotX.startTime));
        e.respond(gsb.toString());
    }

}
