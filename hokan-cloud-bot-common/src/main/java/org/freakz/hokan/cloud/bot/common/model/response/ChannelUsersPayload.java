package org.freakz.hokan.cloud.bot.common.model.response;

import lombok.Builder;
import lombok.Data;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelUserModel;

import java.util.List;


@Data
@Builder
public class ChannelUsersPayload extends Payload {

    private List<ChannelUserModel> joinedChannels;

}
