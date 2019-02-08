/*
Name: Anthony Garza
Project: Mosaic
Purpose: To create a colored mosaic that shows a random pattern of colored circles
         and and squares with a random letter in the middle of the shape. When the "Randomize"
         button is pressed, the tiles need to change at random.
*/
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;
import java.util.Random;

class Tile extends JPanel {
    private int rValue,gValue,bValue;
    private String alphaChar;

    Tile() {
        super();
        SetRandomValue();
    }

    final public void SetRandomValue() {
        rValue = GetNumberBetween(0,255);
        gValue = GetNumberBetween(0,255);
        bValue = GetNumberBetween(0,255);

        //next two lines create a new random value for purpose of randomly selecting a character from A-Z and assigning it to the "alphaChar" var
        Random randValue = new Random();
        char randCharacter = (char) (randValue.nextInt(26) + 'a');
        alphaChar = Character.toString(randCharacter);
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        graphic.setColor(new Color(rValue,gValue,bValue));
        
        //if statement to see if we keep shape as rectangle or if we swap out for a circle
        if(GetNumberBetween(0,1) ==1 ) {
            graphic.fillOval(7, 5, panelWidth - 25, panelHeight - 5);
        }
        else{
            graphic.fillRect(5, 5, panelWidth, panelHeight);
        }

        graphic.setColor(new Color(GetContrastingColor(rValue), GetContrastingColor(gValue), GetContrastingColor(bValue)));

        graphic.setFont(new Font("TimesNewRoman", Font.PLAIN, 30));
        int stringX = (panelWidth/2) - 15;
        int stringY = (panelHeight/2) + 15;

        //toDo - if statement to swap up if we do rand alpha chars or numbers
        graphic.drawString(alphaChar, stringX, stringY);
    }

    private static int GetContrastingColor(int colorIn) {
        return ((colorIn+128)%256);
    }
    
    private static int GetNumberBetween(int min, int max) {
        Random myRandom = new Random();
        return min + myRandom.nextInt(max-min+1);
    }
    
}

class TileFrame extends JFrame implements ActionListener {
    private ArrayList<Tile> tilesArray;

    public TileFrame() {
        setBounds(100,100,1000,620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create container to hold all content - border layout on bottom and gridlayout with mosaic inside center of border layout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //create button panel add it to bottom of content pane (south)
        JPanel btnPanel = new JPanel();
        contentPane.add(btnPanel, BorderLayout.SOUTH);
        JButton randomizeButton = new JButton("Randomize!");
        btnPanel.add(randomizeButton);
        randomizeButton.addActionListener(this);

        //create the grid that the tiles go into
        JPanel gridPanel = new JPanel();
        contentPane.add(gridPanel, BorderLayout.CENTER);
        gridPanel.setLayout(new GridLayout(12,12));

        //array + for loop to populate each cell in grid with random content
        tilesArray = new ArrayList<Tile>();
        for (int i = 0; i < 144; i++) {
            Tile cTile = new Tile();
            gridPanel.add(cTile);
            tilesArray.add(cTile);
        }

    }

    //event listener to repaint and shuffle up all content in gridPanel
    public void actionPerformed(ActionEvent e) {
        for(Tile cTile : tilesArray) {
            cTile.SetRandomValue();
        }
        repaint();
    }
}

public class Mosaic {
    public static void main(String [] args) {
        System.out.println("Mosaic starting...");

        TileFrame myMosaicFrame = new TileFrame();
        myMosaicFrame.setVisible(true);
    }
}