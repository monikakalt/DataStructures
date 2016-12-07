package laborai.studijosktu;

public interface SortedSetADTx<E> extends SortedSetADT<E> {

    void add(String dataString);

    void load(String fName);

    String toVisualizedString(String dataCodeDelimiter);

    Object clone() throws CloneNotSupportedException;

    @Override
    public SortedSetADTx<E> tailSet(E element);

    public E lower(E e);

    public E pollLast();

    public int height();

    @Override
    public E last();

    public SortedSetADT<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive);

    public Object[] toArray();

}
