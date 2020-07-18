import javax.swing.*;
import java.awt.*;

public class GameOfLifeGUI extends JFrame {
    //labels
    JLabel genLabel;
    JLabel aliveLabel;

    //buttons
    JToggleButton playPauseToggle;
    JButton reset;

    public GameOfLifeGUI() {
        //Creating the frame
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        startGame();
    }

    public void startGame() {
        //panel for labels on top
        JPanel topPan = new JPanel();

        //JLabels generation and alive labels
        genLabel = new JLabel();
        genLabel.setName("GenerationLabel");
        genLabel.setText("Generation #" + GameOfLife.gen);
        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        aliveLabel.setText("Alive: " + GameOfLife.cellsAlive);

        //adding labels to top panel
        topPan.add(genLabel);
        topPan.add(aliveLabel);
        topPan.setLayout(new BoxLayout(topPan, BoxLayout.Y_AXIS)); //setting box layout to stack labels vertical

        getContentPane().add(BorderLayout.NORTH, topPan); //adding label to top of frame using border layout
        setVisible(true);
    }

    public void printCurrentUniverse(char[][] cells) {
        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(cells.length, cells.length));

        for (char[] cell : cells) {
            for (int j = 0; j < cells.length; j++) {
                JButton btn = new JButton();
                if (cell[j] == 'O') {
                    btn.setBackground(Color.WHITE);
                    btn.setSize(1, 1);
                    btn.setEnabled(false);
                } else {
                    btn.setBackground(Color.BLACK);
                    btn.setSize(1, 1);
                    btn.setEnabled(false);
                    btn.setBorder(BorderFactory.createEmptyBorder());
                }
                cellPanel.add(btn);
            }
        }
        updateLabels();
        getContentPane().add(BorderLayout.CENTER, cellPanel);
        setVisible(true);
    }

    public void updateLabels() {
        genLabel.setText("Generation #" + GameOfLife.gen);
        aliveLabel.setText("Alive: " + GameOfLife.cellsAlive);
    }


    public static void main(String[] args) {

        GameOfLife life = new GameOfLife(60);
        life.initializeCurrentUniverse();

    }
}
