/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra  interfeisas, kurį turi tenkinti KTU studentų kuriamos duomenų klasės
 *       Metodai užtikrina patogų duomenų suformavimą iš String eilučių
 ******************************************************************************/
package studijosKTU;

public interface KTUable<T> extends Comparable<T> {
    KTUable create(String dataString); // sukuria naują objektą iš eilutės
    String validate();                 // patikrina objekto reikšmes
    void parse(String dataString);     // suformuoja objektą iš eilutės
    @Override
    int compareTo(T e);                // this objektas sulyginamas su e obj.
    @Override
    String toString();                 // atvaizduoja objektą į simbolių eilutę
}
