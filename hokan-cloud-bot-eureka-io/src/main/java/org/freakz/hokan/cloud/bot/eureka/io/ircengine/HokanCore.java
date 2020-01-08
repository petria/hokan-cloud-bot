package org.freakz.hokan.cloud.bot.eureka.io.ircengine;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRC;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.service.HokanCoreRuntimeServiceImpl;
import org.jibble.pircbot.PircBot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class HokanCore extends PircBot {

    private static long transactionId = 0;
    private static final Map<Long, Long> transactionTimesMap = new ConcurrentHashMap<>();

    private final IrcServerConfigModel ircServerConfig;
    private final HokanCoreRuntimeServiceImpl hokanCoreRuntimeService;

    public HokanCore(IrcServerConfigModel ircServerConfig, String botName, HokanCoreRuntimeServiceImpl hokanCoreRuntimeService) {

        this.ircServerConfig = ircServerConfig;
        this.hokanCoreRuntimeService = hokanCoreRuntimeService;

        setVerbose(true);
        setName(botName);
        setVersion("0.0.1");
        setLogin("hokan_cloud");
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
    }

    private static long getAndStoreTransactionId() {
        transactionId++;
        transactionTimesMap.put(transactionId, System.currentTimeMillis());
        return transactionId;
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message, byte[] original) {
        super.onMessage(channel, sender, login, hostname, message, original);

        RawIRCEvent event = builderEvent(this.ircServerConfig.getUniqueIdent(), "MESSAGE")
                .transactionId(getAndStoreTransactionId())
                .parameter(channel)
                .parameter(sender)
                .parameter(login)
                .parameter(hostname)
                .parameter(message);

        hokanCoreRuntimeService.publishRawIRCEvent(event);
    }

    private RawIRCEvent builderEvent(String uniqueIdent, String type) {
        return new RawIRCEvent(uniqueIdent, type);
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message, byte[] original) {
        super.onPrivateMessage(sender, login, hostname, message, original);
    }

    public String getUniqueIdent() {
        return ircServerConfig.getUniqueIdent();
    }

    public boolean sendMessageToIRC(MessageToIRC messageToIRC) {
        String channel = messageToIRC.getChannel();
        sendMessage(channel, messageToIRC.getMessage());
        Long startMillis = transactionTimesMap.remove(messageToIRC.getTransactionId());
        if (startMillis != null) {
            long diff = System.currentTimeMillis() - startMillis;
            String diffStr = String.format("transactionId: %d handling time: %d ms", messageToIRC.getTransactionId(), diff);
            log.debug(":: {}", diffStr);
        }
        return true;
    }
}
