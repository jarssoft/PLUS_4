import java.util.Vector;
import java.util.Random;

/** Tekoäly joka toimii satunnaisesti. */
public class Tyhmä extends Tekoäly {

    protected Vari vari;
    protected Kortti poisto;

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

        return paras(kasi, poisto, vari);

    }
    
    // Värivalinta, joka kysytään älyltä mustan kortin jälkeen
    @Override
	public Vari getVari(){
        return Vari.values()[new Random().nextInt(Vari.values().length)];
    }

	@Override
	int hyvyys(Vector<Kortti> lyotava, Vector<Kortti> jatettava) {
		return (int)(Math.random() * 10000);
	}

}
