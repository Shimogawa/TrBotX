package academy.littlewitch.bot.commands.help;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.config.innerconfig.GlobalCommand;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class HelpCommand implements EverywhereCommand {
    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.globalCommandConfig.helpCommand.enabled)
            return null;
        StringBuilder sb = new StringBuilder();
        try {
            for (Field f : Configuration.config.globalCommandConfig.getClass().getDeclaredFields()) {
                if (f.getType() == GlobalCommand.class) {
                    GlobalCommand gc = (GlobalCommand)f.get(Configuration.config.globalCommandConfig);
                    if (!gc.enabled)
                        continue;
                    sb.append(f.get(Configuration.config.globalCommandConfig));
                    sb.append('\n');
                }
            }
            if (Configuration.config.superManagers.contains(user.getId())) {
                sb.append("-----超管指令-----");
                for (Field f : Configuration.config.superCommandConfig.getClass().getDeclaredFields()) {
                    if (f.getType() == GlobalCommand.class) {
                        GlobalCommand gc = (GlobalCommand)f.get(Configuration.config.superCommandConfig);
                        if (!gc.enabled)
                            continue;
                        sb.append(f.get(Configuration.config.superCommandConfig));
                        sb.append('\n');
                    }
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
