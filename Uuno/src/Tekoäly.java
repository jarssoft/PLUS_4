import java.util.Vector;

public abstract class Tekoäly {

	/** Tiedottaa pelaajalle tapahtumasta. */
	abstract void tapahtuma(Tapahtuma logi);

	/** Pyytää tekoälyä tekemään valinnan. */
	abstract Vector<Kortti> getKortti(Vector<Kortti> kasi);

	/** Värivalinta, joka kysytään älyltä mustan kortin jälkeen. */
	abstract Vari getVari();
	
	/** Palauttaa kokonaisluvun vaihtoehdon hyvyydestä. */
	abstract int hyvyys(Vector<Kortti> lyotava, Vector<Kortti> jatettava);
	
	/** Kutsuu kaikkia mahdollisia lyötäviä hyvyys() -funktiossa.*/
	public Vector<Kortti> paras(Vector<Kortti> kasi, Kortti poisto, Vari vari){
		
        Vector<Kortti> paraslyotava = new Vector<Kortti>();
        int parashyvyys = Integer.MIN_VALUE;
        
        for (Kortti k : kasi) {
        	
        	if(Peli.voiLyoda(k, poisto, vari, kasi.size()==1)){
        		
            	Vector<Kortti> ehdokaslyotava = new Vector<Kortti>();        	
            	Vector<Kortti> ehdokasjatettava = new Vector<Kortti>(kasi);    
            	
        		ehdokaslyotava.add(k);
        		ehdokasjatettava.remove(k);
        		
                for (Kortti lk : kasi) {
                    if(!k.equalsP(lk) && Peli.voiLyodaLisäksi(lk, k)){
                    	ehdokaslyotava.add(lk);
                    	ehdokasjatettava.remove(lk);
                    }
                }
	        	
                //kaikki mahdolliset permutaatiot, jotka loppuvat eri korttiin
                for (int rot = 0; rot < Math.max(1, ehdokaslyotava.size() - 1); rot++) {
                	
                	if(rot>0) {
	                	int alkuperainenkoko=ehdokaslyotava.size();
	                	Kortti viimeinen = ehdokaslyotava.lastElement();
	                	ehdokaslyotava.remove(ehdokaslyotava.size()-1);
	                	ehdokaslyotava.insertElementAt(viimeinen, 1);
	                	assert(alkuperainenkoko == ehdokaslyotava.size());
                	}
                	
	                int ehdokashyvyys = hyvyys(ehdokaslyotava, ehdokasjatettava);
		        	
		        	if(ehdokashyvyys>parashyvyys) {
		        		paraslyotava=new Vector<Kortti>(ehdokaslyotava);
		        		parashyvyys=ehdokashyvyys;
		        	}
                }
        	}
        }
        
        return paraslyotava;
        
	}

}