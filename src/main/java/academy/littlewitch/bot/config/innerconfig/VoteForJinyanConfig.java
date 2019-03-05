package academy.littlewitch.bot.config.innerconfig;

public class VoteForJinyanConfig {
    public String voteWords = "%1$s 发起了对于 %2$s 投票禁言。";

    public String bannedWords = "%1$s 满 %2$s 票，已被禁言 %3$s 分钟。";

    public String getVoteWords = "%1$s 现在有 %2$s 票";

    public String voteAlreadyStartedWords = "之前已经开始了对于该用户的投票。若需要投票，请使用投票指令。";

    public String voteFormatIncorrectWords = "投票禁言请使用qq号";

    public String alreadyVotedWords = "你已经投过票了";

    public String notAtToVoteWords = "请使用at开启对那个人的投票";

    public String removedVotingWords = "已移除你发起的所有投票";

    public String noSuchGuyWords = "查无此人";

    public String checkBanningListCommand = "c";

    public int validTime = 300;

    public int jinyanTime = 600;

    public int votesToGetBanned = 10;
}
