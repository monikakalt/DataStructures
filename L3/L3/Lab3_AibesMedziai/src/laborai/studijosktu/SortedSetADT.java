package laborai.studijosktu;

import java.util.Iterator;

public interface SortedSetADT<E> extends SetADT<E> {

    /**
     * Grąžinamas aibės poaibis iki elemento data.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento data.
     */
    SetADT<E> headSet(E element);

    /**
     * Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     */
    SetADT<E> subSet(E element1, E element2);

    /**
     * Grąžinamas aibės poaibis iki elemento data.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento data.
     */
    SetADT<E> tailSet(E element);

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    Iterator<E> descendingIterator();
    E last();
    SortedSetADT<E> tailSet(E element,boolean ex);
}
