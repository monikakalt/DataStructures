package laborai.studijosktu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

public class BstSetKTUx<E extends KTUable<E>> extends BstSetKTU<E>
        implements SortedSetADTx<E> {

    private final E baseObj;       // bazinis objektas skirtas naujų kūrimui

    /**
     * Konstruktorius su bazinio objekto fiksacija dėl naujų elementų kūrimo
     *
     * @param baseObj
     */
    public BstSetKTUx(E baseObj) {
        super();
        this.baseObj = baseObj;
    }

    /**
     * Konstruktorius su bazinio objekto fiksacija dėl naujų elementų kūrimo
     *
     * @param baseObj
     * @param c
     */
    public BstSetKTUx(E baseObj, Comparator<? super E> c) {
        super(c);
        this.baseObj = baseObj;
    }

    /**
     * Sukuria elementą iš String ir įdeda jį į pabaigą
     *
     * @param dataString
     */
    @Override
    public void add(String dataString) {
        super.add((E) baseObj.create(dataString));
    }
    @Override
    public SortedSetADTx<E> headSet(E element) {
    	SortedSetADTx<E> temp=new BstSetKTUx<E>(element,this.c);
    	if(root.element.compareTo(element)>0){
    		return temp;
    	}
    	temp=headSetRecursive(root,temp,element);
    	return temp;
    }
    private SortedSetADTx<E> headSetRecursive(BstNode<E> node,SortedSetADTx<E>temp,E element)
    {
    	if (node == null) {
            return temp;
        }
    	if(node.element.compareTo(element)<0){
    		temp.add(node.element);
    	}
    	headSetRecursive(node.left,temp,element);
    	headSetRecursive(node.right,temp,element);
    	return temp;
    }
    @Override
    public SortedSetADTx<E> subSet(E element1, E element2) {
    	SortedSetADTx<E> temp=new BstSetKTUx<E>(element1,this.c);
    	if(element1.compareTo(element2)>0){
    		E temp1=element1;
    		element1=element2;
    		element2=temp1;
    		
    	}
    	if(element1.compareTo(element2)==0){
    		return temp;
    	} 	
    	if(root.element.compareTo(element1)>0){
    		return temp;
    	}
    	temp =subSetRecursive(root,temp,element1,element2);
    	return temp;
    }
    private SortedSetADTx<E> subSetRecursive(BstNode<E> node,SortedSetADTx<E>temp,E element1,E element2){
    	if(node==null){
    		return temp;
    	}
    	if((node.element.compareTo(element1)>=0)&&
    			(node.element.compareTo(element2)<0)){
    		temp.add(node.element);
    	}
    	subSetRecursive(node.left,temp,element1,element2);
    	subSetRecursive(node.right,temp,element1,element2);
    	return temp;
    }
    /**
	 * Grąžinamas aibės poaibis iki elemento.
	 *
	 * @param element
	 *            - Aibės elementas.
	 * @return Grąžinamas aibės poaibis nuo elemento.
	 */
	@Override
	public SortedSetADTx<E> tailSet(E element) {
		SortedSetADTx<E> temp = new BstSetKTUx<E>(element,this.c);
		if (root == null) {
			return temp;
		}
		temp=tailSetRecursive(root,temp,element,true);
		return temp;
	}
	public SortedSetADTx<E> tailSet(E element,boolean ex) {
		SortedSetADTx<E> temp = new BstSetKTUx<E>(element,this.c);
		if (root == null) {
			return temp;
		}
		temp=tailSetRecursive(root,temp,element,ex);
		return temp;
	}
	private SortedSetADTx<E> tailSetRecursive(BstNode<E> node, SortedSetADTx<E> temp, E element, boolean ex) {
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
		tailSetRecursive(node.left, temp, element,ex);
		tailSetRecursive(node.right, temp, element,ex);
		return temp;
	}
    /**
     * Suformuoja sąrašą iš filePath failo
     *
     * @param filePath
     */
    @Override
    public void load(String filePath) {
        clear();
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        if (baseObj == null) { // elementų kūrimui reikalingas baseObj
            Ks.ern("Naudojant load-metodą, "
                    + "reikia taikyti konstruktorių = new SetBstKTU(new E())");
        }
        try {
            try (BufferedReader fReader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset())) {
                fReader.lines().filter(line -> line != null && !line.isEmpty()).forEach(line -> add(line));
            }
        } catch (FileNotFoundException e) {
            Ks.ern("Tinkamas duomenų failas " + filePath + " nerastas");
        } catch (IOException e) {
            Ks.ern("Failo " + filePath + " skaitymo klaida");
        }
    }

   

}