package academy.littlewitch.bot.config.innerconfig.monitorconfig;

import com.google.gson.annotations.SerializedName;

public class MonitorConfig {
    @SerializedName("几秒内进行判断")
    public int cycleTime = 5;

    @SerializedName("几秒内最大发言次数")
    public int maxText = 3;

    @SerializedName("最大长度")
    public int maxLength = 300;

    @SerializedName("最大行数")
    public int maxLines = 15;

    @SerializedName("禁言时长")
    public int jinyanTime = 600;

    @SerializedName("最大单人重复发言次数")
    public int maxSingleRepeatNumber = 3;

    @SerializedName("单人重复发言判断时间")
    public int checkSingleRepeatCycle = 60;

    @SerializedName("第几个复读开始禁言")
    public int minRepeatToBeBanned = 4;

    @SerializedName("允许撤回")
    public boolean allowedToRecall = true;

    @SerializedName("警告")
    public String warningWords = "刷屏警告！";
}
