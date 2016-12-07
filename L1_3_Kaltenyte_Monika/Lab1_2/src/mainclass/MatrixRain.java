
package mainclass;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;

public class MatrixRain extends JPanel{

    int FONTSIZE = 32, SCREENSIZE = 700;
    CharsThread[] thArr = new CharsThread[SCREENSIZE/FONTSIZE];
    MatrixRain(){
        for (int i = 0; i < thArr.length; i++) {
            thArr[i] = new CharsThread(i*FONTSIZE);
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g.fillRect(0, 0, SCREENSIZE, SCREENSIZE);
        g.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.BOLD, FONTSIZE);
        g2.setFont(font);
        for (int i = 0; i < thArr.length; i++) {
            if(thArr[i].y > 700){
                thArr[i] = new CharsThread(i*FONTSIZE);
            }
            drawThread(g2,thArr[i]);
        }

        try{Thread.sleep(20);}catch(Exception ex){}

        repaint();
    }
    public void drawThread(Graphics2D g2,CharsThread th){
        Random rand = new Random();
        float r = rand.nextFloat();
        float ge = rand.nextFloat();
        float b = rand.nextFloat();
        Color rC = new Color(r, ge, b);
        int fontsize = g2.getFont().getSize();
        for (int i = 0; i < th.len; i++) {
            if(th.randInt(0, th.len) == i)
                th.chArr[i][0] = th.randChar();
            if(i == th.len-1)
                g2.setColor(Color.WHITE);
            else
                g2.setColor(rC);
            g2.drawChars(th.chArr[i] ,0 ,1 ,th.x , th.y + (i*fontsize));
        }
        th.y+=th.vel;
    }

}
