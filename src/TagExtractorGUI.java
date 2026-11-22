import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TagExtractorGUI extends JFrame {

    private JTextArea outputArea;

    private JLabel fileLabel;

    private File textFile;

    private File stopWordFile;

    private TagExtractor extractor;

    public TagExtractorGUI() {

        extractor = new TagExtractor();

        setTitle("Tag Extractor");

        setSize(700,600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        JButton chooseTextButton =
                new JButton("Select Text File");

        JButton chooseStopButton =
                new JButton("Select Stop Words");

        JButton extractButton =
                new JButton("Extract Tags");

        JButton saveButton =
                new JButton("Save Results");

        fileLabel =
                new JLabel("No File Selected");

        topPanel.add(chooseTextButton);
        topPanel.add(chooseStopButton);
        topPanel.add(extractButton);
        topPanel.add(saveButton);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();

        JScrollPane scrollPane =
                new JScrollPane(outputArea);

        add(scrollPane, BorderLayout.CENTER);

        add(fileLabel, BorderLayout.SOUTH);

        chooseTextButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            if(chooser.showOpenDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                textFile =
                        chooser.getSelectedFile();

                fileLabel.setText(
                        "Text File: "
                                + textFile.getName());
            }
        });

        chooseStopButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            if(chooser.showOpenDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                stopWordFile =
                        chooser.getSelectedFile();
            }
        });

        extractButton.addActionListener(e -> {

            try {

                extractor.loadStopWords(
                        stopWordFile);

                extractor.extractTags(
                        textFile);

                outputArea.setText(
                        extractor.getTagReport());

            }

            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage());
            }
        });

        saveButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            if(chooser.showSaveDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                try {

                    extractor.saveResults(
                            chooser.getSelectedFile());

                    JOptionPane.showMessageDialog(
                            this,
                            "Saved Successfully");

                }

                catch(Exception ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {

        new TagExtractorGUI();
    }
}