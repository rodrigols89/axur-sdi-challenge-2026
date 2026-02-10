public final class DeepestTextTracker {

    private int deepestDepth;
    private String deepestText;
    private boolean foundAnyText;

    public DeepestTextTracker() {
        this.deepestDepth = 0;
        this.deepestText = null;
        this.foundAnyText = false;
    }

    public void track(final String text, final int depth) {

        if (text == null || text.isBlank()) {
            return;
        }

        this.foundAnyText = true;

        if (depth > this.deepestDepth) {
            this.deepestDepth = depth;
            this.deepestText = text.trim();
        }
    }

    public boolean hasText() {
        return this.foundAnyText;
    }

    public String getDeepestText() {
        return this.deepestText;
    }
}
