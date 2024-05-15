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
    public boolean youWon = false;
    public boolean poopPressed=false;

    //Declare the objects used in the program below
    /** STEP 1: Declare your object and give it a name **/
    public Hero pigeon;
    public Background city;
    public Background city2;
    public People[] people;
    public ArrayList<Poop> poo;
    public int poopCounter=0;
    public Airplane[] planes;
    public int score;

    /** STEP 2: Declare an image for your object**/
    public Image City;
    public Image City2;
    public Image bird;
    public Image bird2;
    public Image pooing;
    public Image poop;
    public Image person;
    public Image plane;
    public Image end;
    public Image start;
    public Image won;

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
        people = new People[20];
        poo = new ArrayList<>();
        planes = new Airplane[20];


        /** STEP 4: Load in the image for your object **/
        bird = Toolkit.getDefaultToolkit().getImage("Bird.png");
        bird2 = Toolkit.getDefaultToolkit().getImage("Pigeon.png");
        pooing = Toolkit.getDefaultToolkit().getImage("PigeonPooping.png");
        City = Toolkit.getDefaultToolkit().getImage("City.jpg");
        City2 = Toolkit.getDefaultToolkit().getImage("City2.jpeg");
        poop = Toolkit.getDefaultToolkit().getImage("Poop.png");
        person = Toolkit.getDefaultToolkit().getImage("Person1.png");
        plane = Toolkit.getDefaultToolkit().getImage("Airplane.png");
        end = Toolkit.getDefaultToolkit().getImage("Dead.jpg");
        start = Toolkit.getDefaultToolkit().getImage("Start.png");
        won = Toolkit.getDefaultToolkit().getImage("Win.jpg");



        for(int x=0; x<people.length;x=x+1){
            randomNum=(int)(Math.random()*2000);
            people[x]=new People(randomNum, 520, randomNum/100, 0, 100, 150);
        }
        for(int x=0; x<people.length; x=x+1){
            people[x].isAlive=true;
        }

        for(int x=0; x<planes.length;x=x+1){
            randomNum=(int)(Math.random()*1000);
            planes[x]=new Airplane(randomNum, randomNum, randomNum/100, 0, 50, 75);
        }
        for(int i=0; i<planes.length; i=i+1){
            planes[i].isAlive=true;
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
            g.drawImage(start, 0, 0, city.width, city.height, null);
            //g.drawImage(backgroundPic, 0,0,WIDTH, HEIGHT, null);
        }

        if(isPlaying==true&&gameOver==false){
            //draw the image of your objects below:
            /** STEP 5: Draw the image of your object to the screen**/
            g.drawImage(City, city.xpos, city.ypos, city.width, city.height, null);
            g.drawImage(City2, city2.xpos, city2.ypos, city2.width, city2.height, null);
            g.drawString("People Hit:" + score,180,100);
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
            g.drawImage(end, 0, 0, city.width, city.height, null );
            g.drawString("Score: " + score, 100, 100);
        }

        if(startScreen==false&&gameOver==false) {
            for (int x = 0; x < people.length; x = x + 1) {
                if (people[x].isAlive == true) {
                    g.drawImage(person, people[x].xpos, people[x].ypos, people[x].width, people[x].height, null);
                }
            }

            for (int i = 0; i < planes.length; i = i + 1) {
                planes[i].isAlive = true;
                if (planes[i].isAlive == true) {
                    g.drawImage(plane, planes[i].xpos, planes[i].ypos, planes[i].width, planes[i].height, null);
                }
            }
        }

        if(youWon==true){
            g.drawImage(won, 0, 0, city.width, city.height, null);
        }



        //dispose the images each time(this allows for the illusion of movement).
        g.dispose();

        bufferStrategy.show();
    }
        public void movePoop(int counter){

//                if (poo.get(counter).isAlive == true) {
//                    poo.get(counter).move();
//            }
        }

    public void moveThings() {
        //call the move() method code from your object class
        if(pigeon.isAlive){
            pigeon.move();
        }
        city.wrappingMove();
        city2.wrappingMove();

        for (int x = 0; x < people.length; x = x + 1) {
            people[x].wrappingMove();
        }
        for(int i = 0; i < planes.length; i = i + 1){
            planes[i].wrappingMove();
        }
        if (!poo.isEmpty()) {
        for (int i = 0; i < poo.size(); i = i + 1) {
            poo.get(i).move();
        }
        }
    }

    public void collisions() {
        if (!poo.isEmpty()) {
            for (int i = 0; i < poo.size(); i = i + 1) {
                for (int x = 0; x < people.length; x = x + 1) {
                    if (poo.get(i).rec.intersects(people[x].rec) && people[x].isAlive==true) {
                        people[x].isAlive=false;
                        System.out.println("ouch");
                        score=score+1;
                    }

                }
            }
        }

        for (int i = 0; i < planes.length; i = i + 1) {
            if (planes[i].rec.intersects(pigeon.rec)) {
                pigeon.isAlive = false;
                gameOver=true;
            }
        }
        if(people[0].isAlive==false&&people[1].isAlive==false&&people[2].isAlive==false&&people[3].isAlive==false&&people[4].isAlive==false&&people[5].isAlive==false&&people[6].isAlive==false&&people[7].isAlive==false&&people[8].isAlive==false&&people[9].isAlive==false&&people[10].isAlive==false&&people[11].isAlive==false&&people[12].isAlive==false&&people[13].isAlive==false&&people[14].isAlive==false&&people[15].isAlive==false&&people[16].isAlive==false&&people[17].isAlive==false&&people[18].isAlive==false&&people[19].isAlive==false){
            youWon=true;
        }
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
        if(keyCode==39){
            pigeon.rightPressed=true;
        }
        if(keyCode==37){
            pigeon.leftPressed=true;
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
        if(keyCode==39){
            pigeon.rightPressed=false;
        }
        if(keyCode==37){
            pigeon.leftPressed=false;
        }

        if(keyCode==16){
           poopCounter++;

                poo.add(new Poop(pigeon.xpos + 20, pigeon.ypos + 10, 0, 10, 45, 45)) ;
               // System.out.println("we made poop " + poopCounter + "times");

        }
    }
}