package laborai.gui.fx;

import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author darius
 */
public class PanelsFX extends GridPane {

    private final static int SPACING = 4;
    private final List<TextField> tfs = new ArrayList<>();
    private final List<Button> btns = new ArrayList<>();
    private List<String> tfTexts = new ArrayList<>();

    /**
     * Sukuriama parametrų lentelė (GridPane išdėstymo dėsnis)
     * <pre>
     * |-------------------------------|
     * |                |------------| |
     * |   lblTexts[0]  | tfTexts[0] | |
     * |                |------------| |
     * |                               |
     * |                |------------| |
     * |   lblTexts[1]  | tfTexts[1] | |
     * |                |------------| |
     * |      ...             ...      |
     * |-------------------------------|
     * </pre>
     *
     * @param lblTexts
     * @param tfTexts
     * @param columnWidth
     */
    public PanelsFX(String[] lblTexts, String[] tfTexts, int columnWidth) {
        super();
        if (lblTexts == null || tfTexts == null) {
            throw new IllegalArgumentException("Arguments for table of parameters are incorrect");
        }
        this.tfTexts = Arrays.stream(tfTexts).collect(Collectors.toList());//from array to list
        List<String> lblTextsList = Arrays.stream(lblTexts).collect(Collectors.toList());

        if (lblTextsList.size() > this.tfTexts.size()) {
            this.tfTexts = this.tfTexts.subList(0, lblTextsList.size());
            Collections.fill(this.tfTexts, "");
        }

        paneLayout();
        initTableOfParameters(columnWidth, lblTextsList);
    }

    /**
     * Sukuriamas mygtukų tinklelis (GridPane išdėstymo dėsnis)
     * <pre>
     * |-------------------------------------|
     * | |-------------| |-------------|     |
     * | | btnNames[0] | | btnNames[1] | ... |
     * | |-------------| |-------------|     |
     * |                                     |
     * | |-------------| |-------------|     |
     * | | btnNames[2] | | btnNames[3] | ... |
     * | |-------------| |-------------|     |
     * |       ...              ...          |
     * |-------------------------------------|
     * </pre>
     *
     * @param btnNames
     * @param gridX
     * @param gridY
     */
    public PanelsFX(String[] btnNames, int gridX, int gridY) {
        super();
        if (btnNames == null || gridX < 1 || gridY < 1) {
            throw new IllegalArgumentException("Arguments for buttons grid are incorrect");
        }
        paneLayout();
        initGridOfButtons(gridX, gridY, Arrays.stream(btnNames).collect(Collectors.toList()));
    }

    private void paneLayout() {
        setAlignment(Pos.CENTER);
        setHgap(SPACING);
        setVgap(SPACING);
        setPadding(new Insets(SPACING));
    }

    private void initTableOfParameters(int columnWidth, List<String> lblTexts) {
        for (int i = 0; i < lblTexts.size(); i++) {
            add(new Label(lblTexts.get(i)), 0, i);
        }

        for (int i = 0; i < tfTexts.size(); i++) {
            TextField tf = new TextField(tfTexts.get(i));
            tf.setPrefWidth(columnWidth);
            tf.setAlignment(Pos.CENTER);
            tfs.add(tf);
            add(tf, 1, i);
        }
    }

    private void initGridOfButtons(int gridX, int gridY, List<String> btnNames) {
        int nameIndex = 0;
        for (int i = 0; i < gridY; i++) {
            for (int j = 0; j < gridX; j++) {
                if (nameIndex >= btnNames.size()) {
                    break;
                }
                Button button = new Button(btnNames.get(nameIndex));
                btns.add(button);
                button.setMaxWidth(Double.MAX_VALUE);
                add(button, j, i);
                nameIndex++;
            }
        }
    }

    /**
     * Gražinamas lentelės parametrų sąrašas
     *
     * @return Gražinamas lentelės parametrų sąrašas
     */
    public List<String> getParametersOfTable() {
        tfTexts.clear();
        tfs.forEach((tf) -> {
            tfTexts.add(tf.getText());
        });
        return tfTexts;
    }

    /**
     * Gražinamas parametrų lentelės TextField objektų sąrašas
     *
     * @return Gražinamas parametrų lentelės TextField objektų sąrašas
     */
    public List<TextField> getTfOfTable() {
        return tfs;
    }

    /**
     * Gražinamas mygtukų tinklelio Button objektų sąrašas
     *
     * @return Gražinamas mygtukų tinklelio Button objektų sąrašas
     */
    public List<Button> getButtons() {
        return btns;
    }
}
