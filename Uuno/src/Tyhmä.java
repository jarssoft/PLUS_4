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
	public Lyonti getKortti(Vector<Kortti> kasi){
    
        assert(poisto!=null);
        assert(!kasi.isEmpty());

        return paras(kasi, poisto, vari);        
        
    }
    
	@Override
	int hyvyys(Vector<Kortti> lyotava, Vari vari, Vector<Kortti> jatettava) {
		return (int)(Math.random() * 10000);
	}

}
