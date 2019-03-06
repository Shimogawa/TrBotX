package academy.littlewitch.bot.commands.math;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.utils.Util;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

public class MathCommand implements EverywhereCommand {

    private static final String IMG_URL = "https://latex.codecogs.com/gif.latex?\\dpi{150}%20";

    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.globalCommandConfig.mathCommand.enabled)
            return null;
        if (arrayList.size() == 0)
            return Configuration.config.globalCommandConfig.malformedErrorMessage;
        String math = String.join("%20", arrayList);
        math = Util.toCQValidString(IMG_URL + math);
        System.out.println(math);
        return new ComponentImage(math).toString();
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.mathCommand.commandProperties;
    }
}
