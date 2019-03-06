package academy.littlewitch.bot.listeners.qungui;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.Hashtable;

public class ShuapinControlListener extends IcqListener {

    private Hashtable<Long, FayanRecord> fayanRecords = new Hashtable<>();

    @EventHandler
    public void checkLength(EventGroupMessage egm) {
        if (egm.rawMessage.length() <= Configuration.config.monitorConfig.maxLength &&
            StringUtils.countMatches(egm.rawMessage, "\n") <= Configuration.config.monitorConfig.maxLines) {
            return;
        }
        egm.ban(Configuration.config.monitorConfig.jinyanTime);
        if (Configuration.config.monitorConfig.allowedToRecall) {
            egm.delete();
        }
        egm.respond(Configuration.config.monitorConfig.warningWords);
    }

    @EventHandler
    public void checkShuapin(EventGroupMessage egm) {
        if (!fayanRecords.contains(egm.senderId)) {
            fayanRecords.put(egm.senderId, new FayanRecord(egm.senderId, egm.time));
            return;
        }
        FayanRecord record = fayanRecords.get(egm.senderId);
        if (record.fayanCheck(egm.time)) {
            egm.ban(Configuration.config.monitorConfig.jinyanTime);
            egm.respond(Configuration.config.monitorConfig.warningWords);
        }
    }
}
