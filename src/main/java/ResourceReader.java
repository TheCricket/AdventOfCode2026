import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Utility for reading text files from the classpath resources.
 */
public final class ResourceReader {

    private ResourceReader() {}

    /**
     * Reads a classpath resource into a String using UTF-8.
     * Place files under the standard resources folder (e.g., src/main/resources).
     *
     * @param resourcePath path to the resource (e.g., "input.txt" or "data/day1.txt").
     *                     Leading slash is optional.
     * @return the entire file contents as a String
     * @throws IOException if the resource cannot be found or read
     */
    public static String readResourceAsString(String resourcePath) throws IOException {
        try (InputStream autoCloseIn = getInputStream(resourcePath);
             InputStreamReader isr = new InputStreamReader(autoCloseIn, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (!first) sb.append('\n');
                sb.append(line);
                first = false;
            }
            return sb.toString();
        }
    }

    private static InputStream getInputStream(String resourcePath) throws FileNotFoundException {
        if (resourcePath == null || resourcePath.isEmpty()) {
            throw new IllegalArgumentException("resourcePath must not be null or empty");
        }

        String normalized = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ResourceReader.class.getClassLoader();
        }

        InputStream in = cl.getResourceAsStream(normalized);
        if (in == null) {
            throw new FileNotFoundException("Resource not found on classpath: " + resourcePath);
        }
        return in;
    }
}
