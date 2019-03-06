package academy.littlewitch.bot.listeners.qungui;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

public class RepeatControlListener extends IcqListener {

    private String lastSameMessage = null;

    private int fuduNumber = 1;

    @EventHandler
    public void checkGroupFudu(EventGroupMessage egm) {
        if (!egm.rawMessage.equals(lastSameMessage)) {
            lastSameMessage = egm.rawMessage;
            fuduNumber = 1;
            return;
        }

        fuduNumber++;
        if (fuduNumber >= Configuration.config.monitorConfig.minRepeatToBeBanned) {
            egm.ban(Configuration.config.monitorConfig.jinyanTime);
            egm.respond(Configuration.config.monitorConfig.warningWords);
        }
    }
}
