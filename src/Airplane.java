import java.awt.*;

public class Airplane {
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
        public Airplane(int pXpos, int pYpos, int pDx, int pDy, int pWidth, int pHeight){
            xpos=pXpos;
            ypos=pYpos;
            dx=pDx;
            dy=pDy;
            width=pWidth;
            height=pHeight;
            isAlive=true;
            rec = new Rectangle(xpos, ypos, width/10, height/10);
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
            ypos=ypos-dy;
            xpos=xpos-dx;
            //this updates the rectangle location
            rec = new Rectangle(xpos, ypos, width, height);
        }
    }


