import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Simple console test harness (no GUI, no external libraries).
 * Run from the project root:  java -cp out TestRunner
 * It builds each test file on the fly, runs the real reader + statistics
 * code and prints PASS / FAIL.
 */
public class TestRunner {

    private static int failures = 0;

    public static void main(String[] args) throws IOException {
        // 1. Normal data. Known answer: mean 5, sample SD 2.138089935
        run("Normal file", "2\n4\n4\n4\n5\n5\n7\n9\n", 8, 0, 0, 5.0, 2.138089935, 2.0);

        // 2. Invalid input such as "6v7" is reported and skipped
        run("Invalid input (6v7)", "1\n6v7\n3\n", 2, 0, 1, 2.0, 1.414213562, 1.0);

        // 3. Empty file
        run("Empty file", "", 0, 0, 0, Double.NaN, Double.NaN, Double.NaN);

        // 4. One line only
        run("One line only", "42.5\n", 1, 0, 0, 42.5, Double.NaN, 0.0);

        // 5. Blank lines in the middle
        run("Blank lines in middle", "727\n\n1454\n   \n2181\n", 3, 2, 0, 1454.0, 727.0, 593.593014);

        // 6. Extra: negatives, decimals, whitespace, scientific notation
        run("Mixed formats", "  -1.5 \n2.5\n1e1\n", 3, 0, 0, 3.666666667, 5.838093, 4.766783);

        // 7. Extra: NaN / Infinity words are rejected
        run("NaN and Infinity rejected", "NaN\nInfinity\n5\n", 1, 0, 2, 5.0, Double.NaN, 0.0);

        // 8. Extra: all lines invalid
        run("All invalid", "abc\n1,2\n--3\n", 0, 0, 3, Double.NaN, Double.NaN, Double.NaN);

        // 9. Extra: file without trailing newline
        run("No trailing newline", "1\n2\n3", 3, 0, 0, 2.0, 1.0, 0.816496581);

        // 10. Extra: nonexistent file produces IOException
        try {
            new NumberFileReader().read(new File("does_not_exist.txt"));
            check("Missing file throws IOException", false);
        } catch (IOException e) {
            check("Missing file throws IOException", true);
        }

        System.out.println(failures == 0 ? "\nALL TESTS PASSED" : "\n" + failures + " TEST(S) FAILED");
        System.exit(failures == 0 ? 0 : 1);
    }

    private static void run(String name, String content, int validCount, int blank,
                            int invalid, double mean, double sampleSd, double popSd)
            throws IOException {
        File f = File.createTempFile("stats", ".txt");
        f.deleteOnExit();
        Files.write(f.toPath(), content.getBytes());

        FileReadResult r = new NumberFileReader().read(f);
        Statistics s = new Statistics(r.getNumbers());

        boolean ok = s.getCount() == validCount
                && r.getBlankLines() == blank
                && r.getInvalidEntries().size() == invalid
                && same(s.getMean(), mean)
                && same(s.getSampleStdDev(), sampleSd)
                && same(s.getPopulationStdDev(), popSd);
        check(name, ok);
        if (!ok) {
            System.out.println("   got count=" + s.getCount() + " blank=" + r.getBlankLines()
                    + " invalid=" + r.getInvalidEntries().size() + " mean=" + s.getMean()
                    + " sd=" + s.getSampleStdDev() + " popSd=" + s.getPopulationStdDev());
        }
    }

    private static boolean same(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b)) return Double.isNaN(a) && Double.isNaN(b);
        return Math.abs(a - b) < 1e-5;
    }

    private static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name);
        if (!ok) failures++;
    }
}
