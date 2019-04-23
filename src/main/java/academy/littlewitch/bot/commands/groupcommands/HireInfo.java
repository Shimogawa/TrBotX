package academy.littlewitch.bot.commands.groupcommands;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;

public class HireInfo {

    private EventPrivateMessage eventPrivateMessage;

    private int remainingSending;

    public Thread tickThread;

    public long startTime;

    public boolean isValid;

    public String description;

    public int playerNumber;

    public String mods;

    public String playTime;

    public HireInfo(EventPrivateMessage epm,
                    long startTime,
                    String description,
                    int playerNumber,
                    String mods,
                    String playTime) {
        this.remainingSending = 2;
        this.eventPrivateMessage = epm;
        this.startTime = startTime;
        this.isValid = true;
        this.description = description;
        this.playerNumber = playerNumber;
        this.mods = mods;
        this.playTime = playTime;

        tickThread = new Thread(this::sendInTime);
        tickThread.start();
    }

    @Override
    public String toString() {
        return String.format(
                Configuration.config.hireConfig.hireFormat,
                eventPrivateMessage.getSender().getInfo().getNickname(),
                eventPrivateMessage.getSender().getId(),
                description, playerNumber, mods, playTime);
    }

    private void sendInTime() {
        while (remainingSending > 0) {
            for (Long groupId : Configuration.config.trGroups) {
                eventPrivateMessage.getHttpApi().sendGroupMsg(groupId, this.toString());
            }
            this.remainingSending--;
            try {
                Thread.sleep(Configuration.config.hireConfig.sendInterval * 1000L);
            } catch (InterruptedException e) {
                for (Long supermanager : Configuration.config.superManagers) {
                    eventPrivateMessage.getHttpApi().sendPrivateMsg(supermanager, this.toString());
                    this.isValid = false;
                    return;
                }
            }
        }
        this.isValid = false;
    }
}
