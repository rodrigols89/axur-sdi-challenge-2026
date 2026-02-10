import java.util.List;

public final class HtmlAnalyzer {

    private final HtmlParserState parserState;
    private final DeepestTextTracker textTracker;

    public HtmlAnalyzer() {
        this.parserState = new HtmlParserState();
        this.textTracker = new DeepestTextTracker();
    }

    public static void main(final String[] args) {

        if (args.length == 0) {
            System.out.println("URL connection error");
            return;
        }

        final String url = args[0];
        final List<String> lines;

        try {
            lines = HtmlReader.readLinesFrom(url);
        } catch (final Exception exception) {
            System.out.println("URL connection error");
            return;
        }

        final HtmlAnalyzer analyzer = new HtmlAnalyzer();
        final ExecutionResult result = analyzer.analyze(lines);

        if (result == ExecutionResult.MALFORMED_HTML) {
            System.out.println("malformed HTML");
            return;
        }

        if (result == ExecutionResult.NO_TEXT_FOUND) {
            System.out.println("no text found");
            return;
        }

        System.out.println(analyzer.getResultText());
    }

    public ExecutionResult analyze(final List<String> lines) {

        boolean foundAnyTag = false;

        for (final String line : lines) {

            int index = 0;

            while (index < line.length()) {

                final int tagStart = line.indexOf('<', index);

                if (tagStart < 0) {
                    final String text = line.substring(index).trim();
                    this.textTracker.track(text, this.parserState.getCurrentDepth());
                    break;
                }

                if (tagStart > index) {
                    final String text = line.substring(index, tagStart).trim();
                    this.textTracker.track(text, this.parserState.getCurrentDepth());
                }

                final int tagEnd = line.indexOf('>', tagStart);
                if (tagEnd < 0) {
                    return ExecutionResult.MALFORMED_HTML;
                }

                final String rawTag = line.substring(tagStart + 1, tagEnd).trim();
                foundAnyTag = true;

                if (rawTag.startsWith("/")) {

                    final String tagName = extractTagName(rawTag.substring(1));

                    if (!this.parserState.closeTag(tagName)) {
                        return ExecutionResult.MALFORMED_HTML;
                    }

                } else if (!rawTag.endsWith("/")) {

                    final String tagName = extractTagName(rawTag);
                    this.parserState.openTag(tagName);
                }

                index = tagEnd + 1;
            }
        }

        if (!foundAnyTag) {
            return ExecutionResult.MALFORMED_HTML;
        }

        if (this.parserState.hasOpenTags()) {
            return ExecutionResult.MALFORMED_HTML;
        }

        if (!this.textTracker.hasText()) {
            return ExecutionResult.NO_TEXT_FOUND;
        }

        return ExecutionResult.SUCCESS;
    }

    private String extractTagName(final String rawTag) {

        final int spaceIndex = rawTag.indexOf(' ');
        return spaceIndex > 0
                ? rawTag.substring(0, spaceIndex)
                : rawTag;
    }

    public String getResultText() {
        return this.textTracker.getDeepestText();
    }
}
