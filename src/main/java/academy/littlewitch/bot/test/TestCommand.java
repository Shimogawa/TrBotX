package academy.littlewitch.bot.test;

import academy.littlewitch.utils.Util;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class TestCommand implements EverywhereCommand {
    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        return Util.splitAllWhitespace(arrayList).toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("test");
    }
}
