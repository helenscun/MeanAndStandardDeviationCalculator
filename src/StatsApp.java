import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Main window. The user presses "Select File...", picks a text file with
 * one real number per line, and the mean and standard deviation are shown
 * in a text area.
 *
 * The window only handles the user interface; reading, storage and math
 * live in NumberFileReader, NumberLinkedList and Statistics.
 */
public final class StatsApp extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JTextArea outputArea = new JTextArea();
    private final JLabel fileLabel = new JLabel("No file selected");

    /** Builds the window. */
    public StatsApp() {
        setTitle("Mean and Standard Deviation Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(560, 460);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(content);

        // Top bar: button + selected file name
        JButton selectButton = new JButton("Select File...");
        selectButton.addActionListener(e -> chooseAndProcessFile());
        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.add(selectButton, BorderLayout.WEST);
        top.add(fileLabel, BorderLayout.CENTER);
        content.add(top, BorderLayout.NORTH);

        // Results area
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        outputArea.setText("Press \"Select File...\" to choose a text file of numbers "
                + "(one per line).");
        content.add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    /** Opens a JFileChooser and, if a file is chosen, processes it. */
    private void chooseAndProcessFile() {
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        chooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
        chooser.setAcceptAllFileFilterUsed(true); // let the user pick any file

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            processFile(chooser.getSelectedFile());
        }
    }

    /** Reads the file, computes statistics and displays the report. */
    private void processFile(File file) {
        fileLabel.setText(file.getAbsolutePath());
        try {
            FileReadResult result = new NumberFileReader().read(file);
            Statistics stats = new Statistics(result.getNumbers());
            outputArea.setText(ResultFormatter.format(file, result, stats));
            outputArea.setCaretPosition(0);
        } catch (IOException ex) {
            outputArea.setText("Could not read the file:\n" + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Could not read the file.",
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Program entry point. */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new StatsApp().setVisible(true));
    }
}
