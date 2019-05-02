package academy.littlewitch.bot.commands.supercommand;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class PrivateSendCommand implements PrivateCommand {
    @Override
    public String privateMessage(EventPrivateMessage eventPrivateMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.superCommandConfig.sendPrivateCommand.enabled)
            return null;
        String[] raw = eventPrivateMessage.rawMessage.split("\n", 3);
        if (raw.length < 3)
            return Configuration.config.globalCommandConfig.malformedErrorMessage;
        String[] qqs = raw[1].split("[,|\\s]");
        String msg = raw[2];
        new Thread(() -> {
            for (int i = 0; i < qqs.length; i++) {
                long qq;
                try {
                    qq = Long.parseLong(qqs[i]);
                } catch (NumberFormatException e) {
                    eventPrivateMessage.respond((i + 1) + " / " + qqs.length + " [错误格式：" + qqs[i] + "]");
                    continue;
                }
                eventPrivateMessage.getHttpApi().sendPrivateMsg(qq, msg);
                eventPrivateMessage.respond((i + 1) + " / " + qqs.length + " [" + qqs[i] + "]");
            }
        }).start();
        return "正在发送...";
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.superCommandConfig.sendPrivateCommand.commandProperties;
    }
}
