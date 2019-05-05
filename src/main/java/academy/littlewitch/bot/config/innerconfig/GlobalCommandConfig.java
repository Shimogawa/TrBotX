package academy.littlewitch.bot.config.innerconfig;

import cc.moecraft.icq.command.CommandProperties;
import com.google.gson.annotations.SerializedName;

public class GlobalCommandConfig {
    @SerializedName("全局指令前缀")
    public String[] commandPrefix = { "bot -" };

    @SerializedName("指令格式不正确提示")
    public String malformedErrorMessage = "指令格式不正确";

    @SerializedName("投票禁言指令设定")
    public GlobalCommand startVoteJinyanCommand = new GlobalCommand(
            new CommandProperties("jy"), "投票禁言"
    );

    @SerializedName("招募联机指令设定")
    public GlobalCommand hireMultiCommand = new GlobalCommand(
            new CommandProperties("hire"),
            "招募联机",
            true
    );

    @SerializedName("天气查询指令设定")
    public GlobalCommand weatherInfoCommand = new GlobalCommand(
            new CommandProperties("weather"),
            "天气查询",
            true
    );

//    @SerializedName("投票禁言指令设定")
//    public GlobalCommand voteJinyanCommand = new GlobalCommand(
//            new CommandProperties("vote"),
//            "投票禁言"
//    );

//    @SerializedName("加群验证指令设定")
//    public GlobalCommand authCommand = new GlobalCommand(
//            new CommandProperties("auth"), "加群验证"
//    );

    @SerializedName("版本指令设定")
    public GlobalCommand versionCommand = new GlobalCommand(
            new CommandProperties("v"),
            "查看版本"
    );

    @SerializedName("帮助指令设定")
    public GlobalCommand helpCommand = new GlobalCommand(
            new CommandProperties("h", "help"),
            "查看帮助"
    );

    @SerializedName("数学公式指令设定")
    public GlobalCommand mathCommand = new GlobalCommand(
            new CommandProperties("math"),
            "数学公式",
            false
    );
}
