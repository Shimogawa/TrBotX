package academy.littlewitch.bot.config.innerconfig;

import cc.moecraft.icq.command.CommandProperties;
import com.google.gson.annotations.SerializedName;

public class GlobalCommandConfig {
    @SerializedName("全局指令前缀")
    public String[] commandPrefix = { "bot -" };

    @SerializedName("指令格式不正确提示")
    public String malformedErrorMessage = "指令格式不正确";

    @SerializedName("开始禁言指令设定")
    public GlobalCommand startVoteJinyanCommand = new GlobalCommand(
            new CommandProperties("jy"), "禁言"
    );

    @SerializedName("投票禁言指令设定")
    public GlobalCommand voteJinyanCommand = new GlobalCommand(
            new CommandProperties("vote"),
            "投票禁言"
    );

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

    @SerializedName("计算器指令设定")
    public GlobalCommand calculatorCommand = new GlobalCommand(
            new CommandProperties("calc"),
            "计算器",
            false
    );
}
