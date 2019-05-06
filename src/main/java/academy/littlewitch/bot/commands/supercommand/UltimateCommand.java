package academy.littlewitch.bot.commands.supercommand;

import academy.littlewitch.bot.TrBotX;
import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import javax.script.ScriptException;
import java.util.ArrayList;

public class UltimateCommand implements EverywhereCommand {

    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.superCommandConfig.ultimateCommand.enabled) {
            return null;
        }
        if (!Configuration.config.superManagers.contains(user.getId()))
            return null;
        if (arrayList.size() == 0)
            return null;
        String m = String.join(" ", arrayList);
        try {
            return TrBotX.getJsEngine().eval(m).toString();
        } catch (ScriptException e) {
            return e.getMessage();
        }
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.superCommandConfig.ultimateCommand.commandProperties;
    }
}
