package academy.littlewitch.bot.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;

public class TestListener extends IcqListener {
    private long lastTimeFudu = 0L;

    @EventHandler
    public void repeat(EventGroupMessage msg) {
        if (msg.groupId != 165462193)
            return;
        System.out.println("Received: " + msg.rawMessage + " " + msg.time);
        System.out.println(msg.getGroupUser(msg.senderId).getInfo());
//        if (msg.validTime - lastTimeFudu < 5000)
//            return;
//        lastTimeFudu = msg.validTime;
//        msg.respond(msg.rawMessage + "，，，");
    }

    @EventHandler
    public void privateRepeat(EventPrivateMessage msg) {
        System.out.println("Received from " + msg.getSender().getInfo().getNickname() + ": " + msg.rawMessage);
        msg.respond(msg.rawMessage + "，，，");
    }
}
