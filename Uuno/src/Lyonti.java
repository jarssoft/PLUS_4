import java.util.Vector;

/** Pelaajan tekemä korttien laittaminen poistopakkaan. 
 *  Lyöntiin lasketaan mustien korttien yhteydessä ilmoitettu väri.*/ 
public class Lyonti {

	private Vector<Kortti> kortit = new Vector<Kortti>();
	private Vari vari = null;

	public Lyonti(Vector<Kortti> lyoty, Vari vari) {
		this.setLyoty(lyoty);
		this.setVari(vari);
	}

	public Vector<Kortti> getKortit() {
		return kortit;
	}

	public void setLyoty(Vector<Kortti> lyoty) {
		this.kortit = lyoty;
	}

	public Vari getVari() {
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
}