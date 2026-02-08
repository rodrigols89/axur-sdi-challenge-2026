package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class HTMLReader {

    private HTMLReader() {
        /* Utility class */
    }

    public static List<String> readLinesFrom(
            final String url) throws IOException {

        final HttpURLConnection connection = openConnection(url);

        final List<String> lines = new ArrayList<>();

        try (InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(
                        stream,
                        StandardCharsets.UTF_8);
                BufferedReader buffered = new BufferedReader(reader)) {

            String line;
            while ((line = buffered.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    private static HttpURLConnection openConnection(
            final String urlString) throws IOException {

        final java.net.URI uri = java.net.URI.create(urlString);

        final java.net.URL url = uri.toURL();

        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5_000);
        connection.setReadTimeout(5_000);

        connection.connect();

        final int status = connection.getResponseCode();
        if (status < 200 || status >= 300) {
            throw new IOException("Non-success HTTP status");
        }

        return connection;
    }
}
