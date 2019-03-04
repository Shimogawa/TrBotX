package academy.littlewitch.bot.commands.version;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class VersionCommand implements EverywhereCommand {

    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        return Configuration.config.versionCommandConfig.version;
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.versionCommand.commandProperties;
    }
}
