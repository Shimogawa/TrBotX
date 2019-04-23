package academy.littlewitch.bot.commands.groupcommands;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.utils.Util;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;
import java.util.Hashtable;

public class HireMultiplayerCommand implements PrivateCommand {

    public static Hashtable<Long, HireInfo> hireBoard = new Hashtable<>();

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.hireMultiCommand.commandProperties;
    }

    @Override
    public String privateMessage(EventPrivateMessage eventPrivateMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.globalCommandConfig.hireMultiCommand.enabled) {
            return null;
        }
        if (!canHire(eventPrivateMessage)) {
            return String.format(Configuration.config.hireConfig.exceedUsageLimitWords,
                    Configuration.config.hireConfig.timelimit / 3600);
        }

        ArrayList<String> cmds = Util.splitAllWhitespace(arrayList);
        if (cmds.size() != 4) {
            return Configuration.config.globalCommandConfig.malformedErrorMessage;
        }
        try {
            String desc = cmds.get(0);
            desc = getInfos(desc, "联机描述：");
            String pn = cmds.get(1);
            pn = getInfos(pn, "招募人数：");
            int playerNumber = Integer.parseInt(pn);
            String modList = cmds.get(2);
            modList = getInfos(modList, "模组列表：");
            String playTime = cmds.get(3);
            playTime = getInfos(playTime, "游玩时段：");
            HireInfo info = new HireInfo(eventPrivateMessage, eventPrivateMessage.time,
                    desc, playerNumber, modList, playTime);
            hireBoard.put(user.getId(), info);
        } catch (IndexOutOfBoundsException e) {
            return Configuration.config.globalCommandConfig.malformedErrorMessage;
        } catch (NumberFormatException e2) {
            return Configuration.config.hireConfig.numberFormatErrorMsg;
        }
        return Configuration.config.hireConfig.succeed;
    }

    private String getInfos(String cmd, String prefix) {
        return cmd.substring(cmd.indexOf(prefix)).substring(prefix.length());
    }

    /**
     *
     * @param epm
     * @return true if user can hire at this time.
     */
    private static boolean canHire(EventPrivateMessage epm) {
        hireBoard.entrySet().removeIf(kvp ->
            !kvp.getValue().isValid &&
                    epm.time - kvp.getValue().startTime > Configuration.config.hireConfig.timelimit
        );
        return !hireBoard.containsKey(epm.getSender().getId());
    }
}
