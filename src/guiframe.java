import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class guiframe implements ActionListener {
    JPanel game;
    JButton[][] fieldButtons;
    ImageIcon[] diceIcons = new ImageIcon[7];
    ImageIcon redScaled;
    ImageIcon yellowScaled;
    ImageIcon blackScaled;
    ImageIcon greenScaled;
    Logic lg = new Logic();

    public void showGui() {
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(800, 800);
        fr.setResizable(false);
        game = new JPanel() {
            // instance initializer
            {
                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                try {
                    Image imgs = ImageIO.read(new File("board.png")).getScaledInstance(game.getWidth(),
                            game.getHeight(), Image.SCALE_SMOOTH);
                    graphics.drawImage(imgs, 0, 0, this);
                    super.paintComponent(graphics);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        game.setLayout(new GridLayout(11, 11, 0, 0));
        Color cl = new Color(255, 255, 154);
        game.setBackground(cl);

        fieldButtons = new JButton[11][11];
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                fieldButtons[row][column] = new JButton();
                fieldButtons[row][column].setContentAreaFilled(false);
                fieldButtons[row][column].setBorderPainted(true);
                fieldButtons[row][column].setName(Integer.toString(column * 100 + row));
                fieldButtons[row][column].addActionListener(this);
                game.add(fieldButtons[row][column]);

            }
        }
        fr.getContentPane().add(game);
        fr.setVisible(true);
        setPieces();

    }

    public void actionPerformed(ActionEvent e) {
        String btn = ((Component) e.getSource()).getName();
        System.out.println("Button ID: " + btn);
        int btnValue = Integer.parseInt(btn);
        lg.logicSelector(btnValue);
        
        setPieces();
    }

    public void setPieces() {
        int temp[][] = lg.getArray();
        scaleAll();
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                int currentValue = temp[row][column];
                switch (currentValue) {
                    case 1:
                        fieldButtons[row][column].setIcon(yellowScaled);
                        break;
                    case 2:
                        fieldButtons[row][column].setIcon(greenScaled);
                        break;
                    case 3:
                        fieldButtons[row][column].setIcon(blackScaled);
                        break;
                    case 4:
                        fieldButtons[row][column].setIcon(redScaled);
                        break;
                    case -8:
                        fieldButtons[row][column].setIcon(diceIcons[0]);
                        break;
                    case 11:
                        fieldButtons[row][column].setIcon(diceIcons[1]);
                        break;
                    case 12:
                        fieldButtons[row][column].setIcon(diceIcons[2]);
                        break;
                    case 13:
                        fieldButtons[row][column].setIcon(diceIcons[3]);
                        break;
                    case 14:
                        fieldButtons[row][column].setIcon(diceIcons[4]);
                        break;
                    case 15:
                        fieldButtons[row][column].setIcon(diceIcons[5]);
                        break;
                    case 16:
                        fieldButtons[row][column].setIcon(diceIcons[6]);
                        break;
                    default:
                        fieldButtons[row][column].setIcon(null);
                        break;
                }
            }
        }
    }

    public void scaleAll() {
        try {
            redScaled = new ImageIcon(ImageIO.read(new File("red.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            yellowScaled = new ImageIcon(ImageIO.read(new File("yellow.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            greenScaled = new ImageIcon(ImageIO.read(new File("green.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            blackScaled = new ImageIcon(ImageIO.read(new File("black.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            diceIcons[0] = new ImageIcon(ImageIO.read(new File("X.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            diceIcons[1] = new ImageIcon(ImageIO.read(new File("D6-1.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            diceIcons[2] = new ImageIcon(ImageIO.read(new File("D6-2.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            diceIcons[3] = new ImageIcon(ImageIO.read(new File("D6-3.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            diceIcons[4] = new ImageIcon(ImageIO.read(new File("D6-4.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            diceIcons[5] = new ImageIcon(ImageIO.read(new File("D6-5.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));
            diceIcons[6] = new ImageIcon(ImageIO.read(new File("D6-6.png")).getScaledInstance(
                    fieldButtons[0][0].getWidth(), fieldButtons[0][0].getHeight(), Image.SCALE_DEFAULT));

        } catch (IOException e) {
            System.out.println("Image Scaled Failed");
            System.out.println(e);
        }
    }
}