package academy.littlewitch.bot.commands.auth;

//import academy.littlewitch.bot.config.Configuration;
//import academy.littlewitch.steam.auth.Auth;
//import cc.moecraft.icq.command.CommandProperties;
//import cc.moecraft.icq.command.interfaces.GroupCommand;
//import cc.moecraft.icq.event.events.message.EventGroupMessage;
//import cc.moecraft.icq.sender.message.components.ComponentAt;
//import cc.moecraft.icq.user.Group;
//import cc.moecraft.icq.user.GroupUser;
//
//import java.util.ArrayList;

@Deprecated public class AuthCommand {}
//public class AuthCommand implements GroupCommand {
//
//    @Override
//    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
//        if (!Configuration.config.globalCommandConfig.authCommand.enabled)
//            return null;
//
//        if (group.id != Configuration.config.authConfig.authGroupID) {
//            return null;
//        }
//
//        if (arrayList.size() != 1) {
//            return Configuration.config.globalCommandConfig.malformedErrorMessage;
//        }
//
//        String steamId = arrayList.get(0);
//        int hours = Auth.getGameHoursTerraria(steamId);
//
//        if (hours >= Configuration.config.authConfig.minHours) {
//            return String.format(Configuration.config.authConfig.successWords,
//                    new ComponentAt(eventGroupMessage.senderId));
//        }
//
//        return String.format(Configuration.config.authConfig.failWords,
//                new ComponentAt(eventGroupMessage.senderId));
//    }
//
//    @Override
//    public CommandProperties properties() {
//        return Configuration.config.globalCommandConfig.authCommand.commandProperties;
//    }
//}
