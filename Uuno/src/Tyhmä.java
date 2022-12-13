import java.util.Vector;

/** Tekoäly joka toimii satunnaisesti. */
public class Tyhmä extends Tekoäly {

    protected Vari vari;
    protected Kortti poisto;
    protected Vari lyotavaVari;

    // Tiedottaa pelaajalle tapahtumasta.
    @Override
	public void tapahtuma(Tapahtuma logi){
        if(!logi.lyonti.isEmpty()){
            this.vari=logi.vari;
            this.poisto=logi.lyonti.lastElement();
        }
    }

    // Pyytää tekoälyä tekemään valinnan.
    @Override
	public Vector<Kortti> getKortti(Vector<Kortti> kasi){
    
        assert(poisto!=null);
        assert(!kasi.isEmpty());

        Lyonti l = paras(kasi, poisto, vari);
        lyotavaVari  = l.getVari();
        
        return l.getKortit();
    }
    
    // Värivalinta, joka kysytään älyltä mustan kortin jälkeen
    @Override
	public Vari getVari(){
        return lyotavaVari;
    }

	@Override
	int hyvyys(Vector<Kortti> lyotava, Vari vari, Vector<Kortti> jatettava) {
		return (int)(Math.random() * 10000);
	}

}
