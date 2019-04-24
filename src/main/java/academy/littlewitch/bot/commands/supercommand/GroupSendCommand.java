package academy.littlewitch.bot.commands.supercommand;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class GroupSendCommand implements PrivateCommand {
    @Override
    public String privateMessage(EventPrivateMessage eventPrivateMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.superCommandConfig.groupSendCommand.enabled)
            return null;
        if (!Configuration.config.superManagers.contains(user.getId()))
            return null;
        if (arrayList.size() == 0)
            return null;
        int idx = eventPrivateMessage.rawMessage.indexOf('\n');
        if (idx == -1) {
            return "请使用回车分隔指令与内容";
        }
        String msg = eventPrivateMessage.rawMessage.substring(idx + 1);
        msg += "\n\n——发送者：" + user.getInfo().getNickname() + "（" + user.getId() + "）";
        for (long id : Configuration.config.trGroups) {
            eventPrivateMessage.getHttpApi().sendGroupMsg(id, msg);
        }
        return "发送成功";
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.superCommandConfig.groupSendCommand.commandProperties;
    }
}
