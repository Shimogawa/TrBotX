package academy.littlewitch.bot.config.innerconfig;

import cc.moecraft.icq.command.CommandProperties;

public class GlobalCommand {
    public boolean enabled;

    public CommandProperties commandProperties;

    public String helpDocument;

    public GlobalCommand(CommandProperties commandProperties, String helpDocument) {
        this.enabled = true;
        this.commandProperties = commandProperties;
        this.helpDocument = helpDocument;
    }

    public GlobalCommand(CommandProperties commandProperties, String helpDocument, boolean enabled) {
        this.enabled = enabled;
        this.commandProperties = commandProperties;
        this.helpDocument = helpDocument;
    }

    @Override
    public String toString() {
        return commandProperties.name + " -- " + helpDocument;
    }
}
