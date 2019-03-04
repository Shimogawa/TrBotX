package academy.littlewitch.bot.commands.jinyan;

import academy.littlewitch.bot.commands.GeneralUtilCommands;
import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

public class VoteJinyanCommand implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage,
                               GroupUser groupUser,
                               Group group, String s,
                               ArrayList<String> arrayList) {
        long qq;
        if (arrayList.size() != 1) {
            return Configuration.config.globalCommandConfig.voteJinyanCommand.alt[0];
        }
        try {
            qq = Long.parseLong(arrayList.get(0));
        } catch (NumberFormatException e) {
            return Configuration.config.globalCommandConfig.voteJinyanCommand.alt[0];
        }
        StartVoteJinyanCommand.checkList(eventGroupMessage);
        if (!StartVoteJinyanCommand.waitBanList.containsKey(qq))
            return "查无此人";

        VoteStatus status = StartVoteJinyanCommand.waitBanList.get(qq);
        status.addOneVote();
        if (status.getVotes() >= Configuration.config.voteForJinyanConfig.votesToGetBanned) {
            StartVoteJinyanCommand.waitBanList.remove(qq);
            GeneralUtilCommands.commandBan(eventGroupMessage, group.id, qq,
                    Configuration.config.voteForJinyanConfig.jinyanTime);
            return String.format(Configuration.config.voteForJinyanConfig.bannedWords,
                    new ComponentAt(qq),
                    Configuration.config.voteForJinyanConfig.votesToGetBanned,
                    Configuration.config.voteForJinyanConfig.jinyanTime / 60);
        }
        return String.format(Configuration.config.voteForJinyanConfig.getVoteWords,
                qq, status.getVotes());
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.voteJinyanCommand.commandProperties;
    }
}
