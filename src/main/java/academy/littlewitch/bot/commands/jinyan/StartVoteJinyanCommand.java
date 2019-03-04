package academy.littlewitch.bot.commands.jinyan;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class StartVoteJinyanCommand implements GroupCommand {

    public static Hashtable<Long, VoteStatus> waitBanList = new Hashtable<>();

    @Override
    public String groupMessage(EventGroupMessage event,
                               GroupUser sender,
                               Group group,
                               String command,
                               ArrayList<String> args) {
        if (args.size() != 1)
            return Configuration.config.globalCommandConfig.startVoteJinyanCommand.alt[1];
        String ater = args.get(0);
        if (!ater.startsWith("[CQ:at,qq="))
            return "查无此人";
        checkList(event);
        ater = ater.substring(10, ater.length() - 1);
        long banner;
        try {
            banner = Long.parseLong(ater);
        } catch (NumberFormatException e) {
            return null;
        }

        if (!waitBanList.containsKey(banner)) {
            return Configuration.config.globalCommandConfig.startVoteJinyanCommand.alt[0];
        }

        String info = String.format(Configuration.config.voteForJinyanConfig.voteWords,
                new ComponentAt(sender.id), args.get(0));

        waitBanList.put(banner, new VoteStatus(event.time));
        return info;
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.startVoteJinyanCommand.commandProperties;
    }

    public static void checkList(EventGroupMessage event) {
        waitBanList.entrySet().removeIf(kvp ->
                event.time - kvp.getValue().getVoteStartTime() >
                        Configuration.config.voteForJinyanConfig.validTime);
    }
}
