import java.awt.*;

    public class Background {
        public String name;
        public int xpos;
        public int ypos;
        public int dx;
        public int dy;
        public int width;
        public int height;
        public boolean isAlive;
        public Rectangle rec;

        public Background(int pXpos, int pYpos, int pDx, int pDy, int pWidth, int pHeight){
            xpos=pXpos;
            ypos=pYpos;
            dx=pDx;
            dy=pDy;
            width=pWidth;
            height=pHeight;
            isAlive=true;
            rec = new Rectangle(xpos, ypos, width, height);
        }

        public void wrappingMove() {
            if (xpos < -980) {
                xpos =1000;
                System.out.println("city xpos: "+ xpos);
            }
            ypos=ypos+dy;
            xpos=xpos+dx;
            //this updates the rectangle location
            rec = new Rectangle(xpos, ypos, width, height);

        }

    }


