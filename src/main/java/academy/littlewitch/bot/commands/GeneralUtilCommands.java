package academy.littlewitch.bot.commands;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.HttpApiBase;
import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.sender.returndata.ReturnStatus;

public class GeneralUtilCommands {

    public static boolean commandBan(EventGroupMessage event,
                                  long groupId,
                                  long userId,
                                  int duration) {
        RawReturnData data = event.getHttpApi().setGroupBan(groupId, userId, duration);
        return data.status == ReturnStatus.ok;
    }

    public static void commandRecall(EventGroupMessage event) {
        event.getHttpApi().send(HttpApiBase.DELETE_MSG,
                "message_id", event.messageId);
    }
}
