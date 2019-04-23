package academy.littlewitch.bot.config.innerconfig.monitorconfig;

import academy.littlewitch.bot.config.innerconfig.GlobalCommand;
import cc.moecraft.icq.command.CommandProperties;
import com.google.gson.annotations.SerializedName;

public class SuperCommandConfig {
    @SerializedName("修改指令设定")
    public GlobalCommand changeConfigCommand = new GlobalCommand(
            new CommandProperties("changecfg"), "修改指令设定指令"
    );


}
