package org.example;

public enum ExecutionResult {

    CONNECTION_ERROR,
    MALFORMED_HTML,
    SUCCESS,
    NO_TEXT_FOUND;

    public boolean hasPriorityOver(final ExecutionResult other) {
        return this.ordinal() < other.ordinal();
    }

}
