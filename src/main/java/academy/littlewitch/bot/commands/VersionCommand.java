package academy.littlewitch.bot.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class VersionCommand implements EverywhereCommand {
    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        return "我是rebuild的老婆v0.1";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("v");
    }
}
