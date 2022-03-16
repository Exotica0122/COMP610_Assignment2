package Question4;

/**
 * A class that can be used to build paths in a maze and display it:
 * Note: Must require Room, Direction and Maze classes to work
 * Author: Seth Hall 2021 for DSA Assignment 2
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MazeMaker {

    private static final Random generator = new Random();
    private static int delay = 0;

    public static void createMazePathsInThread(Maze maze) {
        delay = 15;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int numRows = maze.getNumRows();
                int numCols = maze.getNumCols();
                int startRow = generator.nextInt(numRows);
                visitRoom(maze, startRow, 0);
                // randomly open one door along the eastern wall of maze
                int exitRow = generator.nextInt(numRows);
                maze.openDoor(exitRow, numCols - 1, Direction.EAST);
            }
        });
        t.start();
    }

    // prepares a maze of the specified direction that hsa a single
    // exit somewhere along the eastern wall
    public static void createMazePaths(Maze maze) {  // prepare a maze whose doors are all initially closed
        delay = 0;
        int numRows = maze.getNumRows();
        int numCols = maze.getNumCols();
        int startRow = generator.nextInt(numRows);
        visitRoom(maze, startRow, 0);
        // randomly open one door along the eastern wall of maze
        int exitRow = generator.nextInt(numRows);
        maze.openDoor(exitRow, numCols - 1, Direction.EAST);
    }

    // recursive helper method which uses a depth first search of maze
    // opening doors as it moves from room to room
    private static void visitRoom(Maze maze, int row, int col) {  // randomize the order in which directions will be moved
        List<Direction> directionList = new ArrayList<>(4);
        for (Direction direction : Direction.values()) {
            directionList.add(direction);
        }
        Collections.shuffle(directionList);
        Iterator<Direction> iterator = directionList.iterator();
        while (iterator.hasNext()) {
            Direction direction = iterator.next();
            // determine row and column of adjacent room
            int adjRow = row, adjCol = col;
            switch (direction) {
                case NORTH:
                    adjRow--;
                    break;
                case EAST:
                    adjCol++;
                    break;
                case SOUTH:
                    adjRow++;
                    break;
                case WEST:
                    adjCol--;
                    break;
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
            // determine whether the adjacent room should be visited
            if (maze.isInsideMaze(adjRow, adjCol)
                    && !maze.hasOpenDoor(adjRow, adjCol)) {
                maze.openDoor(row, col, direction);
                visitRoom(maze, adjRow, adjCol);
            }
        }
    }

    public static void main(String[] args) {
        MazeMaker maker = new MazeMaker();
        JFrame frame = new JFrame("Maze Maker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(maker.getDisplayPanel());
        frame.pack();
        // position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        frame.setVisible(true);
    }

    public JPanel getDisplayPanel() {
        return new MazeDisplayer();
    }

    public class MazeDisplayer extends JPanel implements ActionListener {

        private Maze maze;
        private final DrawPanel drawPanel;
        private final JButton performMazeAlg;
        private final Timer timer;

        public MazeDisplayer() {
            super(new BorderLayout());
            timer = new Timer(15, this);
            //create the mouses
            JPanel southPanel = new JPanel();
            performMazeAlg = new JButton("Build Paths");
            performMazeAlg.addActionListener((ActionListener) this);
            southPanel.add(performMazeAlg);
            super.add(southPanel, BorderLayout.SOUTH);
            drawPanel = new DrawPanel();
            super.add(drawPanel, BorderLayout.CENTER);
        }

        private class DrawPanel extends JPanel {

            public DrawPanel() {
                super();
                super.setBackground(Color.WHITE);
                super.setPreferredSize(new Dimension(600, 600));
            }

            //draws the maze and draws the different mouses in the maze
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (maze != null) {
                    maze.draw(g, getWidth() - 10, getHeight() - 10);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == performMazeAlg) {
                if (timer.isRunning()) {
                    timer.stop();
                }
                String vals = JOptionPane.showInputDialog("Enter dimension of maze as comma seperated values");
                String[] data = vals.split(",");
                if (data != null && data.length >= 2) {
                    maze = new Maze(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    MazeMaker.createMazePaths(maze);
                } else {
                    System.out.println("Not enough information provided!");
                }
                timer.start();
            }
            drawPanel.repaint();
        }
    }
}
