package org.freakz.hokan.cloud.bot.common.model.response;

import lombok.Builder;
import lombok.Data;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelModel;

import java.util.List;


@Data
@Builder
public class JoinedChannelsServicePayload extends Payload {

    private List<ChannelModel> joinedChannels;

}
