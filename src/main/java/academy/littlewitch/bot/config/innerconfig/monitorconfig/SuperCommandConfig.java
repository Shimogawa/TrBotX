package academy.littlewitch.bot.config.innerconfig.monitorconfig;

import academy.littlewitch.bot.config.innerconfig.GlobalCommand;
import cc.moecraft.icq.command.CommandProperties;
import com.google.gson.annotations.SerializedName;

public class SuperCommandConfig {

    @SerializedName("查看服务器状态设定")
    public GlobalCommand monitorServerCommand = new GlobalCommand(
            new CommandProperties("serverstats"), "查看服务器状态"
    );

    @SerializedName("修改指令设定")
    public GlobalCommand changeConfigCommand = new GlobalCommand(
            new CommandProperties("changecfg"), "修改指令设定指令"
    );

    @SerializedName("群发指令设定")
    public GlobalCommand groupSendCommand = new GlobalCommand(
            new CommandProperties("groupsend"), "群发消息"
    );
}
