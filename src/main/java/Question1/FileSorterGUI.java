package Question1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileSorterGUI {

    private JFrame window;
    private Container con;
    private JPanel panel;
    private JLabel numberInQueue, maxString, inputFile, inputtedFile ,outputFile, outputtedFile, mergeProgress, splitProgress;
    private JButton processTask, enqueueTask;
    private JTextField maxStringField;
    private JProgressBar mergeProgressBar, splitProgressBar;

    private FileSorter fileSorter;

    int numberFilesInQueue;

    public FileSorterGUI() {

        FileSorter fileSorter = new FileSorter();

        window = new JFrame("FileSorter");
        window.setSize(400,300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        con = window.getContentPane();

        numberFilesInQueue = 0;

        panel = new JPanel();
        panel.setSize( 400, 300);
        panel.setVisible(true);
        panel.setLayout(null);

        numberInQueue = new JLabel("Number of items in queue: " + numberFilesInQueue);
        numberInQueue.setBounds(0,0, 200, 20);
        panel.add(numberInQueue);

        maxString = new JLabel("Max. Strings in memory:");
        maxString.setBackground(Color.white);
        maxString.setBounds(0,20, 200, 20);
        panel.add(maxString);

        maxStringField = new JTextField();
        maxStringField.setBounds(0,40, 400, 20);
        panel.add(maxStringField);

        inputFile = new JLabel("Input File:");
        inputFile.setBounds(0,60, 400, 20);
        panel.add(inputFile);

        inputtedFile = new JLabel();
        inputtedFile.setBounds(0,80, 400, 20);
        panel.add(inputtedFile);

        outputFile = new JLabel("Output File:");
        outputFile.setBounds(0,100, 400, 20);
        panel.add(outputFile);

        outputtedFile = new JLabel();
        outputtedFile.setBounds(0,120, 400, 20);
        panel.add(outputtedFile);

        mergeProgress = new JLabel("Merge Progress:");
        mergeProgress.setBounds(10,140, 400, 20);
        panel.add(mergeProgress);

        mergeProgressBar = new JProgressBar(0, 100);
        mergeProgressBar.setBounds(1,160, 382, 20);
        panel.add(mergeProgressBar);

        splitProgress = new JLabel("Split Progress:");
        splitProgress.setBounds(10,180, 400, 20);
        panel.add(splitProgress);

        splitProgressBar = new JProgressBar(0, 100);
        splitProgressBar.setBounds(1,200, 382, 20);
        panel.add(splitProgressBar);

        processTask = new JButton("Process Task");
        processTask.setBounds(30, 225, 150, 30);
        processTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maxStringField.getText().length() <= 0) JOptionPane.showMessageDialog(null, "Please set Max String");
                else if(numberFilesInQueue > 0) {
                    fileSorter.setLimit(Integer.parseInt(maxStringField.getText()));
                    mergeProgressBar.setValue(0);
                    splitProgressBar.setValue(0);
                    try {
                        fileSorter.sort(splitProgressBar, mergeProgressBar);
                        mergeProgressBar.setValue(fileSorter.splitSortProgress);
                        splitProgressBar.setValue(fileSorter.mergeSortProgress);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "File Sorted");
                    numberFilesInQueue--;
                    numberInQueue.setText("Number of items in queue: " + numberFilesInQueue);
                    if(numberFilesInQueue > 0) {
                        inputtedFile.setText(fileSorter.inputFirst().getName());
                        outputtedFile.setText(fileSorter.outputFirst().getName());
                    } else {
                        inputtedFile.setText("");
                        outputtedFile.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Add Files");
                }
            }
        });
        panel.add(processTask);

        enqueueTask = new JButton("Enqueue Task");
        enqueueTask.setBounds(200, 225, 150, 30);
        enqueueTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser inputFileChooser = new JFileChooser();
                int responseInput = inputFileChooser.showOpenDialog(null);

                if(responseInput != JFileChooser.APPROVE_OPTION) return;

                JFileChooser outputFileChooser = new JFileChooser();
                int responseOutput = outputFileChooser.showOpenDialog(null);

                if(responseOutput == JFileChooser.APPROVE_OPTION && responseInput == JFileChooser.APPROVE_OPTION) {
                    File input = new File(inputFileChooser.getSelectedFile().getAbsolutePath());
                    fileSorter.enqueueInput(input);

                    File output = new File(outputFileChooser.getSelectedFile().getAbsolutePath());
                    fileSorter.enqueueOutput(output);
                    numberFilesInQueue++;
                    numberInQueue.setText("Number of items in queue: " + numberFilesInQueue);
                    inputtedFile.setText(fileSorter.inputFirst().getName());
                    outputtedFile.setText(fileSorter.outputFirst().getName());
                }
            }
        });
        panel.add(enqueueTask);


        con.add(panel);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new FileSorterGUI();
    }
}
