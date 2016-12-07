package laborai.gui.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

/**
 * Klasės objektu galima sukurti du panelius: parametrų lentelę ir mygtukų
 * tinklelį. Panelyje talpinamų objektų kiekis nustatomas parametrais.
 *
 * @author darius.matulis@ktu.lt
 */
public class Panels extends JPanel {

    private final static int SPACING = 4;
    private final List<JTextField> tfs = new ArrayList<>();
    private final List<JButton> btns = new ArrayList<>();
    private List<String> tfTexts = new ArrayList<>();

    /**
     * Sukuriama parametrų lentelė (GridBag išdėstymo dėsnis)
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
    public Panels(String[] lblTexts, String[] tfTexts, int columnWidth) {
        super();
        if (lblTexts == null || tfTexts == null) {
            throw new IllegalArgumentException("Arguments for table of parameters are incorrect");
        }

        this.tfTexts = Arrays.stream(tfTexts).collect(Collectors.toList());
        List<String> lblTextsList = Arrays.stream(lblTexts).collect(Collectors.toList());

        if (lblTextsList.size() > this.tfTexts.size()) {
            this.tfTexts = this.tfTexts.subList(0, lblTextsList.size());
            Collections.fill(this.tfTexts, "");
        }

        initTableOfParameters(columnWidth, lblTextsList);
        // Tokia pati parametrų lentelė, padaryta naudojant SpringLayout išdėstymo dėsnį
        //initTableOfParametersWithSpringLayout(columnWidth, lblTextsList);
    }

    /**
     * Sukuriamas mygtukų tinklelis (GridLayout išdėstymo dėsnis)
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
    public Panels(String[] btnNames, int gridX, int gridY) {
        super();
        if (btnNames == null || gridX < 1 || gridY < 1) {
            throw new IllegalArgumentException("Arguments for buttons grid are incorrect");
        }
        initGridOfButtons(gridX, gridY, Arrays.stream(btnNames).collect(Collectors.toList()));
    }

    private void initTableOfParameters(int columnWidth, List<String> lblTexts) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Spacing'as tarp komponentų
        c.insets = new Insets(SPACING, SPACING, SPACING, SPACING);
        // Lygiavimas į kairę
        c.anchor = GridBagConstraints.WEST;
        // Pasirenkamas pirmas stulpelis..
        c.gridx = 0;
        // ..ir į jį sudedami labeliai
        lblTexts.forEach((lblText) -> {
            add(new JLabel(lblText), c);
        });
        // Pasirenkamas antras stulpelis..
        c.gridx = 1;
        // ..ir į jį sudedami textfieldai

        for (String tfText : tfTexts) {
            JTextField tf = new JTextField(tfText, columnWidth);
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.setBackground(Color.WHITE);
            tfs.add(tf);
            add(tf, c);
        }
    }

    // Kam įdomu: tokia pati parametrų lentelė, padaryta naudojant SpringLayout išdėstymo dėsnį
    private void initTableOfParametersWithSpringLayout(int columnWidth, List<String> lblTexts) {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        Spring panelWidth = layout.getConstraint(SpringLayout.EAST, this);
        JLabel[] lbls = new JLabel[lblTexts.size()];
        Spring maxLabel = Spring.constant(0);
        for (int i = 0; i < lblTexts.size(); i++) {
            lbls[i] = new JLabel(lblTexts.get(i));
            SpringLayout.Constraints con = layout.getConstraints(lbls[i]);
            maxLabel = Spring.max(maxLabel, con.getWidth());
            add(lbls[i]);
            JTextField tf = new JTextField(tfTexts.get(i), columnWidth);
            tf.setHorizontalAlignment(JTextField.CENTER);
            tfs.add(tf);
            add(tf);
        }

        SpringLayout.Constraints textFieldCons = null;
        for (int i = 0; i < lblTexts.size(); i++) {
            SpringLayout.Constraints labelCons = layout.getConstraints(lbls[i]);
            textFieldCons = layout.getConstraints(tfs.get(i));
            int pad = (textFieldCons.getHeight().getValue() - labelCons.getHeight().getValue()) / 2;

            labelCons.setX(Spring.constant(SPACING));
            textFieldCons.setX(Spring.sum(Spring.constant(SPACING * 2), maxLabel));
            if (i == 0) {
                labelCons.setY(Spring.constant(SPACING + pad));
                textFieldCons.setY(Spring.constant(SPACING));
            } else {
                SpringLayout.Constraints tfConstraintsLast = layout.getConstraints(tfs.get(i - 1));
                labelCons.setY(Spring.sum(Spring.constant(SPACING + pad), tfConstraintsLast.getConstraint(SpringLayout.SOUTH)));
                textFieldCons.setY(Spring.sum(Spring.constant(SPACING), tfConstraintsLast.getConstraint(SpringLayout.SOUTH)));
            }
            if (i != tfs.size() - 1) {
                int reiksme = maxLabel.getValue();
                textFieldCons.setWidth(Spring.sum(Spring.constant(-reiksme - (3 * SPACING)), panelWidth));
            }
        }
        if (textFieldCons != null) {
            SpringLayout.Constraints consParent = layout.getConstraints(this);
            consParent.setConstraint(SpringLayout.EAST,
                    Spring.sum(Spring.constant(SPACING), textFieldCons.getConstraint(SpringLayout.EAST)));
            consParent.setConstraint(SpringLayout.SOUTH,
                    Spring.sum(Spring.constant(SPACING), textFieldCons.getConstraint(SpringLayout.SOUTH)));
        }
    }

    private void initGridOfButtons(int gridX, int gridY, List<String> btnNames) {
        setLayout(new GridLayout(gridY, gridX, SPACING, SPACING));
        int nameIndex = 0;
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                if (nameIndex >= btnNames.size()) {
                    break;
                }
                JButton button = new JButton(btnNames.get(nameIndex));
                btns.add(button);
                add(button);
                nameIndex++;
            }
        }
    }

    /**
     * Gražinamas parametrų lentelės parametrų sąrašas
     *
     * @return Gražinamas parametrų lentelės parametrų sąrašas
     */
    public List<String> getParametersOfTable() {
        tfTexts.clear();
        tfs.forEach((tf) -> {
            tfTexts.add(tf.getText());
        });
        return tfTexts;
    }

    /**
     * Gražinamas parametrų lentelės JTextField objektų sąrašas
     *
     * @return Gražinamas parametrų lentelės JTextField objektų sąrašas
     */
    public List<JTextField> getTfOfTable() {
        return tfs;
    }

    /**
     * Gražinamas mygtukų tinklelio JButton objektų sąrašas
     *
     * @return Gražinamas mygtukų tinklelio JButton objektų sąrašas
     */
    public List<JButton> getButtons() {
        return btns;
    }
}
