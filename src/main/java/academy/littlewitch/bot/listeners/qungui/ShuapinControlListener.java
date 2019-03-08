package academy.littlewitch.bot.listeners.qungui;

import academy.littlewitch.bot.commands.GeneralUtilCommands;
import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.Hashtable;

public class ShuapinControlListener extends IcqListener {

    @EventHandler
    public void checkLength(EventGroupMessage egm) {
        if (Configuration.config.debugMode && egm.groupId != 610531770) {
            return;
        }

        if (egm.rawMessage.length() <= Configuration.config.monitorConfig.maxLength &&
            StringUtils.countMatches(egm.rawMessage, "\n") <= Configuration.config.monitorConfig.maxLines) {
            return;
        }
        egm.ban(Configuration.config.monitorConfig.jinyanTime);
        if (Configuration.config.monitorConfig.allowedToRecall) {
            GeneralUtilCommands.commandRecall(egm);
        }
        egm.respond(Configuration.config.monitorConfig.warningWords);
    }

    @EventHandler
    public void checkShuapin(EventGroupMessage egm) {
        if (Configuration.config.debugMode && egm.groupId != 610531770) {
            return;
        }

        if (!RepeatControlListener.personalRecords.containsKey(egm.groupId)) {
            RepeatControlListener.personalRecords.put(egm.groupId, new Hashtable<>());
        }

        Hashtable<Long, FayanRecord> records = RepeatControlListener.personalRecords.get(egm.groupId);

        if (!records.containsKey(egm.senderId)) {
            records.put(egm.senderId, new FayanRecord());
        }

        FayanRecord theRecord = records.get(egm.senderId);

        if (theRecord.fayanShuapinCheck(egm.time) && !theRecord.isJinyaned()) {
            theRecord.setJinyaned(true);
            GeneralUtilCommands.commandBan(egm,
                    egm.groupId, egm.senderId, Configuration.config.monitorConfig.jinyanTime);
            egm.respond(Configuration.config.monitorConfig.warningWords);
            System.out.println("3333333");
        }
    }
}
