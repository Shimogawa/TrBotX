package academy.littlewitch.bot.commands.jinyan;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class StartVoteJinyanCommand implements GroupCommand {

    public static Hashtable<Long, VoteStatus> waitBanList = new Hashtable<>();

    @Override
    public String groupMessage(EventGroupMessage event,
                               GroupUser sender,
                               Group group,
                               String command,
                               ArrayList<String> args) {
        if (!Configuration.config.globalCommandConfig.startVoteJinyanCommand.enabled)
            return null;
        checkList(event);
        if (args.size() < 1) {
            if (!removeFromList(sender.id))
                return Configuration.config.globalCommandConfig.malformedErrorMessage;
            return Configuration.config.voteForJinyanConfig.removedVotingWords;
        }
        String ater = args.get(0);
        if (ater.toLowerCase().equals(Configuration.config.voteForJinyanConfig.checkBanningListCommand)) {
            return getWaitingBanners();
        }
        if (!ater.startsWith("[CQ:at,qq="))
            return Configuration.config.voteForJinyanConfig.notAtToVoteWords;
        ater = ater.substring(10, ater.length() - 1);
        long banner;
        try {
            banner = Long.parseLong(ater);
        } catch (NumberFormatException e) {
            return null;
        }

        if (waitBanList.containsKey(banner)) {
            System.out.println(waitBanList.get(banner).getVoteStartTime());
            return Configuration.config.voteForJinyanConfig.voteAlreadyStartedWords;
        }

        String info = String.format(Locale.CHINA, Configuration.config.voteForJinyanConfig.voteWords,
                new ComponentAt(sender.id).toString(), args.get(0));


        waitBanList.put(banner, new VoteStatus(event.time, sender.id));
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

    public static boolean removeFromList(long initiator) {
        return waitBanList.entrySet().removeIf(kvp ->
                kvp.getValue().getInitiator() == initiator);
    }

    public static String getWaitingBanners() {
        StringBuilder sb = new StringBuilder();
        sb.append("查询禁言投票名单：\nQQ号 - 票数\n");
        for (Map.Entry<Long, VoteStatus> entry : waitBanList.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" - ");
            sb.append(entry.getValue().getVotes());
            sb.append('\n');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
