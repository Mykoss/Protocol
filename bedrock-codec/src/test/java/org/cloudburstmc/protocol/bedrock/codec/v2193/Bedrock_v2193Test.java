package org.cloudburstmc.protocol.bedrock.codec.v2193;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Bedrock_v2193Test {

    @Test
    void exposesMinecraft12652ProtocolIdentity() {
        assertEquals(2193, Bedrock_v2193.CODEC.getProtocolVersion());
        assertEquals("1.26.52", Bedrock_v2193.CODEC.getMinecraftVersion());
    }
}
