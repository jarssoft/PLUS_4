import java.util.Vector;

/** Pelaajan tekemä korttien laittaminen poistopakkaan. 
 *  Lyöntiin lasketaan mustien korttien yhteydessä ilmoitettu väri.*/ 
public class Lyonti {

	private Vector<Kortti> kortit = new Vector<Kortti>();
	private Vari vari = null;

	public Lyonti(Vector<Kortti> kortit, Vari vari) {
		
		//Korttien pitää olla samaa merkkiä
		assert(kortit.size()<2 || kortit.firstElement().getMerkki() == kortit.lastElement().getMerkki());
		
		this.setLyoty(kortit);
		this.setVari(vari);
	}

	public Vector<Kortti> getKortit() {
		return kortit;
	}

	public void setLyoty(Vector<Kortti> lyoty) {
		this.kortit = lyoty;
	}

	public Vari getVari() {
		assert(!isEmpty());
		if(vari!=null) {
			return vari;
		}else {
			return kortit.lastElement().getVari(); 
		}
	}

	public Vari getValittuVari() {
		return vari;
	}
	
	public void setVari(Vari vari) {
		this.vari = vari;
	}
	
	public boolean isMusta() {
		return kortit.firstElement().isMusta();
	}
	
	public boolean isEmpty() {
		return kortit.isEmpty();
	}

	public Merkki getMerkki() {
		return kortit.firstElement().getMerkki();
	}
}