package academy.littlewitch.bot.commands;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

public class VoteJinyanCommand implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {

        String info = String.format(Configuration.config.voteForJinyanConfig.voteWords,
                groupUser.getInfo().card);
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.commandConfig.voteForJinyan;
    }
}
