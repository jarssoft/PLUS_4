import java.util.Vector;

// Ainut osa joka tekee päätöksiä.
public class Tyhmä implements Tekoäly {

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

        Vector<Kortti> lyotava = new Vector<Kortti>();


        for (Kortti k : kasi) {

            if(Peli.voiLyoda(k, this.poisto, this.vari, kasi.size()==1)){
                lyotava.add(k);
                for (Kortti lk : kasi) {
                    if(k != lk && Peli.voiLyodaLisäksi(lk, k)){
                        lyotava.add(lk);
                    }
                }
                break;
            }
        }
        return lyotava;
    }
    
    // Värivalinta, joka kysytään älyltä mustan kortin jälkeen
    @Override
	public Vari getVari(){
        return Vari.KELTAINEN;
    }

}
