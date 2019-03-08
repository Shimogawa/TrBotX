package academy.littlewitch.bot.listeners.qungui;

import academy.littlewitch.bot.commands.GeneralUtilCommands;
import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.utils.Pair;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

import java.util.Hashtable;

public class RepeatControlListener extends IcqListener {

    /**
     * Elem1: the last same message
     * Elem2: the number of times of fudu
     */
    private static Hashtable<Long, Pair<String, Integer>> groupFuduTable = new Hashtable<>();

    @EventHandler
    public void checkGroupFudu(EventGroupMessage egm) {
        if (Configuration.config.debugMode && egm.groupId != 610531770) {
            return;
        }

        if (egm.getGroupUser(egm.senderId).getInfo() == null || egm.senderId == egm.getBotAccount().getId())
            return;

        if (!groupFuduTable.containsKey(egm.groupId)) {
            groupFuduTable.put(egm.groupId, new Pair<>(egm.rawMessage, 0));
        }

        Pair<String, Integer> pp = groupFuduTable.get(egm.groupId);

        if (!egm.rawMessage.equals(pp.elem1)) {
            pp.elem1 = egm.rawMessage;
            pp.elem2 = 1;
            return;
        }

        pp.elem2++;
        if (pp.elem2 >= Configuration.config.monitorConfig.minRepeatToBeBanned) {
            if (personalRecords.containsKey(egm.groupId)) {
                FayanRecord fr = personalRecords.get(egm.groupId).get(egm.senderId);
                if (fr != null) {
                    if (fr.isJinyaned())
                        return;
                    else
                        fr.setJinyaned(true);
                }
            }
            GeneralUtilCommands.commandBan(egm,
                    egm.groupId, egm.senderId, Configuration.config.monitorConfig.jinyanTime);
            egm.respond(Configuration.config.monitorConfig.warningWords);
            System.out.println("2222222");
        }
    }


    public static Hashtable<Long, Hashtable<Long, FayanRecord>> personalRecords = new Hashtable<>();

    @EventHandler
    public void checkSingleFudu(EventGroupMessage egm) {
        if (Configuration.config.debugMode && egm.groupId != 610531770) {
            return;
        }

        if (!personalRecords.containsKey(egm.groupId)) {
            personalRecords.put(egm.groupId, new Hashtable<>());
        }

        Hashtable<Long, FayanRecord> records = personalRecords.get(egm.groupId);

        if (!records.containsKey(egm.senderId)) {
            records.put(egm.senderId, new FayanRecord());
        }

        FayanRecord theRecord = records.get(egm.senderId);

        if (theRecord.repeatFayanCheck(egm) && !theRecord.isJinyaned()) {
            GeneralUtilCommands.commandBan(egm,
                    egm.groupId, egm.senderId, Configuration.config.monitorConfig.jinyanTime);
            egm.respond(Configuration.config.monitorConfig.warningWords);
            System.out.println("1111111");
        }
    }
}
