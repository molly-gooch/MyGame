import java.awt.*;

public class Hero {
    //variables declaration section
    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle rec;

    //movement booleans
    public boolean rightPressed;
    public boolean leftPressed;
    public boolean upPressed;
    public boolean downPressed;

    //constructor method
    public Hero(int pXpos, int pYpos, int pDx, int pDy, int pWidth, int pHeight){
        xpos=pXpos;
        ypos=pYpos;
        dx=pDx;
        dy=pDy;
        width=pWidth;
        height=pHeight;
        isAlive=true;
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void move(){//this is user control move method
        //horizontal movement
        if(rightPressed==true){
            dx=2;
        }else if(leftPressed==true){
            dx=-2;
        }else{
            dx=0;
        }
        //vertical
        if(upPressed==true){
            dy=-4;
        }else if(downPressed==true){
            dy=4;
        }else{
            dy=0;
        }

        ypos=ypos+dy;
        xpos=xpos+dx;

        rec = new Rectangle(xpos, ypos, width, height);


    }

    public void wrappingMove(){
        if(xpos>1000){
            xpos=0;
        }
        if(ypos>700){
            ypos=0;
        }
        if(ypos<0){
            ypos=700;
        }
        if(xpos<0){
            xpos=1000;
        }
        ypos=ypos+dy;
        xpos=xpos+dx;
        //this updates the rectangle location
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void bouncingMove(){
        if(xpos>1000-width){
            dx=-dx;
        }
        if(ypos>700-height){
            dy=-dy;
        }
        if(xpos<0){
            dx=-dx;
        }
        if(ypos<0){
            dy=-dy;
        }
        ypos=ypos+dy;
        xpos=xpos+dx;
        rec = new Rectangle(xpos, ypos, width, height);

    }

    public void printInfo(){
        System.out.println("(x,y): ("+xpos+", "+ypos+")");
        System.out.println("X speed: " + dx);
        System.out.println("Y speed: " + dy);
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        System.out.println("isAlive: " + isAlive);
    }


}
