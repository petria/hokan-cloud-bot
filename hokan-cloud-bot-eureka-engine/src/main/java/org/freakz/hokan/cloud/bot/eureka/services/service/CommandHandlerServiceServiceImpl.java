package org.freakz.hokan.cloud.bot.eureka.services.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.ToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.eureka.services.client.IrcIOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class CommandHandlerServiceServiceImpl implements CommandHandlerService {


    private final IrcIOClient ircIOClient;

    @Autowired
    public CommandHandlerServiceServiceImpl(IrcIOClient ircIOClient) {
        this.ircIOClient = ircIOClient;
    }

    @Async
    @Override
    public void handleMessage(RawIRCEvent event) {

        handleLine(event);

    }

    private void handleLine(RawIRCEvent event) {
        String line = event.getParameter("4");
        if (line.equals("!ping")) {
            Random rnd = new Random();
            long delay = 5000 + (rnd.nextInt(10) * 1000);
            log.debug("Async: {}", delay);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendReply(event.getTransactionId() + " :: pong: " + delay, event.getSource(), event.getParameters().get("0"), event.getTransactionId());
        }

    }

    private void sendReply(String reply, String target, String channel, long transactionId) {
        ToIRCEvent message = new ToIRCEvent();
        message.setTransactionId(transactionId);
        message.setMessage(reply);
        message.setTarget(target);
        message.setChannel(channel);
        log.debug("Post reply!");
        ircIOClient.postMessageToIRC(message);
    }
}
