package academy.littlewitch.bot.config.innerconfig;

import cc.moecraft.icq.command.CommandProperties;
import com.google.gson.annotations.SerializedName;

public class GlobalCommand {
    @SerializedName("是否开启")
    public boolean enabled;

    @SerializedName("指令属性")
    public CommandProperties commandProperties;

    @SerializedName("帮助文档")
    public String helpDocument;

    public GlobalCommand(CommandProperties commandProperties, String helpDocument) {
        this(commandProperties, helpDocument, true);
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
