package academy.littlewitch.bot.config.innerconfig;

import cc.moecraft.icq.command.CommandProperties;

public class GlobalCommandConfig {
    public String[] commandPrefix = { "bot -" };

    public String malformedErrorMessage = "指令格式不正确";

    public GlobalCommand startVoteJinyanCommand = new GlobalCommand(
            new CommandProperties("jy"), "禁言"
    );

    public GlobalCommand voteJinyanCommand = new GlobalCommand(
            new CommandProperties("vote"),
            "投票禁言"
    );

    public GlobalCommand versionCommand = new GlobalCommand(
            new CommandProperties("v"),
            "查看版本"
    );

    public GlobalCommand helpCommand = new GlobalCommand(
            new CommandProperties("h", "help"),
            "查看帮助"
    );
}
