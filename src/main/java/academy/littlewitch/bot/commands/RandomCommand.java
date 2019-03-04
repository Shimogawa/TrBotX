package academy.littlewitch.bot.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;
import java.util.Random;

public class RandomCommand implements GroupCommand {

    private Random random = new Random();

    @Override
    public CommandProperties properties() {
        return new CommandProperties("d");
    }

    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        if (arrayList.size() != 1) return null;
        int i = 0;
        try {
            i = Integer.parseInt(arrayList.get(0));
        } catch (NumberFormatException e) {
            return null;
        }

        System.out.println(groupUser.getInfo());
        return null;
    }
}
