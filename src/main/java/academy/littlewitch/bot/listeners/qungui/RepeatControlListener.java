package academy.littlewitch.bot.listeners.qungui;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.utils.Util;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

public class RepeatControlListener extends IcqListener {

    private String lastSameMessage = Util.BAD_STRING;
    private int fuduNumber;

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

    private String personalLastSameMessage = Util.BAD_STRING;
    private long qq;
    private int personalNumber;

    @EventHandler
    public void checkSingleFudu(EventGroupMessage egm) {
        if (Configuration.config.monitorConfig.maxRepeatNumber >=
                Configuration.config.monitorConfig.minRepeatToBeBanned - 1) {
            return;
        }
        if (!egm.rawMessage.equals(personalLastSameMessage)) {
            personalLastSameMessage = egm.rawMessage;
            qq = egm.senderId;
            personalNumber = 1;
            return;
        }

        if (egm.senderId != qq) {
            qq = egm.senderId;
            personalNumber = 1;
            return;
        }

        personalNumber++;
        if (personalNumber > Configuration.config.monitorConfig.maxRepeatNumber) {
            egm.ban(Configuration.config.monitorConfig.jinyanTime);
            egm.respond(Configuration.config.monitorConfig.warningWords);
            resetSingleFudu();
        }
    }

    private void resetSingleFudu() {
        personalLastSameMessage = Util.BAD_STRING;
        qq = 0L;
        personalNumber = 0;
    }
}
