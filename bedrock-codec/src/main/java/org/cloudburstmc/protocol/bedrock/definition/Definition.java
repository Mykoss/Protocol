package org.cloudburstmc.protocol.bedrock.definition;

/**
 * A mapping for a protocol feature that uses runtime IDs to transmit data more efficiently.
 */
public interface Definition {

    /**
     * The runtime ID of this definition to be sent over the network.
     *
     * @return runtime id
     */
    int runtimeId();

    /**
     * JavaBean-style compatibility accessor used by older serializers and downstream forks.
     *
     * @return runtime id
     */
    default int getRuntimeId() {
        return runtimeId();
    }
}
