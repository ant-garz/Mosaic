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
    private Boolean shapeRand;
    private String shapeCode;

    Tile() {
        super();
        SetRandomValue();
    }

    //ToDo new constructor for Tile Class

    final public void SetRandomValue() {
        rValue = GetNumberBetween(0,255);
        gValue = GetNumberBetween(0,255);
        bValue = GetNumberBetween(0,255);

        //next two lines create a new random value for purpose of randomly selecting a character from A-Z and assigning it to the "alphaChar" var
        Random randValue = new Random();
        char randCharacter = (char) (randValue.nextInt(26) + 'a');
        alphaChar = Character.toString(randCharacter);

        //randomize shape
        if(GetNumberBetween(0,1) ==1){
            shapeRand = true;
            setShape("Circle");
        }
        else{
            shapeRand = false;
            setShape("Square");
        }
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        graphic.setColor(new Color(rValue,gValue,bValue));
        
        //if statement to see if we keep shape as rectangle or if we swap out for a circle
        if(shapeRand == true ) {
            graphic.fillOval(7, 5, panelWidth - 25, panelHeight - 5);
        }
        else{
            graphic.fillRect(5, 5, panelWidth, panelHeight);
        }

        graphic.setColor(new Color(GetContrastingColor(rValue), GetContrastingColor(gValue), GetContrastingColor(bValue)));

        graphic.setFont(new Font("TimesNewRoman", Font.PLAIN, 30));
        int stringX = (panelWidth/2) - 15;
        int stringY = (panelHeight/2) + 15;

        graphic.drawString(alphaChar, stringX, stringY);
    }

    //method written to get contrast color of whatever random we have so that the characters on the squares or rectangles are visible.
    private static int GetContrastingColor(int colorIn) {
        return ((colorIn+128)%256);
    }
    
    //call this method to get # between min param in and max param in. Saves time to call this than to write out several lines to do same thing each time.
    private static int GetNumberBetween(int minValue, int maxValue) {
        Random myRandomNum = new Random();
        return minValue + myRandomNum.nextInt(maxValue-minValue+1);
    }

    //setters + getters for color values, shape and alpha char
    //setter for rValue
    public final void setrValue (int rValueIn){
        rValue = rValueIn;
    }
    //getter for rValue
    public int getrValue (){
        return rValue;
    }
    //setter for gValue
    public final void setgValue(int gValueIn){
        gValue = gValueIn;
    }
    //getter for gValue
    public int getgValue(){
        return gValue;
    } 
    //setter for bValue
    public final void setbValue(int bValueIn){
        bValue = bValueIn;
    }
    //getter for bValue
    public int getbValue(){
        return bValue;
    }
    //setter for shapeCode
    public final void setShape(String shapeIn){
        shapeCode = shapeIn;
    }
    public String getShape(){
        return shapeCode;
    }
    //setter for alphaChar
    public final void setAlphaChar(String alphaCharIn){
        alphaChar = alphaCharIn;
    }
    //getter for alphaChar
    public String getAlphaChar(){
        return alphaChar;
    }

    public String toString(){
            String superString = super.toString();
            return String.format("--------------------New Tile Created!--------------------\nAttributes of Tile\nshapeCode: %s alphaChar: %s rValue: %d gValue: %d bValue: %d",getShape(), getAlphaChar(), getrValue(), getgValue(), getbValue());
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
            System.out.println(cTile.toString());
        }
    }

    //might reimplement as stand-alone class if it makes it easier for rest of project
    //event listener to repaint and shuffle up all content in gridPanel
    public void actionPerformed(ActionEvent e) {
        for(Tile cTile : tilesArray) {
            System.out.println(cTile.toString());
        }
        repaint();
    }
}

public class Mosaic {
    public static void main(String [] args) {
        System.out.println("Start Paint***\n");

        TileFrame myMosaicFrame = new TileFrame();
        myMosaicFrame.setVisible(true);
    }
}