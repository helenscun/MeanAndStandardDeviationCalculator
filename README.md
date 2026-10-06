# Mean and Standard Deviation Calculator

A Java Swing application that reads real numbers (one per line) from a text
file, stores them in a **custom linked list**, and displays the mean and
standard deviation in a GUI text area.

## Project structure

| File | Purpose |
|------|---------|
| `src/StatsApp.java` | Swing GUI: button, `JFileChooser`, results text area |
| `src/NumberLinkedList.java` | Custom singly linked list (O(1) append, iterable) |
| `src/NumberFileReader.java` | Reads the file, validates each line |
| `src/FileReadResult.java` | Holds valid numbers + counts of blank/invalid lines |
| `src/Statistics.java` | Mean and standard deviation (single pass, Welford) |
| `src/ResultFormatter.java` | Builds the report text shown in the GUI |
| `src/TestRunner.java` | Console tests (no libraries needed) |
<img width="640" height="360" alt="image" src="https://github.com/user-attachments/assets/b88bc439-eb4a-4ea0-a872-eb0ac276b9fd" />


| `testdata/` | Sample input files for manual GUI testing |

## Build and run

Requires a JDK (Java 11 or newer). If not, use a docker. I don't know, lol.
<img width="1316" height="1126" alt="image" src="https://github.com/user-attachments/assets/739c6615-f51a-4c79-9905-34dfa8e4b3c4" />


```
mkdir out
javac -d out src/*.java
java -cp out StatsApp
```

Or import the `src` folder into Eclipse as a Java project and run `StatsApp`.
(`StatsApp` is plain Swing code, so it can be opened in WindowBuilder.)

## Run the tests

```
java -cp out TestRunner
```

## Input format

One real number per line, e.g.

```
12.5
-3
1e2
```

## Assumptions and decisions

- **Sample standard deviation** (divide by n-1) is the main result because
  the input is described as a "sample". Population SD (divide by n) is also
  shown.
- With exactly one value, the sample SD is undefined and is reported as such.
- With no valid values, no mean or SD is calculated and a message is shown.
- Blank (or whitespace-only) lines are skipped and counted.
- Invalid lines (e.g. `6v7`) are skipped, listed with their line number in
  the output, and do not stop processing.
- `NaN` and `Infinity` are treated as invalid.
- Surrounding whitespace on a line is ignored.
