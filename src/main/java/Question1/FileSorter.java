/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question1;

import javax.swing.*;
import java.io.*;

/**
 *
 * @author Peter An
 */

public class FileSorter<E> {

    private int limit;
    private LinkedQueue<File> inputFiles;
    private LinkedQueue<File> outputFiles;

    public FileSorter(int limit) {
        this.limit = limit;
        this.inputFiles = new LinkedQueue<>();
        this.outputFiles = new LinkedQueue<>();
    }

    public FileSorter() {
        this.limit = 10;
        this.inputFiles = new LinkedQueue<>();
        this.outputFiles = new LinkedQueue<>();
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void enqueueInput(File input) {
        inputFiles.enqueue(input);
    }

    public void enqueueOutput(File output) {
        outputFiles.enqueue(output);
    }

    public File inputFirst() {
        return inputFiles.first();
    }

    public File outputFirst() {
        return outputFiles.first();
    }

    public void sort(JProgressBar split, JProgressBar merge) throws IOException {
        if(inputFiles.isEmpty() && outputFiles.isEmpty()) return;       // clause case

//      dequeue inputs and outputs
        File input = inputFiles.dequeue();
        File output = outputFiles.dequeue();

//      delete to restart algorithm
        if(output.exists()) output.delete();

        ArraySorter sorter = new ArraySorter();

//      quick split sort
        LinkedQueue<FileInfo> splitFiles = this.splitSort(sorter, input, split);

//      merge sort
        mergeSort(sorter, splitFiles, merge);

//      rename to output file
        File deleteFile = splitFiles.dequeue().file;
        deleteFile.renameTo(output);
    }

    public int splitSortProgress;

    private LinkedQueue<FileInfo> splitSort(ArraySorter sorter, File input, JProgressBar split) throws IOException {
        splitSortProgress = 0;
        BufferedReader br = new BufferedReader(new FileReader(input));
        LinkedQueue<FileInfo> tempFiles = new LinkedQueue<>();

        String st = br.readLine();
        int counter = 0;
        while(st != null) {

            String[] temp = new String[limit];
            File file = new File("temp"+counter+".txt");
            FileWriter myWriter = new FileWriter(file);

            int length = 0;
            for (int i=0; i < limit && st != null; i++) {
                temp[i] = st;
                st = br.readLine();
                length++;
            }
            sorter.quickSort(temp, length);
            for(String line : temp) if(line != null) myWriter.write(line + "\n");
            tempFiles.enqueue(new FileInfo(file, length));
            myWriter.close();
            counter++;
            splitSortProgress += 10;
            split.setValue(splitSortProgress);
            System.out.println(splitSortProgress);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        br.close();
        splitSortProgress = 100;
        split.setValue(splitSortProgress);
        return tempFiles;
    }

    public int mergeSortProgress;

    public void mergeSort(ArraySorter sorter, LinkedQueue<FileInfo> splitFiles, JProgressBar merge) throws IOException {

        mergeSortProgress = 0;
        int counter = 0;
        while(splitFiles.size() > 1) {
            FileInfo file1 = splitFiles.dequeue();
            FileInfo file2 = splitFiles.dequeue();

            BufferedReader br1 = new BufferedReader(new FileReader(file1.file));
            BufferedReader br2 = new BufferedReader(new FileReader(file2.file));
            String st1 = br1.readLine();
            String st2 = br2.readLine();

            File file = new File("tempw"+counter+".txt");
            FileWriter myWriter = new FileWriter(file);
            String[] temp = new String[file1.length + file2.length];
            int length = 0;


            while(st1 != null) {
                temp[length] = st1;
                st1 = br1.readLine();
                length++;
            }
            br1.close();
            file1.file.delete();

            while(st2 != null) {
                temp[length] = st2;
                st2 = br2.readLine();
                length++;
            }
            br2.close();
            file2.file.delete();

            sorter.mergeSort(temp);

            for(String line : temp) if(line != null) myWriter.write(line + "\n");
            splitFiles.enqueue(new FileInfo(file, length));
            myWriter.close();
            counter++;
            mergeSortProgress += 10;
            merge.setValue(mergeSortProgress);
            System.out.println(mergeSortProgress);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mergeSortProgress = 100;
        merge.setValue(mergeSortProgress);
    }

    public static void main(String[] args) throws IOException {
//        Testing

//        File input = new File("countries.txt");
//
//        File output = new File("countriesSorted.txt");
//
//        FileSorter fileSorter = new FileSorter(20, input, output);
//        Thread sort = new Thread(fileSorter);
//        sort.run();
    }

}

class FileInfo {

    public File file;
    public int length;

    public FileInfo(File file, int length) {
        this.file = file;
        this.length = length;
    }
}
