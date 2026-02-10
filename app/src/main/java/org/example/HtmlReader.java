package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public final class HtmlReader {

    private HtmlReader() {
    }

    public static List<String> readLinesFrom(final String url)
            throws Exception {

        final URI uri = URI.create(url);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(uri.toURL().openStream())
        )) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
