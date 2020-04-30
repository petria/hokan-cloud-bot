package org.freakz.hokan.cloud.bot.eureka.io.ircengine;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelModel;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelUserModel;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.exception.HokanException;
import org.freakz.hokan.cloud.bot.eureka.io.service.HokanCoreRuntimeService;
import org.freakz.hokan.cloud.bot.eureka.io.service.HokanCoreRuntimeServiceImpl;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.connection.ClientConnectionEndedEvent;
import org.kitteh.irc.client.library.event.connection.ClientConnectionEstablishedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class HokanCoreNew {

    private static long transactionId = 0;
    private static final Map<Long, Long> transactionTimesMap = new ConcurrentHashMap<>();
    private final Map<String, String> channelTopics = new ConcurrentHashMap<>();

    private final IrcServerConfigModel ircServerConfig;
    private final HokanCoreRuntimeService hokanCoreRuntimeService;
    private Client client;

    public HokanCoreNew(IrcServerConfigModel ircServerConfig, HokanCoreRuntimeServiceImpl hokanCoreRuntimeService) {
        this.ircServerConfig = ircServerConfig;
        this.hokanCoreRuntimeService = hokanCoreRuntimeService;
    }

    public void connect(String nick) {
        log.debug("Connecting with nick: {}", nick);

        Client.Builder botBuilder = Client.builder();
        botBuilder.name("Hokan the Cloud Bot");
        botBuilder.nick(nick);
        botBuilder.server().host(ircServerConfig.getServer());
        botBuilder.server().port(ircServerConfig.getPort());
        botBuilder.server().secure(false);
        botBuilder.listeners().input(input -> log.info("[IN] " + input));
        botBuilder.listeners().output(output -> log.info("[OUT] " + output));

        this.client = botBuilder.buildAndConnect();
        client.getEventManager().registerEventListener(this);
//        this.client.addChannel("#HokanCLOUD");
        log.debug("Connected!");
    }


    private static long getAndStoreTransactionId() {
        transactionId++;
        transactionTimesMap.put(transactionId, System.currentTimeMillis());
        return transactionId;
    }

    //-------------- HANDLERS

    @Handler
    public void onMessage(ChannelMessageEvent e) {
        RawIRCEvent rawIRCEvent = RawIRCEvent.builder()
                .transactionId(getAndStoreTransactionId())
                .source(this.ircServerConfig.getUniqueIdent())
                .type("MESSAGE")
                .build()
                .addParameter(e.getChannel().getName())
                .addParameter(e.getActor().getNick())
                .addParameter(e.getActor().getUserString())
                .addParameter(e.getActor().getHost())
                .addParameter(e.getMessage());

        hokanCoreRuntimeService.publishRawIRCEvent(rawIRCEvent);

    }

    @Handler
    public void onConnect(ClientConnectionEstablishedEvent event) {
        log.debug("connected: {}", event);
        hokanCoreRuntimeService.coreConnected(this);
    }


    @Handler
    protected void onDisconnect(ClientConnectionEndedEvent event) {
        log.debug("connection ended: {}", event);
        hokanCoreRuntimeService.coreDisconnected(this);
    }

    @Handler
    public void onUserJoinChannel(ChannelJoinEvent event) {
        if (event.getClient().isUser(event.getUser())) { // It's me!
            event.getChannel().sendMessage("Hello world! Kitteh's here for cuddles.");
            return;
        }
        // It's not me!
        event.getChannel().sendMessage("Welcome, " + event.getUser().getNick() + "! :3");
    }

    //--------------


    public void disconnect() {
        this.client.shutdown();
        this.client = null;
    }

    public void joinChannel(String channel) {
        if (this.client != null) {
            this.client.addChannel(channel);
        } else {
            throw new HokanException(this.ircServerConfig.getNetwork());
        }
    }

    public String getUniqueIdent() {
        return ircServerConfig.getUniqueIdent();
    }

    public String getNetwork() {
        return ircServerConfig.getNetwork();
    }

    public boolean sendMessageToIRC(MessageToIRCEvent messageToIRCEvent) {
        String channelName = messageToIRCEvent.getChannel();
        Optional<Channel> channel = client.getChannel(channelName);
        if (channel.isPresent()) {
            channel.get().sendMessage(messageToIRCEvent.getMessage());
            Long startMillis = transactionTimesMap.remove(messageToIRCEvent.getTransactionId());
            if (startMillis != null) {
                long diff = System.currentTimeMillis() - startMillis;
                String diffStr = String.format("transactionId: %d handling time: %d ms", messageToIRCEvent.getTransactionId(), diff);
                log.debug(":: {}", diffStr);
            }
            return true;
        }
        log.error("No channel: {}", messageToIRCEvent);
        return false;
    }

    public List<ChannelModel> getJoinedChannels() {
        List<ChannelModel> channelList = new ArrayList<>();
        for (Channel channel : client.getChannels()) {
            ChannelModel model = ChannelModel.builder().name(channel.getName()).topic(channel.getTopic().toString()).build();
            channelList.add(model);
        }
        return channelList;
    }

    public List<ChannelUserModel> getChannelUsers(String channelName) {
        if (client.getChannel(channelName).isPresent()) {
            List<ChannelUserModel> userModelList = new ArrayList<>();
            for (String nickName : client.getChannel(channelName).get().getNicknames()) {
                ChannelUserModel user = ChannelUserModel.builder()
                        .nick(nickName).build();
//                        .prefix(pircBotUser.getPrefix())
//                        .hasVoice(pircBotUser.hasVoice())
// TODO                        .isOp(pircBotUser.isOp())
//                        .build();

                userModelList.add(user);
            }

            return userModelList;

        }
        return null;
    }

    public boolean sendWhoChannel(String channelName) {
        if (client != null) {
            client.sendRawLine("WHO " + channelName);
            return true;
        }
        return false;
    }

    public boolean joinNetworkChannel(String channelName) {
        if (client != null) {
            client.addChannel(channelName);
            return true;
        }
        return false;
    }

    public boolean partNetworkChannel(String channelName) {
        if (client != null) {
            if (client.getChannel(channelName).isPresent()) {
                client.getChannel(channelName).get().part();
                return true;
            }
        }
        return false;
    }
}
