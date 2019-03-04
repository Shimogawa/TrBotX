package academy.littlewitch.bot.commands;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.HttpApiBase;

import java.util.HashMap;

public class GeneralUtilCommands {

    public static void commandBan(EventGroupMessage event,
                                  long groupId,
                                  long userId,
                                  int duration) {
        event.getHttpApi().send(HttpApiBase.SET_GROUP_BAN,
                "group_id", groupId,
                "user_id", userId,
                "duration", duration
        );
    }
}
