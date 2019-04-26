package academy.littlewitch.bot.test;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

public class TestListener extends IcqListener {

    @EventHandler
    public void repeat(EventGroupMessage msg) {
        if (msg.groupId != 610531770L)
            return;
        System.out.println(msg.rawMessage + " " + msg.time);
        System.out.println(msg.getGroupUser(msg.senderId).getInfo());
    }
//
//    @EventHandler
//    public void f(EventPrivateMessage epm) {
//        if (epm.senderId != 714026292L)
//            return;
//        System.out.println(epm.rawMessage);
//        epm.respond("[CQ:rich,url=https://www.baidu.com,text=田所浩二向你转账100元 以转入你的余额 QQ转账]");
//    }
}
