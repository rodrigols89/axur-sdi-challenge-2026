package org.example;

import java.util.List;

public final class HTMLDepthAnalyzer {

    public static void main(final String[] args) {

        /*
         * =====================
         * INPUT VALIDATION
         * =====================
         */
        if (args == null || args.length == 0) {
            System.out.println("URL connection error");
            return;
        }

        final String url = args[0];

        if (url == null || url.isBlank()) {
            System.out.println("URL connection error");
            return;
        }

        /*
         * =====================
         * PROCESSING
         * =====================
         */
        final HTMLAnalyzer analyzer = new HTMLAnalyzer();
        final List<String> lines;

        try {
            lines = HTMLReader.readLinesFrom(url);
        } catch (final Exception exception) {
            System.out.println("URL connection error");
            return;
        }

        analyzer.analyze(lines);

        final ExecutionResult result = analyzer.getExecutionResult();

        if (result == ExecutionResult.CONNECTION_ERROR) {
            System.out.println("URL connection error");
            return;
        }

        if (result == ExecutionResult.MALFORMED_HTML) {
            System.out.println("malformed HTML");
            return;
        }

        final String text = analyzer.getResultText();

        /*
         * =====================
         * OUTPUT
         * =====================
         */
        if (text != null) {
            System.out.println(text);
        }
    }
}
