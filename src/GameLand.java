//Game Example
//Lockwood Version 2023-24
// Learning goals:
// make an object class to go with this main class
// the object class should have a printInfo()
//input picture for background
//input picture for object and paint the object on the screen at a given point
//create move method for the object, and use it
// create a wrapping move method and a bouncing move method
//create a second object class
//give objects rectangles
//start interactions/collisions

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable, KeyListener {

    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public int randomNum;

    public BufferStrategy bufferStrategy;

    //declare screen/level booleans
    public boolean startScreen=true;
    public boolean isPlaying=false;
    public boolean gameOver=false;
    public boolean poopPressed=false;

    //Declare the objects used in the program below
    /** STEP 1: Declare your object and give it a name **/
    public Hero pigeon;
    public Background city;
    public Background city2;
    public People[] people;
    public ArrayList<Poop> poo;
    public int poopCounter=0;

    /** STEP 2: Declare an image for your object**/
    public Image City;
    public Image City2;
    public Image bird;
    public Image bird2;
    public Image pooing;
    public Image poop;
    public Image person;

    // Main method definition: PSVM
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        GameLand ex = new GameLand();   //creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    // This has no return type and has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public GameLand() {
        setUpGraphics(); //this calls the setUpGraphics() method

        //create (construct) the objects needed for the game below
        //for each object that has a picture, load in images as well
        /** STEP 3: Construct a specific Hero **/
        pigeon = new Hero(400, 100, 1, 1, 120, 100);
        city = new Background(0, 0, -4, 0, 1002, 700);
        city2 = new Background(1000, 0, -4, 0, 1000, 700);
        people = new People[8];
        poo = new ArrayList<>();


        /** STEP 4: Load in the image for your object **/
        bird = Toolkit.getDefaultToolkit().getImage("Bird.png");
        bird2 = Toolkit.getDefaultToolkit().getImage("Pigeon.png");
        pooing = Toolkit.getDefaultToolkit().getImage("PigeonPooping.png");
        City = Toolkit.getDefaultToolkit().getImage("City.jpg");
        City2 = Toolkit.getDefaultToolkit().getImage("City2.jpeg");
        poop = Toolkit.getDefaultToolkit().getImage("Poop.png");
        person = Toolkit.getDefaultToolkit().getImage("Person1.png");


        for(int x=0; x<people.length;x=x+1){
            randomNum=(int)(Math.random()*1000);
            people[x]=new People(randomNum, 520, randomNum/100, 0, 100, 150);
        }
        for(int i=0; i<people.length; i=i+1){
            people[i].isAlive=true;
        }

    }// GameLand()

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever using a while loop
        while (true) {
            moveThings();  //move all the game objects
            collisions(); //checks for rectangle intersections
            render();  // paint the graphics
            pause(20); // sleep for 20 ms
        }
    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if(startScreen==true) {
            g.drawString("PRESS SPACEBAR TO START", 400, 300);
            //g.drawImage(backgroundPic, 0,0,WIDTH, HEIGHT, null);
        }

        if(isPlaying==true){
            //draw the image of your objects below:
            /** STEP 5: Draw the image of your object to the screen**/

            g.drawImage(City, city.xpos, city.ypos, city.width, city.height, null);
            g.drawImage(City2, city2.xpos, city2.ypos, city2.width, city2.height, null);

                for(int x=0; x<poo.size();x=x+1) {
                    if(poo.get(x).isAlive = true) {
                        g.drawImage(poop, poo.get(x).xpos, poo.get(x).ypos, poo.get(x).width, poo.get(x).height, null);
                    }
                }
            if (pigeon.isAlive == true) {
                g.drawImage(bird2, pigeon.xpos, pigeon.ypos, pigeon.width, pigeon.height, null);
            }

        }


        if(gameOver==true){
            //paint game over image to the screen
            //g.drawImage(backgroundPic, 0,0,WIDTH, HEIGHT, null);
            g.drawString("GAME OVER", 400, 300);
        }

        if(startScreen==false) {
            for (int i = 0; i < people.length; i = i + 1) {
                people[i].isAlive = true;
                if (people[i].isAlive == true) {
                    g.drawImage(person, people[i].xpos, people[i].ypos, people[i].width, people[i].height, null);
                }
            }
        }



        //dispose the images each time(this allows for the illusion of movement).
        g.dispose();

        bufferStrategy.show();
    }
        public void movePoop(int counter){

                if (poo.get(counter).isAlive == true) {
                    poo.get(counter).move();
            }
        }

    public void moveThings() {
        //call the move() method code from your object class
        if(pigeon.isAlive){
            pigeon.move();
        }
        city.wrappingMove();
        city2.wrappingMove();

        for (int i = 0; i < people.length; i = i + 1) {
            people[i].wrappingMove();
        }
        for (int i=0; i<=poopCounter; i=i+1){
            movePoop(poopCounter);
        }
    }

    public void collisions(){

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addKeyListener(this);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //probably will stay empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode=e.getKeyCode();
        System.out.println("Key: " + key+ ", KeyCode: " + keyCode);
        if(keyCode==38){//38 is up
            pigeon.upPressed=true;
        }
        if(keyCode==40){//40 is down
            pigeon.downPressed=true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode=e.getKeyCode();

        //spacebar start
        if(keyCode==32){
            startScreen=false;
            isPlaying=true;
        }

        //user control
        if(keyCode==38){//38 is up
            pigeon.upPressed=false;
        }
        if(keyCode==40){//40 is down
            pigeon.downPressed=false;
        }

        if(keyCode==16){
            poopCounter++;
            for (int i=0;i<poopCounter;i=i+1) {
                poo.add(new Poop(pigeon.xpos + 20, pigeon.ypos + 10, 0, 10, 45, 45)) ;
            }

        }
    }
}