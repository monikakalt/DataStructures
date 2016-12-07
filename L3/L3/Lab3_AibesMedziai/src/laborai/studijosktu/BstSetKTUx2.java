package laborai.studijosktu;

import java.util.Comparator;

/**
 * Klasė paveldi klasę BstSetKTUx ir perdengia add metoda iteracine realizacija
 *
 * @author darius
 * @param <E>
 */
public class BstSetKTUx2<E extends KTUable<E>> extends BstSetKTUx<E>
        implements SortedSetADT<E> {

    public BstSetKTUx2(E baseObj) {
        super(baseObj);
    }

    public BstSetKTUx2(E baseObj, Comparator<? super E> c) {
        super(baseObj, c);
    }

    /**
     * Aibė papildoma nauju elementu. Papildymas atliekamas iteracijos į gylį
     * būdu
     *
     * @param element
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        if (root == null) {
            root = new BstNode<>(element);
        } else {
            BstNode<E> current = root;
            BstNode<E> parent = null;
            while (current != null) {
                parent = current;
                int cmp = c.compare(element, current.element);
                if (cmp < 0) {
                    current = current.left;
                } else if (cmp > 0) {
                    current = current.right;
                } else {
                    return;
                }
            }

            int cmp = c.compare(element, parent.element);
            if (cmp < 0) {
                parent.left = new BstNode<>(element);
            } else if (cmp > 0) {
                parent.right = new BstNode<>(element);
            }
        }
        size++;
    }
}
