package academy.littlewitch.bot.commands.jinyan;

import academy.littlewitch.bot.commands.GeneralUtilCommands;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.utils.Pair;
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

    public static Hashtable<Long, Pair<Integer, Long>> usageList = new Hashtable<>();

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
            return Configuration.config.globalCommandConfig.malformedErrorMessage;
        }
        String ater = args.get(0).toLowerCase();

        // Check banning list
        if (ater.equals(Configuration.config.voteForJinyanConfig.checkBanningListCommand.toLowerCase())) {
            return getWaitingBanners();
        }

        // Cancel voting
        if (ater.equals(Configuration.config.voteForJinyanConfig.cancelVotingsCommand.toLowerCase())) {
            if (!removeFromList(sender.getId())) {
                return Configuration.config.voteForJinyanConfig.didNotInitiatedAnyVoteWords;
            }
            return Configuration.config.voteForJinyanConfig.removedVotingWords;
        }

        // Check used times and cooldown
        if (!checkUsageTimes(event)) {
            return String.format(Configuration.config.voteForJinyanConfig.exceedUsageLimitWords,
                    Configuration.config.voteForJinyanConfig.cooldown / 3600,
                    Configuration.config.voteForJinyanConfig.maxUseLimit);
        }

        long banner;
        if (!ater.startsWith("[cq:at,qq="))
        {
            try {
                banner = Long.parseLong(ater);
            } catch (NumberFormatException e) {
                return Configuration.config.voteForJinyanConfig.notValidStartVoteWords;
            }

        } else {
            ater = ater.substring(10, ater.length() - 1);
            try {
                banner = Long.parseLong(ater);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        GroupUser gu = event.getGroupUser(banner);
        if (gu.getInfo() == null || gu.isAdmin())
            return Configuration.config.voteForJinyanConfig.noSuchGuyWords;

        // Vote for that guy
        if (waitBanList.containsKey(banner)) {
            checkList(event);

            VoteStatus status = waitBanList.get(banner);
            if (!status.addVote(event.senderId)) {
                return Configuration.config.voteForJinyanConfig.alreadyVotedWords;
            }
            if (status.getVotes() >= Configuration.config.voteForJinyanConfig.votesToGetBanned) {
                waitBanList.remove(banner);
                GeneralUtilCommands.commandBan(event, group.getId(), banner,
                        Configuration.config.voteForJinyanConfig.jinyanTime);
                return String.format(Configuration.config.voteForJinyanConfig.bannedWords,
                        new ComponentAt(banner),
                        Configuration.config.voteForJinyanConfig.votesToGetBanned,
                        Configuration.config.voteForJinyanConfig.jinyanTime / 60);
            }
            return String.format(Configuration.config.voteForJinyanConfig.getVoteWords,
                    banner, status.getVotes(),
                    (Configuration.config.voteForJinyanConfig.validTime - (event.time - status.getVoteStartTime())) / 60);
        }

        // Start the vote
        String info = String.format(Locale.CHINA, Configuration.config.voteForJinyanConfig.voteWords,
                new ComponentAt(sender.getId()).toString(), new ComponentAt(banner));

        waitBanList.put(banner, new VoteStatus(event.time, sender.getId()));
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
        if (waitBanList.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("查询禁言投票名单：\n");
        sb.append(String.format(Configuration.config.voteForJinyanConfig.waitingBanListFormat, "QQ号", "票数"));
        for (Map.Entry<Long, VoteStatus> entry : waitBanList.entrySet()) {
            String f = String.format(Configuration.config.voteForJinyanConfig.waitingBanListFormat,
                    entry.getKey(), entry.getValue().getVotes());
            sb.append(f);
            sb.append('\n');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     *
     * @param egm
     * @return true if can continue to use, false otherwise.
     */
    public static boolean checkUsageTimes(EventGroupMessage egm) {
        if (usageList.containsKey(egm.senderId)) {
            Pair<Integer, Long> p = usageList.get(egm.senderId);
            p.elem1++;
            if (p.elem1 > Configuration.config.voteForJinyanConfig.maxUseLimit) {
                if (egm.time - p.elem2 > Configuration.config.voteForJinyanConfig.cooldown) {
                    p.elem2 = egm.time;
                    p.elem1 = 1;
                    return true;
                }
                return false;
            }
            return true;
        }
        usageList.put(egm.senderId, new Pair<>(1, egm.time));
        return true;
    }
}
