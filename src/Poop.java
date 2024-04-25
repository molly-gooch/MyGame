import java.awt.*;

public class Poop {
        //variables declaration section
        public int xpos;
        public int ypos;
        public int dx;
        public int dy;
        public int width;
        public int height;
        public boolean isAlive;
        public Rectangle rec;

        //constructor method
        public Poop(int pXpos, int pYpos, int pDx, int pDy, int pWidth, int pHeight){
            xpos=pXpos;
            ypos=pYpos;
            dx=pDx;
            dy=pDy;
            width=pWidth;
            height=pHeight;
            isAlive=true;
            rec = new Rectangle(xpos, ypos, width, height);
        }

        public void move(){
            ypos=ypos+dy;
            xpos=xpos+dx;
            //this updates the rectangle location
            rec = new Rectangle(xpos, ypos, width, height);
        }
    }

