package academy.littlewitch.bot.commands.supercommand;

import academy.littlewitch.bot.config.Configuration;
import academy.littlewitch.bot.utils.Util;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;

public class ServerMonitorCommand implements EverywhereCommand {

    private static final String INFO_FORMAT = "时间：%1$s\n此进程占用cpu：%2$.2f%%\n" +
            "总cpu占用：%3$.2f%%\n物理内存：%4$s / %5$s，%6$.2f%%";

    private static OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @Override
    public String run(EventMessage eventPrivateMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.superCommandConfig.monitorServerCommand.enabled) {
            return null;
        }
        Date date = new Date(System.currentTimeMillis());
        double procCpuLoad = bean.getProcessCpuLoad() * 100;
        double cpuLoad = bean.getSystemCpuLoad() * 100;
        long fm = bean.getFreePhysicalMemorySize();
        long tm = bean.getTotalPhysicalMemorySize();
        String freeMem = Util.humanReadableByteCount(fm, false);
        String maxMem = Util.humanReadableByteCount(tm, false);
        double memPerc = ((double)fm) / tm * 100;
        return String.format(INFO_FORMAT, date.toString(), procCpuLoad, cpuLoad, freeMem, maxMem, memPerc);
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.superCommandConfig.monitorServerCommand.commandProperties;
    }
}
