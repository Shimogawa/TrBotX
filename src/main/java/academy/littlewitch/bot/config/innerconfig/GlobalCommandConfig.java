package academy.littlewitch.bot.config.innerconfig;

import cc.moecraft.icq.command.CommandProperties;

public class GlobalCommandConfig {
    public String[] commandPrefix = { "bot -" };

    public GlobalCommand startVoteJinyanCommand = new GlobalCommand(
            new CommandProperties("jy"), "禁言",
            "已经开始对于该用户的投票。若需要投票，请使用投票指令。", "指令格式不正确"
    );
    public GlobalCommand voteJinyanCommand = new GlobalCommand(
            new CommandProperties("vote"),
            "投票禁言",
            "投票禁言请使用qq号"
    );
}
