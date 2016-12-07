/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kaltenyte;
/**
 * @author Monika Kaltenyte
 */
import laborai.gui.swing.Lab3Window;
import java.util.Locale;

/*
 * Darbo atlikimo tvarka - čia yra pradinė klasė.
 */
public class VykdymoModulis {

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        KnyguTestai.aibėsTestas();
        Lab3Window.createAndShowGUI();
    }
}

