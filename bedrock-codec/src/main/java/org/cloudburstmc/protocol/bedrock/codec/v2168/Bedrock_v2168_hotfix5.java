package org.cloudburstmc.protocol.bedrock.codec.v2168;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;

public class Bedrock_v2168_hotfix5 extends Bedrock_v2168_hotfix4 {

    public static final BedrockCodec CODEC = Bedrock_v2168_hotfix4.CODEC.toBuilder()
            .minecraftVersion("1.26.45")
            .build();
}
