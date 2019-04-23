package academy.littlewitch.bot.config.innerconfig;

import com.google.gson.annotations.SerializedName;

public class HireMPCommandConfig {

    @SerializedName("公告格式")
    public String hireFormat = "%1$s(%2$s)发起了联机招募！\n" +
            "(点击此链接快捷私聊:tencent://Message/?uin=%2$s）\n" +
            "#联机描述：%3$s\n" +
            "#招募人数：%4$d人\n" +
            "#模组列表：%5$s\n" +
            "#游玩时段：%6$s";

    @SerializedName("发送次数")
    public int totalSendTime = 2;

    @SerializedName("发起间隔")
    public long sendInterval = 1800;

    @SerializedName("使用时限（秒）")
    public long timelimit = 172800;

    @SerializedName("滥用指令消息")
    public String exceedUsageLimitWords = "严禁滥用禁言指令。每%1$s小时只能使用1次";

    @SerializedName("发起成功消息")
    public String succeed = "发起成功";
}
