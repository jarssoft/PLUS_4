import java.util.Vector;

public abstract class Tekoäly {

	/** Tiedottaa pelaajalle tapahtumasta. */
	abstract void tapahtuma(Tapahtuma logi);

	/** Pyytää tekoälyä tekemään valinnan. */
	abstract Lyonti getKortti(Vector<Kortti> kasi);
	
	/** Palauttaa kokonaisluvun vaihtoehdon hyvyydestä. */
	abstract int hyvyys(Lyonti lyonti, Vector<Kortti> jatettava);
	
	/** Kutsuu kaikkia mahdollisia lyötäviä hyvyys() -funktiossa. */
	public Lyonti paras(Vector<Kortti> kasi, Lyonti poisto){
		
        Vector<Kortti> paraslyotava = new Vector<Kortti>();
        Vari parasvari = null;
        int parashyvyys = Integer.MIN_VALUE;
        
        for (Kortti k : kasi) {
        	
        	if(Peli.voiLyoda(k, poisto, kasi.size()==1)){
        		
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
                	
                	int ehdokashyvyys=Integer.MIN_VALUE;
                	
                	if(ehdokaslyotava.firstElement().isMusta()) {
                		for(Vari v : Vari.values()) {
                			ehdokashyvyys = hyvyys(new Lyonti(ehdokaslyotava, v), ehdokasjatettava);
        		        	if(ehdokashyvyys>parashyvyys) {        		        		
        		        		paraslyotava=new Vector<Kortti>(ehdokaslyotava);
        		        		parasvari=v;
        		        		parashyvyys=ehdokashyvyys;
        		        	}
                		}
                	}else {
                		ehdokashyvyys = hyvyys(new Lyonti(ehdokaslyotava, null), ehdokasjatettava);
    		        	if(ehdokashyvyys>parashyvyys) {
    		        		paraslyotava=new Vector<Kortti>(ehdokaslyotava);
    		        		parasvari=null;
    		        		parashyvyys=ehdokashyvyys;    		        		
    		        	}
                	}

                }
        	}
        }

        return new Lyonti(paraslyotava, parasvari);
        
	}

}