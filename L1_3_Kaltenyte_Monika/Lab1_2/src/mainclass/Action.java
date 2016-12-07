package mainclass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Action {

    static boolean isPressed = false;
    static ArrayList<Shape> shapes = new ArrayList<>();
    static int counter = 0;

    public void play() {
        final JFrame frame = new JFrame();
        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGHT = 600;

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Figures");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);
        final JButton btnRectangle = new JButton("START!");
        panel.add(btnRectangle);

        class RectangleButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                isPressed = true;
            }
        }

        ActionListener rectButtonListener = new RectangleButtonListener();
        btnRectangle.addActionListener(rectButtonListener);

        final JComponent component = new ShapeComponent(shapes);
        frame.add(component);

        class MousePressListener implements MouseListener {

            @Override
            public void mousePressed(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                if (isPressed) {
                    Rectangle rnew = new Rectangle(x, y, 20, 20);
                    counter++;
                    shapes.add(rnew);
                    component.repaint();
                }
                String str = String.valueOf(counter);
                final JButton but = new JButton(str);
                but.setSize(200, 200);
                but.setBackground(Color.MAGENTA);
                but.setForeground(Color.black);
                but.setVisible(false);
                but.repaint();
                but.setFont(new Font("Arial", Font.PLAIN, 20));
                
                panel.add(but);
                but.setVisible(true);
                if (counter > 9) {
                    but.setVisible(false);
                    frame.setVisible(false);
                    JFrame jf = new JFrame("Matrix raining code");
                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jf.setSize(700, 700);
                    jf.setResizable(false);
                    jf.add(new MatrixRain());
                    jf.setVisible(true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
            }

            @Override
            public void mouseClicked(MouseEvent event) {
            }

            @Override
            public void mouseEntered(MouseEvent event) {
            }

            @Override
            public void mouseExited(MouseEvent event) {
            }
        }

        MousePressListener mListener = new MousePressListener();
        frame.addMouseListener(mListener);
        frame.setVisible(true);
    }

}
