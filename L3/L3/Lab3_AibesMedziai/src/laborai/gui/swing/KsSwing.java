package laborai.gui.swing;

import java.awt.Color;
import javax.swing.JTextArea;

/**
 * Klasė, skirta duomenų išvedimui į GUI
 *
 * @author darius
 */
public class KsSwing {

    private static int lineNr;
    private static boolean formatStartOfLine = true;

    public static void setFormatStartOfLine(boolean formatStartOfLine) {
        KsSwing.formatStartOfLine = formatStartOfLine;
    }

    private static String getStartOfLine() {
        return (formatStartOfLine) ? ++lineNr + "| " : "";
    }

    public static void oun(JTextArea ta, Object o) {
        if (o instanceof Iterable) {
            for (Object iter : (Iterable) o) {
                ta.append(iter.toString() + System.lineSeparator());
            }
            ta.append(System.lineSeparator());
        } else {
            ta.append(o.toString() + System.lineSeparator());
        }
    }

    public static void ou(JTextArea ta, Object o) {
        if (o instanceof Iterable) {
            for (Object iter : (Iterable) o) {
                ta.append(iter.toString() + System.lineSeparator());
            }
        } else {
            ta.append(o.toString());
        }
    }

    public static void ou(JTextArea ta, Object o, String msg) {
        ta.append(getStartOfLine() + msg + ": ");
        oun(ta, o);
    }

    public static void oun(JTextArea ta, Object o, String msg) {
        ta.append(getStartOfLine() + msg + ": " + System.lineSeparator());
        oun(ta, o);
    }

    public static void ounerr(JTextArea ta, Exception e) {
        ta.setBackground(Color.pink);
        ta.append(getStartOfLine() + e.toString() + System.lineSeparator());
        for (StackTraceElement ste : e.getStackTrace()) {
            ta.append(ste.toString() + System.lineSeparator());
        }
        ta.append(System.lineSeparator());
    }

    public static void ounerr(JTextArea ta, String msg) {
        ta.setBackground(Color.pink);
        ta.append(getStartOfLine() + msg + ". " + System.lineSeparator());
    }

    public static void ounerr(JTextArea ta, String msg, String parameter) {
        ta.setBackground(Color.pink);
        ta.append(getStartOfLine() + msg + ": " + parameter + System.lineSeparator());
    }
}