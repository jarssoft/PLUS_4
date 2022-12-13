import java.util.Vector;

public class Lyonti {

	private Vector<Kortti> lyoty = new Vector<Kortti>();
	private Vari vari = null;

	public Lyonti(Vector<Kortti> lyoty, Vari vari) {
		this.setLyoty(lyoty);
		this.setVari(vari);
	}

	public Vector<Kortti> getKortit() {
		return lyoty;
	}

	public void setLyoty(Vector<Kortti> lyoty) {
		this.lyoty = lyoty;
	}

	public Vari getVari() {
		return vari;
	}

	public void setVari(Vari vari) {
		this.vari = vari;
	}

}