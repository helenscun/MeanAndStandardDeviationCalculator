import java.io.File;

/** Turns a FileReadResult and its Statistics into the text shown in the GUI. */
public class ResultFormatter {

    /** Builds the report text. */
    public static String format(File file, FileReadResult result, Statistics stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("File: ").append(file.getName()).append("\n");
        sb.append("Lines read: ").append(result.getTotalLines()).append("\n");
        sb.append("Valid numbers: ").append(stats.getCount()).append("\n");
        sb.append("Blank lines skipped: ").append(result.getBlankLines()).append("\n");
        sb.append("Invalid lines skipped: ")
          .append(result.getInvalidEntries().size()).append("\n");
        for (String bad : result.getInvalidEntries()) {
            sb.append("    ").append(bad).append("\n");
        }
        sb.append("\n");

        if (stats.getCount() == 0) {
            sb.append("No valid numbers found, so the mean and standard ")
              .append("deviation cannot be calculated.\n");
            return sb.toString();
        }

        sb.append(String.format("Mean: %.6f%n", stats.getMean()));
        if (stats.getCount() < 2) {
            sb.append("Sample standard deviation: undefined (needs at least 2 values)\n");
        } else {
            sb.append(String.format("Sample standard deviation (n-1): %.6f%n",
                    stats.getSampleStdDev()));
        }
        sb.append(String.format("Population standard deviation (n): %.6f%n",
                stats.getPopulationStdDev()));
        return sb.toString();
    }
}
