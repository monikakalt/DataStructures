package laborai.studijosktu;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 *
 * @author darius.matulis@ktu.lt
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<T>, arba
 * per klasės konstruktorių turi būti paduodamas Comparator<T> interfeisą
 * tenkinantis objektas.
 */
public class BstSetKTU<E extends Comparable<E>> implements SortedSetADT<E>, Cloneable {

    // Medžio šaknies mazgas
    protected BstNode<E> root = null;
    // Medžio dydis
    protected int size = 0;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c = null;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<T>
     */
    public BstSetKTU() {
        this.c = (e1, e2) -> e1.compareTo(e2);
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<T>
     *
     * @param c Komparatorius
     */
    public BstSetKTU(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        root = addRecursive(element, root);
    }

    private BstNode<E> addRecursive(E element, BstNode<E> node) {
        if (node == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = addRecursive(element, node.right);
        }

        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }

        root = removeRecursive(element, root);
    }
/*REMOVECURSIVE*/
    private BstNode<E> removeRecursive(E element, BstNode<E> node) {
        if (node == null) {
            return node;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {
            /* Atvejis kai šalinamas elemento mazgas turi abu vaikus.
             Ieškomas didžiausio rakto elemento mazgas kairiajame pomedyje.
             Galima kita realizacija kai ieškomas mažiausio rakto
             elemento mazgas dešiniajame pomedyje. Tam yra sukurtas
             metodas getMin(E element);
             */
            BstNode<E> nodeMax = getMax(node.left);
            /* Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            node.element = nodeMax.element;
            // Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            node.left = removeMax(node.left);
            size--;
            // Kiti atvejai
        } else {
            node = (node.left != null) ? node.left : node.right;
            size--;
        }

        return node;
    }

    private E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    /**
     * Pašalina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> removeMax(BstNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        } else {
            return node.left;
        }
    }

    /**
     * Grąžina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMax(BstNode<E> node) {
        return get(node, true);
    }

    /**
     * Grąžina minimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMin(BstNode<E> node) {
        return get(node, false);
    }

    private BstNode<E> get(BstNode<E> node, boolean findMax) {
        BstNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    /**
     * Suranda BST medžio aukštį (kelio ilgį nuo šaknies iki tolimiausio lapo)
     *
     * @return medžio aukštis
     */
    public int height() {
        if (this.isEmpty()) {
            return -1;
        } else {
            BstNode<E> node = root;
            return findHeight(node) + 1;
        }
    }

    private int findHeight(BstNode<E> node) {
        if (node == null) {
            return -1;
        }

        int lefth = findHeight(node.left);
        int righth = findHeight(node.right);

        if (lefth > righth) {
            return lefth + 1;
        } else {
            return righth + 1;
        }
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(element.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     *
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? new StringBuilder().append(">").append(horizontal).toString()
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSetKTU<E> cl = (BstSetKTU<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private BstNode<E> cloneRecursive(BstNode<E> node) {
        if (node == null) {
            return null;
        }

        BstNode<E> clone = new BstNode<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento.
     */
    @Override
    public SortedSetADT<E> headSet(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in"
                    + " headSet(E element)");
        }
        if (root == null) {
            throw new NullPointerException("Set's root is null in "
                    + "headSet(E element)");
        }
        if (!this.contains(element)) {
            throw new NoSuchFieldError("Set isn't containing element in"
                    + " headSet(E element)");
        }
        SortedSetADT<E> head = new BstSetKTU<>();
        for (E e : this) {
            if (c.compare(e, element) < 0) {
                head.add(e);
            }
        }
        return head;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */
    
/*SUBSET*/    
    @Override
    public SortedSetADT<E> subSet(E element1, E element2) {
        if (element1 == null || element2 == null) {
            throw new IllegalArgumentException("Element1 or/and element2 are null in"
                    + " subSet(E element1, E element2)");
        }
        if (root == null) {
            throw new NullPointerException("Set's root is null in "
                    + "subSet(E element1, E element2)");
        }
        if (!this.contains(element1) || !(this.contains(element2))) {
            throw new NoSuchFieldError("Set isn't containing element1 or/and element2 in"
                    + " subSet(E element1, E element2)");
        }
        SortedSetADT<E> head = new BstSetKTU<>();
        for (E e : this) {
            if (c.compare(e, element1) >= 0 && c.compare(e, element2) < 0) {
                head.add(e);
            }
        }
        return head;
    }

    
    /**
     * Grąžinamas aibės poaibis nuo elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    
 /*TAILSET*/////////////////////////
    @Override
    public SortedSetADT<E> tailSet(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in"
                    + " tailSet(E element)");
        }
        if (root == null) {
            throw new NullPointerException("Set's root is null in "
                    + "tailSet(E element)");
        }
        if (!this.contains(element)) {
            throw new NoSuchFieldError("Set isn't containing element in"
                    + " tailSet(E element)");
        }
        SortedSetADT<E> head = new BstSetKTU<>();
        for (E e : this) {
            if (c.compare(e, element) >= 0) {
                head.add(e);
            }
        }
        return head;
    }

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorKTU(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorKTU(false);
    }
 @Override
    public E last() {
        if (this.root == null) {
            throw new NullPointerException("Set's root is null in "
                    + "E last()");
        }
        Iterator<E> it = iterator();
        E last = null;
        while (it.hasNext()) {
            last = it.next();
            if (!it.hasNext()) {
                it.remove();
                return last;
            }
        }
        return last;
    }
    
//    @Override
    public E pollLast() {
        if (this.root == null) {
            throw new NullPointerException("Set's root is null in "
                    + "E last()");
        }
        Iterator<E> it = iterator();
        E previous=null;
        E last = null;
        while (it.hasNext()) {
            last = it.next();
            if (!it.hasNext()) {
                it.remove();
                
            }
        }
        return null;
    }

    /**
     * Grąžinamas didžiausias aibės elementas, mažesnis už duotąjį
     *
     * @param e - Aibės elementas
     * @return Grąžinamas didžiausias aibės elementas, mažesnis už duotąjį
/*LOWER****/
    public E lower(E e) {
        if (this.root == null) {
            throw new NullPointerException("Set's root is null in "
                    + " E lover(E e)");
        }
        if (e == null) {
            throw new IllegalArgumentException("Element is null in"
                    + " E lover(E e)");
        }
        SortedSetADT<E> set = new BstSetKTU<>();
        for (E el : this) {
            if (c.compare(el, e) < 0) {
                set.add(el);
            }
        }
        return set.last();
    }

    /**
     * Grąžinamas aibės poaibis nuo fromElement iki toElement fromInclusive ir
     * toInclusive nurodo režių tipą
     *
     * @param fromElement - pirmasis poaibio elementas
     * @param fromInclusive - nurodo, ar įtraukti fromElement į poaibį
     * @param toElement - paskutinis poaibio elementas
     * @param toInclusive - nurodo, ar traukti toElement į poaibį
     * @return Grąžinamas aibės poaibis nuo fromElement iki toElement
     */
/*SUBSET*************/
    public SortedSetADT<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        if (fromElement == null || toElement == null) {
            throw new IllegalArgumentException("Element1 or/and element2 are null in"
                    + " subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)");
        }
        if (root == null) {
            throw new NullPointerException("Set's root is null in "
                    + "subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)");
        }
        if (!this.contains(fromElement) || !(this.contains(toElement))) {
            throw new NoSuchFieldError("Set isn't containing element1 or/and element2 in"
                    + " subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)");
        }
        SortedSetADT<E> head = new BstSetKTU<>();
        if (!fromInclusive) {
            if (!toInclusive) {
                for (E e : this) {
                    if (c.compare(e, fromElement) > 0 && c.compare(e, toElement) < 0) {
                        head.add(e);
                    }
                }
            } else {
                for (E e : this) {
                    if (c.compare(e, fromElement) > 0 && c.compare(e, toElement) <= 0) {
                        head.add(e);
                    }
                }
            }
        } else if (!toInclusive) {
            head = subSet(fromElement, toElement);
        } else {
            for (E e : this) {
                if (c.compare(e, fromElement) >= 0 && c.compare(e, toElement) <= 0) {
                    head.add(e);
                }
            }
        }
        return head;
    }

    
    @Override
    public SortedSetADT<E> tailSet(E element,boolean ex) {
		SortedSetADT<E> temp = new BstSetKTU<E>(this.c);
		if (root == null) {
			return temp;
		}
		temp=tailSetRecursive((BstNode<E>)root,temp,element,ex);
		return temp;
	}
	private SortedSetADT<E> tailSetRecursive(BstNode<E> node, SortedSetADT<E> temp, E element, boolean ex) {
		if (node == null) {
			return temp;
		}
		if (ex) {
			if (node.element.compareTo(element) >= 0) {
				temp.add(node.element);
			}
		} else {
			if (node.element.compareTo(element) > 0) {
				temp.add(node.element);
			}
		}
		tailSetRecursive((BstNode<E>)node.left, temp, element,ex);
		tailSetRecursive((BstNode<E>)node.right, temp, element,ex);
		return temp;
	}
    

    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorKTU implements Iterator<E> {

        private Stack<BstNode<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private boolean ascending;
        // Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private BstNode<E> parent = root;
        private BstNode<E> current = null;

        IteratorKTU(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {
            if (!stack.empty()) {
                // Grąžinamas paskutinis į steką patalpintas elementas
                BstNode<E> n = stack.pop();
                // Atsimenama tėvo viršunė. Reikia remove() metodui
                parent = (!stack.empty()) ? stack.peek() : root;
                BstNode node = (ascending) ? n.right : n.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                current = n;
                return n.element;
            } else { // Jei stekas tuščias
                return null;
            }
        }

        @Override
        public void remove() {
            BstSetKTU.this.remove(current.element);
        }

        private void toStack(BstNode<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class BstNode<N> {

        // Elementas
        protected N element;
        // Rodyklė į kairįjį pomedį
        protected BstNode<N> left;
        // Rodyklė į dešinįjį pomedį
        protected BstNode<N> right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}









/* @Override
    public E pollLast() {
        if (this.root == null) {
            throw new NullPointerException("Set's root is null in "
                    + "E last()");
        }
        Iterator<E> it = iterator();
        E last = null;
        while (it.hasNext()) {
            last = it.next();
            if (!it.hasNext()) {
                it.remove();
                //return last;
            }
        }
        return null;
    }*/