import java.util.Vector;

// Ainut osa joka tekee päätöksiä.
public class Viisas implements Tekoäly {

    private Vari vari;
    private Kortti poisto;

    // Tiedottaa pelaajalle tapahtumasta.
    @Override
	public void tapahtuma(Logi logi){
        
        if(!logi.lyonti.isEmpty()){
            this.vari=logi.vari;
            this.poisto=logi.lyonti.get(logi.lyonti.size()-1);
        }
    }

    // Pyytää tekoälyä tekemään valinnan.
    @Override
	public Vector<Kortti> getKortti(Vector<Kortti> kasi){
    
        assert(poisto!=null);
        assert(!kasi.isEmpty());      
        
        Vector<Kortti> paraslyotava = new Vector<Kortti>();
        int parashyvyys = 0;

        for (Kortti k : kasi) {
        	
        	Vector<Kortti> ehdokaslyotava = new Vector<Kortti>();        	
        	int ehdokashyvyys = 0;        
            
        	if(Peli.voiLyoda(k, this.poisto, this.vari, kasi.size()==1)){
        		ehdokaslyotava.add(k);
                for (Kortti lk : kasi) {
                    if(k != lk && Peli.voiLyodaLisäksi(lk, k)){
                    	ehdokaslyotava.add(lk);
                    }
                }
            }
        	
        	// Pienempi käsi on parempi
        	ehdokashyvyys+=10-ehdokaslyotava.size();
        	
        	// Nollakäsi ei hyvä
        	if(!ehdokaslyotava.isEmpty()) {
        		ehdokashyvyys+=10;
        		
        		// Yritetään käyttää mustat ennen viimeistä korttia
        		if(ehdokaslyotava.get(0).isMusta() && kasi.size()>1 && kasi.size()<2) {
        			ehdokashyvyys+=3;
        		}
        		
        		// Säästetään ohittavat kortit loppuun
        		if(!ehdokaslyotava.get(0).isMusta() 
        				&& (ehdokaslyotava.get(0).getMerkki() == Merkki.PLUS2
        						|| ehdokaslyotava.get(0).getMerkki() == Merkki.OHITUS)
        				&& kasi.size()>1 && kasi.size()<2) {
        			ehdokashyvyys+=3;
        		}
        	}
        	
        	if(ehdokashyvyys>parashyvyys) {
        		paraslyotava=new Vector<Kortti>(ehdokaslyotava);
        		parashyvyys=ehdokashyvyys;
        	}
        }
        
        if(!paraslyotava.isEmpty() && paraslyotava.get(0).isMusta()) {
        	vari=Vari.PUNAINEN;
	    	for(Kortti k: kasi) {
	    		if(!k.isMusta()) {
	    			vari = k.getVari();
	    		}
	    	}
        }
    	
        return paraslyotava;
    }
    
    // Värivalinta, joka kysytään älyltä mustan kortin jälkeen
    @Override
	public Vari getVari(){
    	return vari;
    }

}
