package org.example;

import java.util.ArrayDeque;
import java.util.Deque;

public final class HTMLParserState {

    private final Deque<String> openTagsArray;

    public HTMLParserState() {
        this.openTagsArray = new ArrayDeque<>();
    }

    public void openTag(final String tagName) {
        if (tagName == null || tagName.isBlank()) {
            return;
        }
        this.openTagsArray.push(tagName);
    }

    public String closeTag() {
        return this.openTagsArray.pop();
    }

    public int getCurrentDepth() {
        return this.openTagsArray.size();
    }

    public boolean hasopenTagsArray() {
        return !this.openTagsArray.isEmpty();
    }
}
