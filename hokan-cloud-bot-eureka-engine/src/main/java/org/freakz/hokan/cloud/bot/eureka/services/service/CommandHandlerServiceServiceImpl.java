package org.freakz.hokan.cloud.bot.eureka.services.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRC;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.eureka.services.client.IrcIOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommandHandlerServiceServiceImpl implements CommandHandlerService {

    private final IrcIOClient ircIOClient;

    @Autowired
    public CommandHandlerServiceServiceImpl(IrcIOClient ircIOClient) {
        this.ircIOClient = ircIOClient;
    }

    @Override
    public void handleMessage(RawIRCEvent event) {
        String line = event.getParameters().get("4");
        if (line.equals("!ping")) {
            sendReply("pong", event.getSource(), event.getParameters().get("0"), event.getTransactionId());
        }
    }

    private void sendReply(String reply, String target, String channel, long transactionId) {
        MessageToIRC message = new MessageToIRC();
        message.setTransactionId(transactionId);
        message.setMessage(reply);
        message.setTarget(target);
        message.setChannel(channel);
        log.debug("Post reply!");
        ircIOClient.postMessageToIRC(message);
    }
}
