import java.util.Vector;

/** Tekoäly joka toimii satunnaisesti. */
public class Tyhmä extends Tekoäly {

    protected Vari vari;
	private Lyonti poisto;

    // Tiedottaa pelaajalle tapahtumasta.
    @Override
	public void tapahtuma(Tapahtuma logi){
        if(!logi.lyonti.isEmpty()){
        	poisto=logi.getLyonti();
        }
    }

    // Pyytää tekoälyä tekemään valinnan.
    @Override
	public Lyonti getKortti(Vector<Kortti> kasi){
    
        assert(poisto.getKortit().lastElement()!=null);
        assert(!kasi.isEmpty());

        return paras(kasi, poisto);        
    }
    
	@Override
	int hyvyys(Lyonti lyonti, Vector<Kortti> jatettava) {
		return (int)(Math.random() * 10000);
	}

}
