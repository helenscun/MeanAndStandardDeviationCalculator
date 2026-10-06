import java.util.ArrayList;
import java.util.List;

/**
 * Holds everything produced by reading one input file: the valid numbers
 * plus a summary of the lines that were skipped.
 */
public class FileReadResult {

    private final NumberLinkedList numbers = new NumberLinkedList();
    private final List<String> invalidEntries = new ArrayList<>();
    private int totalLines;
    private int blankLines;

    /** @return list of valid numbers read */
    public NumberLinkedList getNumbers() {
        return numbers;
    }

    /** @return descriptions such as "Line 3: \"3b4\"" for each invalid line */
    public List<String> getInvalidEntries() {
        return invalidEntries;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getBlankLines() {
        return blankLines;
    }

    void incrementTotalLines() {
        totalLines++;
    }

    void incrementBlankLines() {
        blankLines++;
    }
}
