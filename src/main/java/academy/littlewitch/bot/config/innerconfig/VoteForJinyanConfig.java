package academy.littlewitch.bot.config.innerconfig;

import com.google.gson.annotations.SerializedName;

public class VoteForJinyanConfig {
    @SerializedName("发起投票消息")
    public String voteWords = "%1$s 发起了对于 %2$s 投票禁言。";

    @SerializedName("被禁言消息")
    public String bannedWords = "%1$s 满 %2$s 票，已被禁言 %3$s 分钟。";

    @SerializedName("目前得票消息")
    public String getVoteWords = "%1$s 现在有 %2$s 票";

    @SerializedName("已开始投票消息")
    public String voteAlreadyStartedWords = "之前已经开始了对于该用户的投票。若需要投票，请使用投票指令。";

    @SerializedName("投票格式错误消息")
    public String voteFormatIncorrectWords = "投票禁言请使用qq号";

    @SerializedName("已投票消息")
    public String alreadyVotedWords = "你已经投过票了";

    @SerializedName("发起投票格式错误消息")
    public String notValidStartVoteWords = "请使用at或qq号开启对那个人的投票";

    @SerializedName("已移除投票消息")
    public String removedVotingWords = "已移除你发起的所有投票";

    @SerializedName("未发起任何投票消息")
    public String didNotInitiatedAnyVoteWords = "你没有发起任何投票";

    @SerializedName("显示正在进行的投票格式")
    public String waitingBanListFormat = "%1$s -- %2$s";

    @SerializedName("查无此人消息")
    public String noSuchGuyWords = "查无此人";

    @SerializedName("显示正在进行的投票指令")
    public String checkBanningListCommand = "c";

    @SerializedName("取消投票指令")
    public String cancelVotingsCommand = "stop";

    @SerializedName("投票有效时间")
    public int validTime = 300;

    @SerializedName("禁言时长")
    public int jinyanTime = 600;

    @SerializedName("禁言所需票数")
    public int votesToGetBanned = 10;
}
