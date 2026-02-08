package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public final class HTMLReader {

    private HTMLReader() {
        /* Utility class */
    }

    public static HttpURLConnection openConnection(
            final String urlString) throws IOException {

        final URI uri = URI.create(urlString);
        final URL url = uri.toURL();

        final HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5_000);
        connection.setReadTimeout(5_000);

        connection.connect();

        final int statusCode = connection.getResponseCode();
        if (statusCode < 200 || statusCode >= 300) {
            throw new IOException("Non-success HTTP status");
        }

        return connection;
    }
}
