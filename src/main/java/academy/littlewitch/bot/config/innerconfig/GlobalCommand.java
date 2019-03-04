package academy.littlewitch.bot.config.innerconfig;

import cc.moecraft.icq.command.CommandProperties;

public class GlobalCommand {
    public CommandProperties commandProperties;

    public String helpDocument;

    public GlobalCommand(CommandProperties commandProperties, String helpDocument) {
        this.commandProperties = commandProperties;
        this.helpDocument = helpDocument;
    }
}
