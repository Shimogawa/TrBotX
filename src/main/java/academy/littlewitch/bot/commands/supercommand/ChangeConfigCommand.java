package academy.littlewitch.bot.commands.supercommand;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class ChangeConfigCommand implements PrivateCommand {

    @Override
    public String privateMessage(EventPrivateMessage eventPrivateMessage, User user, String s, ArrayList<String> arrayList) {
        return "功能暂未完成。";
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.superCommandConfig.changeConfigCommand.commandProperties;
    }
}
