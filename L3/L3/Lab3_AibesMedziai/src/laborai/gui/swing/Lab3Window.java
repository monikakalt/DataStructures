package laborai.gui.swing;

import laborai.gui.MyException;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.BstSetKTUx;
import Lab3Kaltenyte.KnyguGamyba;
import Lab3Kaltenyte.Knyga;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import Lab3Kaltenyte.GreitaveikosTyrimas;
import java.util.Iterator;
import laborai.studijosktu.Ks;

/**
 * Lab3 langas su Swing'u
 * <pre>
 *                  Lab3Panel (BorderLayout)
 *  |-----------------------Center-------------------------|
 *  |  |-----------------scrollOutput-------------------|  |
 *  |  |  |------------------------------------------|  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                 taOutput                 |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |------------------------------------------|  |  |                                                              |  |
 *  |  |------------------------------------------------|  |                                          |
 *  |------------------------South-------------------------|
 *  |  |~~~~~~~~~~~~~~~~~~~scrollSouth~~~~~~~~~~~~~~~~~~|  |
 *  |  |                                                |  |
 *  |  |    |------------||-----------||-----------|    |  |
 *  |  |    | panButtons || panParam1 || panParam2 |    |  |
 *  |  |    |            ||           ||           |    |  |
 *  |  |    |------------||-----------||-----------|    |  |
 *  |  |                                                |  |
 *  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
 *  |------------------------------------------------------|
 * </pre>
 *
 * @author darius.matulis@ktu.lt
 */
public class Lab3Window extends JFrame implements ActionListener {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");
    private static final int TF_WIDTH = 8;

    private final JTextArea taOutput = new JTextArea();
    private final JScrollPane scrollOutput = new JScrollPane(taOutput);
    private final JTextField tfDelimiter = new JTextField();
    private final JTextField tfInput = new JTextField();
    private final JComboBox cmbTreeType = new JComboBox();
    private final JPanel panSouth = new JPanel();
    private final JScrollPane scrollSouth = new JScrollPane(panSouth);
    private final JPanel panParam2 = new JPanel();
    private Menu menus;
    private Panels panParam1, panButtons;

    private SortedSetADTx<Knyga> knygaSet;

    private int sizeOfInitialSubSet, sizeOfGenSet, sizeOfLeftSubSet;
    private double coef;
    private String delimiter;
    private final String[] errors;

    public Lab3Window() {
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
        initComponents();
    }

    private void initComponents() {
        // Kad prijungiant tekstą prie JTextArea vaizdas visada nušoktų į apačią
        scrollOutput.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
            taOutput.select(taOutput.getCaretPosition()
                    * taOutput.getFont().getSize(), 0);
        });
        //======================================================================
        // Formuojamas mygtukų tinklelis (mėlynas). Naudojame klasę Panels.
        //======================================================================
        // 4 eilutes, 2 stulpeliai
        panButtons = new Panels(
                new String[]{
                    MESSAGES.getString("button1"),
                    MESSAGES.getString("button2"),
                    MESSAGES.getString("button3"),
                    MESSAGES.getString("button4"),
                    MESSAGES.getString("button5"),
                    MESSAGES.getString("button6"),
                    MESSAGES.getString("button7")}, 2, 4);
        panButtons.getButtons().forEach((btn) -> {
            btn.addActionListener(this);
        });
        enableButtons(false);
        //======================================================================
        // Formuojama pirmoji parametrų lentelė (žalia). Naudojame klasę Panels.
        //======================================================================
        panParam1 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam11"),
                    MESSAGES.getString("lblParam12"),
                    MESSAGES.getString("lblParam13"),
                    MESSAGES.getString("lblParam14"),
                    MESSAGES.getString("lblParam15")},
                new String[]{
                    MESSAGES.getString("tfParam11"),
                    MESSAGES.getString("tfParam12"),
                    MESSAGES.getString("tfParam13"),
                    MESSAGES.getString("tfParam14"),
                    MESSAGES.getString("tfParam15")}, TF_WIDTH);
        //======================================================================
        // Formuojama antroji parametrų lentelė (gelsva).
        //======================================================================
        panParam2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 6, 3, 4);
        // Išlygiavimas į kairę
        c.anchor = GridBagConstraints.WEST;
        // Komponentų išplėtimas iki maksimalaus celės dydžio
        c.fill = GridBagConstraints.BOTH;
        // Pirmas stulpelis
        c.gridx = 0;
        panParam2.add(new JLabel(MESSAGES.getString("lblParam21")), c);
        panParam2.add(new JLabel(MESSAGES.getString("lblParam22")), c);
        panParam2.add(new JLabel(MESSAGES.getString("lblParam23")), c);
        // Antras stulpelis
        c.gridx = 1;
        for (String s : new String[]{
            MESSAGES.getString("cmbTreeType1"),
            MESSAGES.getString("cmbTreeType2"),
            MESSAGES.getString("cmbTreeType3")
        }) {
            cmbTreeType.addItem(s);
        }
        cmbTreeType.addActionListener(this);
        panParam2.add(cmbTreeType, c);
        tfDelimiter.setHorizontalAlignment(JTextField.CENTER);
        panParam2.add(tfDelimiter, c);
        // Vėl pirmas stulpelis, tačiau plotis - 2 celės
        c.gridx = 0;
        c.gridwidth = 2;
        tfInput.setEditable(false);
        tfInput.setBackground(Color.lightGray);
        panParam2.add(tfInput, c);
        //======================================================================
        // Formuojamas bendras parametrų panelis (tamsiai pilkas).
        //======================================================================
        panSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panSouth.add(panButtons);
        panSouth.add(panParam1);
        panSouth.add(panParam2);

        menus = new Menu(this);
        // Meniu juosta patalpinama šiame freime
        setJMenuBar(menus);
        // Formuojamas Lab3 panelis        
        JPanel lab3 = new JPanel();
        lab3.setLayout(new BorderLayout());
        // ..centre ir pietuose talpiname objektus..
        lab3.add(scrollOutput, BorderLayout.CENTER);
        lab3.add(scrollSouth, BorderLayout.SOUTH);

        // Šio freimo "viduje" talpinamas lab3 panelis
        getContentPane().add(lab3);
        appearance();
    }

    private void appearance() {
        // Grafinių objektų rėmeliai
        TitledBorder outputBorder = new TitledBorder(MESSAGES.getString("border1"));
        outputBorder.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        scrollOutput.setBorder(outputBorder);
        TitledBorder southBorder = new TitledBorder(MESSAGES.getString("border2"));
        southBorder.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        scrollSouth.setBorder(southBorder);

        panParam2.setBackground(new Color(255, 255, 153));// Gelsva
        panParam1.setBackground(new Color(204, 255, 204));// Šviesiai žalia
        panParam1.getTfOfTable().get(2).setEditable(false);
        panParam1.getTfOfTable().get(2).setForeground(Color.red);
        panParam1.getTfOfTable().get(4).setEditable(false);
        panParam1.getTfOfTable().get(4).setBackground(Color.lightGray);
        panButtons.setBackground(new Color(112, 162, 255)); // Blyškiai mėlyna
        panSouth.setBackground(Color.GRAY);
        taOutput.setFont(Font.decode("courier new-12"));
        taOutput.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            System.gc();
            System.gc();
            System.gc();
            taOutput.setBackground(Color.white);
            Object source = ae.getSource();

            if (source.equals(panButtons.getButtons().get(0))) {
                treeGeneration(null);
            } else if (source.equals(panButtons.getButtons().get(1))) {
                treeIteration();
            } else if (source.equals(panButtons.getButtons().get(2))) {
                treeAdd();
            } else if (source.equals(panButtons.getButtons().get(3))) {
                treeEfficiency();
            } else if (source.equals(panButtons.getButtons().get(4))) {
                treeRemove();
            } else if (source.equals(panButtons.getButtons().get(5))) {
                treeLower();
            } else if (source.equals(panButtons.getButtons().get(6))) {
                treeHeight();

            } else if (source.equals(cmbTreeType)) {
                enableButtons(false);
            }
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                KsSwing.ounerr(taOutput, errors[e.getCode()] + ": " + e.getMessage());
                if (e.getCode() == 2) {
                    panParam1.getTfOfTable().get(0).setBackground(Color.red);
                    panParam1.getTfOfTable().get(1).setBackground(Color.red);
                }
            } else if (e.getCode() == 4) {
                KsSwing.ounerr(taOutput, MESSAGES.getString("msg3"));
            } else {
                KsSwing.ounerr(taOutput, e.getMessage());
            }
        } catch (java.lang.UnsupportedOperationException e) {
            KsSwing.ounerr(taOutput, e.getLocalizedMessage());
        } catch (Exception e) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("error5"));
            e.printStackTrace(System.out);
        }
    }

    public void treeGeneration(String filePath) throws MyException {
        // Nuskaitomi uždavinio parametrai
        readTreeParameters();
        // Sukuriamas aibės objektas, priklausomai nuo medžio pasirinkimo
        // cmbTreeType objekte
        createTree();

        Knyga[] knyguArray;
        // Jei failas nenurodytas - generuojama
        if (filePath == null) {
            knyguArray = KnyguGamyba.generuotiIrIsmaisyti(sizeOfGenSet, sizeOfInitialSubSet, coef);
            panParam1.getTfOfTable().get(2).setText(String.valueOf(sizeOfLeftSubSet));
        } else { // Skaitoma is failo
            knygaSet.load(filePath);
            knyguArray = new Knyga[knygaSet.size()];
            int i = 0;
            for (Object o : knygaSet.toArray()) {
                knyguArray[i++] = (Knyga) o;
            }
            // Skaitant iš failo išmaišoma standartiniu Collections.shuffle metodu.
            Collections.shuffle(Arrays.asList(knyguArray), new Random());
        }

        // Išmaišyto masyvo elementai surašomi i aibę
        knygaSet.clear();
        for (Knyga a : knyguArray) {
            knygaSet.add(a);
        }
        // Išvedami rezultatai
        // Nustatoma, kad eilutės pradžioje neskaičiuotų išvedamų eilučių skaičiaus
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, knygaSet.toVisualizedString(delimiter),
                MESSAGES.getString("msg5"));
        // Nustatoma, kad eilutės pradžioje skaičiuotų išvedamų eilučių skaičių
        KsSwing.setFormatStartOfLine(false);
        enableButtons(true);
    }

    public void treeLower() {

        Knyga a9 = new Knyga("Egziuperi Gyvenimas  2007 66 78");
        Knyga a2 = new Knyga("Zemaite Pasaka  1996 66 85");
        Knyga a3 = new Knyga("Tolstoj Gyvenimas  2007 66 85");
        Knyga a4 = new Knyga("Vardas Gyvenimas  2006 11 45");
        KsSwing.ou(taOutput, a4);
        KsSwing.ou(taOutput, " ");
        knygaSet.add(a9);
        knygaSet.add(a2);
        knygaSet.add(a3);
        knygaSet.add(a4);
        Knyga nauja = knygaSet.lower(a4);
        KsSwing.ou(taOutput, nauja);

    }

    public void treeHeight() {
        int dydis=0;
        knygaSet.clear();
        Knyga a1 = new Knyga("Meyer Pasaka  1996 66 85");
        Knyga a2 = new Knyga("Meyer Pasaka  1996 66 85");
        Knyga a3 = new Knyga("Meyer Pasaka  1996 66 85");
        Knyga a4 = new Knyga("Meyer Pasaka  1996 66 85");
        Knyga a5 = new Knyga("Meyer Pasaka  1996 66 85");
        Knyga a6 = new Knyga("Vojic Gyvenimas  1999 66 85");
        Knyga a7 = new Knyga("Egziuperi Vasara  2006 11 45");
        Knyga a8 = new Knyga("Rowling Gyvenimas  2000 66 78");
        Knyga a9 = new Knyga("Kamiu Maras  2007 66 11");
        knygaSet.add(a1);
        knygaSet.add(a2);
        knygaSet.add(a3);
        knygaSet.add(a4);
        knygaSet.add(a5);
        knygaSet.add(a6);
        knygaSet.add(a7);
        knygaSet.add(a8);
         knygaSet.add(a9);
        KsSwing.oun(taOutput, "*Medis ir jo aukštis*");
        KsSwing.oun(taOutput, knygaSet.toVisualizedString(""));
       
        
            KsSwing.oun(taOutput,  knygaSet.subSet(a3, a9));
            
        
        dydis = knygaSet.height();
        KsSwing.oun(taOutput, dydis);
       

    }

    private void treeAdd() throws MyException {
        Knyga knyga = KnyguGamyba.gautiIsBazes();
        knygaSet.add(knyga);
        panParam1.getTfOfTable().get(2).setText(String.valueOf(--sizeOfLeftSubSet));
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, knyga, MESSAGES.getString("msg7"));
        KsSwing.oun(taOutput, knygaSet.toVisualizedString(delimiter));
        KsSwing.setFormatStartOfLine(false);
    }

    private void treeRemove() {
        KsSwing.setFormatStartOfLine(true);
        if (knygaSet.isEmpty()) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("msg4"));
            KsSwing.oun(taOutput, knygaSet.toVisualizedString(delimiter));
        } else {
            int nr = new Random().nextInt(knygaSet.size());
            Knyga knyga = (Knyga) knygaSet.toArray()[nr];
            knygaSet.remove(knyga);
            KsSwing.oun(taOutput, knyga, MESSAGES.getString("msg6"));
            KsSwing.oun(taOutput, knygaSet.toVisualizedString(delimiter));
        }
        KsSwing.setFormatStartOfLine(false);
    }

    private void treeIteration() {
        KsSwing.setFormatStartOfLine(true);
        if (knygaSet.isEmpty()) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("msg4"));
        } else {
            KsSwing.oun(taOutput, knygaSet, MESSAGES.getString("msg8"));
        }
        KsSwing.setFormatStartOfLine(false);
    }

    private void treeEfficiency() throws MyException {
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, "", MESSAGES.getString("msg2"));
        KsSwing.setFormatStartOfLine(false);
        boolean[] statesOfButtons = new boolean[panButtons.getButtons().size()];
        for (int i = 0; i < panButtons.getButtons().size(); i++) {
            statesOfButtons[i] = panButtons.getButtons().get(i).isEnabled();
            panButtons.getButtons().get(i).setEnabled(false);
        }
        cmbTreeType.setEnabled(false);
        for (Component component : menus.getComponents()) {
            component.setEnabled(false);
        }

        GreitaveikosTyrimas gt = new GreitaveikosTyrimas();

        // Sukuriamos dvi tuscios gijos. Panaudojamas Java Executor servisas.
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Si gija paima rezultatus is greitaveikos tyrimo gijos ir isveda 
        // juos i taOutput. Gija baigia darbą kai gaunama FINISH_COMMAND
        executorService.submit(() -> {
            KsSwing.setFormatStartOfLine(false);
            try {
                String result;
                while (!(result = gt.getResultsLogger().take())
                        .equals(GreitaveikosTyrimas.FINISH_COMMAND)) {
                    KsSwing.ou(taOutput, result);
                    gt.getSemaphore().release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            gt.getSemaphore().release();

            for (int i = 0; i < panButtons.getButtons().size(); i++) {
                panButtons.getButtons().get(i).setEnabled(statesOfButtons[i]);
            }
            cmbTreeType.setEnabled(true);
            for (Component component : menus.getComponents()) {
                component.setEnabled(true);
            }
        });

        //Sioje gijoje atliekamas greitaveikos tyrimas
        executorService.submit(() -> gt.pradetiTyrima());
        executorService.shutdown();
    }

    private void readTreeParameters() throws MyException {
        // Truputėlis kosmetikos..
        for (int i = 0; i < 3; i++) {
            panParam1.getTfOfTable().get(i).setBackground(Color.WHITE);
        }
        // Nuskaitomos parametrų reiksmės. Jei konvertuojant is String
        // įvyksta klaida, sugeneruojama NumberFormatException situacija. Tam, kad
        // atskirti kuriame JTextfield'e ivyko klaida, panaudojama nuosava
        // situacija MyException
        int i = 0;
        try {
            // Pakeitimas (replace) tam, kad sukelti situaciją esant
            // neigiamam skaičiui            
            sizeOfGenSet = Integer.valueOf(panParam1.getParametersOfTable().get(i).replace("-", "x"));
            sizeOfInitialSubSet = Integer.valueOf(panParam1.getParametersOfTable().get(++i).replace("-", "x"));
            sizeOfLeftSubSet = sizeOfGenSet - sizeOfInitialSubSet;
            ++i;
            coef = Double.valueOf(panParam1.getParametersOfTable().get(++i).replace("-", "x"));
        } catch (NumberFormatException e) {
            // Galima ir taip: pagauti exception'ą ir vėl mesti
            throw new MyException(panParam1.getParametersOfTable().get(i), i);
        }
        delimiter = tfDelimiter.getText();
    }

    private void createTree() throws MyException {
        switch (cmbTreeType.getSelectedIndex()) {
            case 0:
                knygaSet = new BstSetKTUx(new Knyga());
                break;
            case 1:
                knygaSet = new AvlSetKTUx(new Knyga());
                break;
            default:
                enableButtons(false);
                throw new MyException(MESSAGES.getString("msg1"));
        }
    }

    private void enableButtons(boolean enable) {
        for (int i : new int[]{1, 2, 4, 5, 6}) {
            panButtons.getButtons().get(i).setEnabled(enable);
        }
    }

    public JTextArea getTaOutput() {
        return taOutput;
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()
            // Arba sitaip, tada swing komponentu isvaizda priklausys
            // nuo naudojamos OS:
            //  UIManager.getSystemLookAndFeelClassName()
            // Arba taip:
            //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
            // Linux'e dar taip:
            //  "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"
            );
            UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Ks.ou(ex.getMessage());
        }
        Lab3Window window = new Lab3Window();
        window.setLocation(50, 50);
        window.setIconImage(new ImageIcon(MESSAGES.getString("icon")).getImage());
        window.setTitle(MESSAGES.getString("title"));
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1100, 650));
        window.pack();
        window.setVisible(true);
    }
}
