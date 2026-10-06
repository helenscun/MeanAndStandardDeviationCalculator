/**
 * Computes the mean and standard deviation of the values in a
 * NumberLinkedList.
 *
 * The calculation uses Welford's algorithm, which needs only ONE pass over
 * the list and is numerically more stable than the "sum of squares" formula.
 *
 * Assumption: the input is a SAMPLE, so the sample standard deviation
 * (divide by n - 1) is the primary result. The population standard
 * deviation (divide by n) is also available.
 */
public class Statistics {

    private final int count;
    private final double mean;
    private final double sumSquaredDiffs; // sum of (x - mean)^2

    /** Walks the list once and computes the statistics. */
    public Statistics(NumberLinkedList list) {
        int n = 0;
        double runningMean = 0.0;
        double m2 = 0.0;
        for (double x : list) {
            n++;
            double delta = x - runningMean;
            runningMean += delta / n;
            m2 += delta * (x - runningMean);
        }
        this.count = n;
        this.mean = runningMean;
        this.sumSquaredDiffs = m2;
    }

    /** @return number of values used */
    public int getCount() {
        return count;
    }

    /** @return arithmetic mean, or NaN if there are no values */
    public double getMean() {
        return count == 0 ? Double.NaN : mean;
    }

    /**
     * @return sample standard deviation (n - 1 denominator).
     *         NaN if fewer than 2 values (undefined for a single value).
     */
    public double getSampleStdDev() {
        return count < 2 ? Double.NaN : Math.sqrt(sumSquaredDiffs / (count - 1));
    }

    /** @return population standard deviation (n denominator), NaN if empty */
    public double getPopulationStdDev() {
        return count == 0 ? Double.NaN : Math.sqrt(sumSquaredDiffs / count);
    }
}
