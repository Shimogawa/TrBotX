package academy.littlewitch.bot.listeners.qungui;

import cc.moecraft.icq.event.events.message.EventGroupMessage;

public class SingleFuduRecord {

    private String lastMessage;

    private int fuduTime;

    public SingleFuduRecord(EventGroupMessage egm) {
        this.lastMessage = egm.rawMessage;
        this.fuduTime = 1;
    }

//    public boolean shouldJinyan(EventGroupMessage egm) {
//
//    }
}
