package academy.littlewitch.bot.config.innerconfig;

public class voteForJinyan {
    public String voteWords = "%1$s 发起了对于 %2$s 投票禁言。";

    public String bannedWords = "%1$s 满 %2$s 票，已被禁言 %3$s 分钟。";

    public String getVoteWords = "%1$s 现在有 %2$s 票";

    public int validTime = 300;

    public int jinyanTime = 600;

    public int votesToGetBanned = 10;
}
