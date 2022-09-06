import java.lang.Math;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;

// Hoitaa uuno-pelissä pelaajien vuorottelun mukaan lukien
// suunnanvaihdot ja ohitukset.
public class KööriIterator<T>  implements Iterator<T> {

    private LinkedList<T> pelaajat;
    private ListIterator<T> iterator;
    private int suunta=1;

    public KööriIterator(LinkedList<T> pelaajat){
       this.pelaajat = pelaajat;
       iterator = this.pelaajat.listIterator(0);
    }

    public boolean hasNext(){
        return iterator.hasNext() || iterator.hasPrevious();
    }

    // Hakee seuraavan pelaajan
    public T next(){
        assert(Math.abs(suunta) == 1);

        if(suunta==1){
            if(!iterator.hasNext()){
                iterator = pelaajat.listIterator(0);
            }
            return iterator.next();
        }else{
            if(!iterator.hasPrevious()){
                iterator = pelaajat.listIterator(pelaajat.size());
            }
            return iterator.previous();
        }
    }

    // Vaihtaa next()-funktion kulkusuunnan
    public void suunnanvaihto(int m){
        final int muutos = (int)Math.pow(-1, m);
        suunta *= muutos;

        //Jos suunta vaihtuu, joudutaan siirtymään askel.
        if(muutos<0){
            next();
        }
    }

    // Ohittaa tietyn verran vuoroja
    public void ohitus(int m){
        for(int i=0;i<m;i++){
            next();
        }
    }

    // Poistaa vuorossa olevan pelaajan
    public void remove(){
        iterator.remove();
    }

    public static void main(String[] args) {

        LinkedList<Integer> pelaajat = new LinkedList<Integer>();
        pelaajat.add(1);
        pelaajat.add(2);
        pelaajat.add(3);
        assert(pelaajat.size()==3);

        KööriIterator<Integer> ki = new KööriIterator<Integer>(pelaajat);
        assert(ki.hasNext()==true);
        assert(ki.next()==1);
        ki.ohitus(1);
        assert(ki.next()==3);
        ki.suunnanvaihto(1);
        assert(ki.next()==2);
        ki.suunnanvaihto(2);
        assert(ki.next()==1);
        ki.suunnanvaihto(3);
        ki.ohitus(1);
        assert(ki.next()==3);
        
        // Palataan alusta loppuun
        assert(ki.next()==1);
        ki.suunnanvaihto(1);
        assert(ki.next()==3);
        ki.ohitus(2);
        assert(ki.next()==3);

        // Poistaminen
        ki.remove();
        assert(ki.next()==2);
        ki.remove();
        assert(ki.next()==1);
        assert(ki.next()==1);
        ki.remove();

        // listassa ei ole muita elementtejä
        assert(ki.hasNext()==false);
        
    }

}
