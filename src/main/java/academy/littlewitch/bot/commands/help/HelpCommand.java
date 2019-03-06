package academy.littlewitch.bot.commands.help;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.config.innerconfig.GlobalCommand;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class HelpCommand implements EverywhereCommand {
    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Field f : Configuration.config.globalCommandConfig.getClass().getDeclaredFields()) {
                if (f.getType() == GlobalCommand.class) {
                    sb.append(f.get(Configuration.config.globalCommandConfig));
                    sb.append('\n');
                }
            }
        } catch (IllegalAccessException e) {
            return null;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.helpCommand.commandProperties;
    }
}
