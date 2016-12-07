package mainclass;

import java.awt.Color;
import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Random;

public class ShapeComponent extends JComponent {

    static boolean isPressed = false;
    static ArrayList<Shape> shapes = new ArrayList<>();

    public ShapeComponent(ArrayList<Shape> shapes1) {
        shapes = shapes1;
    }

    @Override
    public void paintComponent(Graphics g) {
        Random rand = new Random();
        float r = rand.nextFloat();
        float ge = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, ge, b);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(randomColor);

        shapes.stream().map((shape) -> {
            g2.draw(shape);
            g2.fill(shape);
            return shape;
        }).map((_item) -> {
            g.setColor(randomColor.brighter());
            return _item;
        }).forEach((_item) -> {
            g.fillOval(150, 150, 200, 190);
        });

    }

}
