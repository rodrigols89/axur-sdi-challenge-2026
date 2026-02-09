package org.example;

import java.util.List;

public final class HTMLAnalyzer {

    private final HTMLParserState parserState;
    private final DeepestTextTracker textTracker;

    private ExecutionResult executionResult;

    public HTMLAnalyzer() {
        this.parserState = new HTMLParserState();
        this.textTracker = new DeepestTextTracker();
        this.executionResult = ExecutionResult.SUCCESS;
    }

    private boolean isOpeningTag(final String line) {

        if (!line.startsWith("<")) {
            return false;
        }

        if (line.startsWith("</")) {
            return false;
        }

        if (line.endsWith("/>")) {
            return false;
        }

        return true;
    }

    private boolean isClosingTag(final String line) {

        if (!line.startsWith("</")) {
            return false;
        }

        if (!line.endsWith(">")) {
            return false;
        }

        return true;
    }

    private void processLine(final String line) {
        if (line == null || line.isBlank()) {
            return;
        }
        final String sanitizedLine = line.trim();
    }

    private void finalizeAnalysis() {
        if (this.parserState.hasopenTagsArray()) {
            this.executionResult = ExecutionResult.MALFORMED_HTML;
        }
    }

    public String getResultText() {
        if (this.textTracker.hasResult()) {
            return this.textTracker.getDeepestText();
        }
        return null;
    }

    public void markConnectionError() {
        this.executionResult = ExecutionResult.CONNECTION_ERROR;
    }

    public ExecutionResult getExecutionResult() {
        return this.executionResult;
    }

    public void analyze(final List<String> lines) {

        for (final String line : lines) {

            this.processLine(line);

            if (this.executionResult.hasPriorityOver(ExecutionResult.SUCCESS)) {
                break;
            }
        }
        this.finalizeAnalysis();
    }
}
