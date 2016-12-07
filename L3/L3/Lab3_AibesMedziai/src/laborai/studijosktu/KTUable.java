package laborai.studijosktu;

/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra  interfeisas, kurį turi tenkinti KTU studentų kuriamos duomenų klasės
 *       Metodai užtikrina patogų duomenų suformavimą iš String eilučių
 ******************************************************************************/
public interface KTUable<T> extends Comparable<T> {

    /**
     * Sukuria naują objektą iš String eilutės
     *
     * @param dataString
     * @return
     */
    KTUable<T> create(String dataString);

    /**
     * Patikrina objekto reikšmes
     *
     * @return
     */
    String validate();

    /**
     * Suformuoja objektą iš eilutės
     *
     * @param e
     */
    void parse(String e);

    /**
     * this objektas sulyginamas su e obj.
     *
     * @param e
     * @return
     */
    @Override
    int compareTo(T e);

    /**
     * Atvaizduoja objektą į String eilutę
     *
     * @return
     */
    @Override
    String toString();
}
