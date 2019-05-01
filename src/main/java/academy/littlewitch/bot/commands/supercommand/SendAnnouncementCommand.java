package academy.littlewitch.bot.commands.supercommand;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.net.HttpUtils;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class SendAnnouncementCommand implements PrivateCommand {
    @Override
    public String privateMessage(EventPrivateMessage eventPrivateMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.superCommandConfig.sendAnnouncementCommand.enabled)
            return null;
        if (!Configuration.config.superManagers.contains(user.getId()))
            return null;
        if (arrayList.size() == 0)
            return null;
        String[] all = eventPrivateMessage.rawMessage.split("\n", 3);
        for (String sss : all) {
            if (StringUtils.isEmpty(sss)) {
                return "请使用一（1）个回车分隔指令与标题与内容";
            }
        }
        for (long id : Configuration.config.trGroups) {
            HttpUtils.sendThroughApi("_send_group_notice", "group_id", id,
                    "title", all[1], "content", all[2]);
        }
        return "发送成功";
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.superCommandConfig.sendAnnouncementCommand.commandProperties;
    }
}
