package academy.littlewitch.bot.config.innerconfig.monitorconfig;

import com.google.gson.annotations.SerializedName;

@Deprecated
public class AuthConfig {

    @SerializedName("审核群群号")
    public long authGroupID = 0L;

    @SerializedName("最低游戏时长小时")
    public int minHours = 5;

    @SerializedName("目前开放群组")
    public long openGroup = 0L;

    @SerializedName("通过验证消息")
    public String successWords = "%1$s通过验证！";

    @SerializedName("验证失败消息")
    public String failWords = "%1$s验证失败！";
}
