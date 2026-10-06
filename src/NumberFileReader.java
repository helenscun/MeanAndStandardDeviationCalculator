import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads real numbers (one per line) from a text file into a linked list.
 *
 * Input rules (documented assumptions):
 *  - Blank / whitespace-only lines are ignored and counted separately.
 *  - Leading and trailing whitespace on a line is ignored.
 *  - A line that is not a valid real number (e.g. "6v7") is skipped and
 *    recorded as invalid; reading continues with the next line.
 *  - "NaN" and "Infinity" are rejected even though Java can parse them,
 *    because they are not meaningful data for a mean / std deviation.
 */
public class NumberFileReader {

    /**
     * Reads the given file.
     *
     * @param file the file to read
     * @return a FileReadResult with the valid numbers and invalid-line info
     * @throws IOException if the file cannot be opened or read
     */
    public FileReadResult read(File file) throws IOException {
        FileReadResult result = new FileReadResult();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                result.incrementTotalLines();
                String text = line.trim();

                if (text.isEmpty()) {
                    result.incrementBlankLines();
                    continue;
                }
                try {
                    double value = Double.parseDouble(text);
                    if (Double.isNaN(value) || Double.isInfinite(value)) {
                        throw new NumberFormatException("non-finite");
                    }
                    result.getNumbers().add(value);
                } catch (NumberFormatException e) {
                    result.getInvalidEntries()
                          .add("Line " + lineNumber + ": \"" + text + "\"");
                }
            }
        }
        return result;
    }
}
