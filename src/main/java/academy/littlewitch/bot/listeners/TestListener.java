package academy.littlewitch.bot.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;

public class TestListener extends IcqListener {

    @EventHandler
    public void repeat(EventGroupMessage msg) {
        if (msg.groupId != 610531770)
            return;
        System.out.println(msg.rawMessage + " " + msg.time);
        System.out.println(msg.getGroupUser(msg.senderId).getInfo());
    }
}
