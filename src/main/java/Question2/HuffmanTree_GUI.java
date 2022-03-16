package Question2;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
   Author: Seth Hall 2021 for DSA Assignment 2
 */

import Question2.HuffmanEncoder;
import Question2.HuffmanNode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;


public class HuffmanTree_GUI extends JPanel implements ActionListener {

    private final JButton decodeButton, encodeButton;

    private final DrawPanel drawPanel;
    private HuffmanNode root;
    private int numberNodes = 0;

    private final JTextArea ioTextField;
    public static int PANEL_H = 5000;
    public static int PANEL_W = 5000;
    private JLabel nodeCounterLabel;
    private final int BOX_SIZE = 40;
    private JScrollPane scroller;

    public HuffmanTree_GUI() {
        super(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        root = null;
        super.setPreferredSize(new Dimension(500, 500));
        JPanel buttonPanel = new JPanel();
        drawPanel = new DrawPanel();

        decodeButton = new JButton("Decode From Huffman");
        decodeButton.setEnabled(false);
        encodeButton = new JButton("Encode to Huffman");

        decodeButton.addActionListener((ActionListener) this);
        encodeButton.addActionListener((ActionListener) this);

        ioTextField = new JTextArea(5,120);
        ioTextField.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) ioTextField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);

        buttonPanel.add(ioTextField);
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);

        ioTextField.setBorder(BorderFactory.createTitledBorder("Input/Output Huffman string"));
        super.add(buttonPanel,BorderLayout.NORTH);
        super.add(new JScrollPane(drawPanel), BorderLayout.CENTER);
        super.add(new JScrollPane(ioTextField), BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == decodeButton) {   
            String decoded = HuffmanEncoder.huffmanDecode(root,ioTextField.getText());
            
            ioTextField.setText(decoded);
            decodeButton.setEnabled(false);
            encodeButton.setEnabled(true);

        } if (source == encodeButton && ioTextField.getText() != null) {
         
            String input = ioTextField.getText();
            Map<Character,Integer> map = HuffmanEncoder.countCharacterInstances(input.toCharArray());
        
            root = HuffmanEncoder.buildHuffmanTree(map);
            
            Map<Character,String> codes = HuffmanEncoder.extractCodeMap(root);

            String encoding = HuffmanEncoder.huffmanEncode(input,codes);
            ioTextField.setText(encoding);
            
            System.out.println("Theoretical original size in bytes = "+input.length()*2);
            System.out.println("Compressed now to size "+(encoding.length()/8));
            decodeButton.setEnabled(true);
            encodeButton.setEnabled(false);
        }
        drawPanel.repaint();
    }

    private class DrawPanel extends JPanel {

        public DrawPanel() {
            super();
            super.setBackground(Color.WHITE);
            super.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root != null) {
                drawTree(g, getWidth());
            }
        }

        public void drawTree(Graphics g, int width) {
            drawNode(g, root, BOX_SIZE, 0, 0, new IdentityHashMap<>());
        }

        private int drawNode(Graphics g, HuffmanNode current,
                int x, int level, int nodeCount, Map<HuffmanNode, Point> map) 
        {   
            HuffmanNode leftChild = current.getLeftChild();
            if (leftChild!= null) {
                nodeCount = drawNode(g, leftChild, x, level + 1, nodeCount, map);
            }

            int currentX = x + nodeCount * BOX_SIZE;
            int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
            nodeCount++;
            map.put(current, new Point(currentX, currentY));

            HuffmanNode rightChild = current.getRightChild();
            if (rightChild!= null) {
                nodeCount = drawNode(g, rightChild, x, level + 1, nodeCount, map);
            }

            g.setColor(Color.red);
            if (leftChild != null) {
                Point leftPoint = map.get(leftChild);
                g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
            }
            if (rightChild != null) {
                Point rightPoint = map.get(rightChild);
                g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);

            }
            if (current.isLeaf()) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.YELLOW);
            }

            Point currentPoint = map.get(current);
            g.fillRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
            Font f = new Font("courier new", Font.BOLD, 16);
            g.setFont(f);
            String symbol = "";
            if(current.getSymbol() != null)
                symbol += current.getSymbol();
            g.drawString(symbol+":"+current.getFrequency(), currentPoint.x - BOX_SIZE/2 + 2, currentPoint.y);
            
            return nodeCount;
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Huffman Tree Encoding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HuffmanTree_GUI());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack();            
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }
}
