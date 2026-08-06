package org.cloudburstmc.protocol.bedrock.definition;

public interface NamedDefinition extends Definition {
    /**
     * The identifier of this definition.
     *
     * @return identifier
     */
    String identifier();

    /**
     * JavaBean-style compatibility accessor used by older serializers and downstream forks.
     *
     * @return identifier
     */
    default String getIdentifier() {
        return identifier();
    }
}
