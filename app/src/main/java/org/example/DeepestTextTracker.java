package org.example;

public class DeepestTextTracker {

    private int maxDepth;
    private String deepestText;

    DeepestTextTracker() {
        this.maxDepth = -1;
        this.deepestText = null;
    }

    public void considerText(final String text, final int currentDepth) {
        if (currentDepth > this.maxDepth) {
            this.maxDepth = currentDepth;
            this.deepestText = text;
        }
    }

    public boolean hasResult() {
        return this.deepestText != null;
    }

    public String getDeepestText() {
        return this.deepestText;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }
}
