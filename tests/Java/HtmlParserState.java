import java.util.ArrayDeque;
import java.util.Deque;

public final class HtmlParserState {

    private final Deque<String> openTagsArray;

    public HtmlParserState() {
        this.openTagsArray = new ArrayDeque<>();
    }

    public void openTag(final String tagName) {
        if (tagName == null || tagName.isBlank()) {
            return;
        }
        this.openTagsArray.push(tagName);
    }

    public boolean closeTag(final String tagName) {

        if (tagName == null || tagName.isBlank()) {
            return false;
        }

        if (this.openTagsArray.isEmpty()) {
            return false;
        }

        final String lastOpenedTag = this.openTagsArray.pop();
        return lastOpenedTag.equals(tagName);
    }

    public int getCurrentDepth() {
        return this.openTagsArray.size();
    }

    public boolean hasOpenTags() {
        return !this.openTagsArray.isEmpty();
    }
}
