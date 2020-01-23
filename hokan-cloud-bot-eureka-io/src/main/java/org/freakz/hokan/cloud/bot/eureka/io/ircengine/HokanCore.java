package org.freakz.hokan.cloud.bot.eureka.io.ircengine;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelModel;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.service.HokanCoreRuntimeService;
import org.jibble.pircbot.PircBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class HokanCore extends PircBot {

    private static long transactionId = 0;
    private static final Map<Long, Long> transactionTimesMap = new ConcurrentHashMap<>();

    private final IrcServerConfigModel ircServerConfig;
    private final HokanCoreRuntimeService hokanCoreRuntimeService;

    public HokanCore(IrcServerConfigModel ircServerConfig, String botName, HokanCoreRuntimeService hokanCoreRuntimeService) {

        this.ircServerConfig = ircServerConfig;
        this.hokanCoreRuntimeService = hokanCoreRuntimeService;

        setVerbose(true);
        setName(botName);
        setVersion("Håkan The CloudBot 0.0.1");
        setLogin("hokan");
        setMessageDelay(1100);
    }

    @Override
    public void log(String message) {
        if (!message.contains("PING") && !message.contains("PONG")) {
            log.info(message);
        }
    }

    @Override
    protected void onConnect() {
        log.debug("Connected!");
        hokanCoreRuntimeService.coreConnected(this);
    }

    private static long getAndStoreTransactionId() {
        transactionId++;
        transactionTimesMap.put(transactionId, System.currentTimeMillis());
        return transactionId;
    }

    @Override
    protected void onDisconnect() {
        hokanCoreRuntimeService.coreDisconnected(this);
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message, byte[] original) {

        RawIRCEvent event = RawIRCEvent.builder()
                .transactionId(getAndStoreTransactionId())
                .source(this.ircServerConfig.getUniqueIdent())
                .type("MESSAGE")
                .build()
                .addParameter(channel)
                .addParameter(sender)
                .addParameter(login)
                .addParameter(hostname)
                .addParameter(message);

        hokanCoreRuntimeService.publishRawIRCEvent(event);
    }


    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message, byte[] original) {
        super.onPrivateMessage(sender, login, hostname, message, original);
    }

    public boolean sendMessageToIRC(MessageToIRCEvent messageToIRCEvent) {
        String channel = messageToIRCEvent.getChannel();
        sendMessage(channel, messageToIRCEvent.getMessage());
        Long startMillis = transactionTimesMap.remove(messageToIRCEvent.getTransactionId());
        if (startMillis != null) {
            long diff = System.currentTimeMillis() - startMillis;
            String diffStr = String.format("transactionId: %d handling time: %d ms", messageToIRCEvent.getTransactionId(), diff);
            log.debug(":: {}", diffStr);
        }
        return true;
    }


    @Override
    public String toString() {
        return String.format("[%s] %s:%d", ircServerConfig.getNetwork(), ircServerConfig.getServer(), ircServerConfig.getPort());
    }


    public String getUniqueIdent() {
        return ircServerConfig.getUniqueIdent();
    }

    public String getNetwork() {
        return ircServerConfig.getNetwork();
    }

    public List<ChannelModel> getJoinedChannels() {
        List<ChannelModel> channelList = new ArrayList<>();
        for (String channel : getChannels()) {
            ChannelModel model = ChannelModel.builder().name(channel).topic("?").build();
            channelList.add(model);
        }
        return channelList;
    }
}
