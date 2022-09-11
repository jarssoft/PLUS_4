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
        int parashyvyys = -1000;

        for (Kortti k : kasi) {
        	
        	Vector<Kortti> ehdokaslyotava = new Vector<Kortti>();        	
        	Vector<Kortti> ehdokasjatettava = new Vector<Kortti>(kasi);
        	int ehdokashyvyys = 0;        
            
        	if(Peli.voiLyoda(k, this.poisto, this.vari, kasi.size()==1)){
        		ehdokaslyotava.add(k);
        		ehdokasjatettava.remove(k);
                for (Kortti lk : kasi) {
                    if(k != lk && Peli.voiLyodaLisäksi(lk, k)){
                    	ehdokaslyotava.add(lk);
                    	ehdokasjatettava.remove(k);
                    }
                }
            }
        	
        	// Pienempi käsi on parempi
        	ehdokashyvyys+=10-ehdokaslyotava.size()*2;
        	
        	// Nollakäsi ei hyvä
        	if(!ehdokaslyotava.isEmpty()) {
        		ehdokashyvyys+=10*2;
        		
        		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
        		Kortti viimeinen = ehdokaslyotava.lastElement();
        		if(!viimeinen.isMusta()) {
	                for (Kortti jk : ehdokasjatettava) {
	                	if(!jk.isMusta()) {
		                    if(viimeinen.getVari() == jk.getVari()){
		                    	ehdokashyvyys+=1;
		                    }
	                	}
	                }
        		}
        		
        		// Jätetään viimeiseksi kaksi samanväristä
        		if(ehdokasjatettava.size()==2) {
        			if(!ehdokasjatettava.get(0).isMusta() && !ehdokasjatettava.get(1).isMusta()) {
        				if(ehdokasjatettava.get(0).getVari() == ehdokasjatettava.get(1).getVari()) {
        					ehdokashyvyys+=3;
        				}
        			}
        		}
        		
        		/*
        		if(ehdokasjatettava.size()>3) {        			
        			//ratkaistaan yleisin väri
        			int maarat[] = new int[4];
	                for (Kortti jk : ehdokasjatettava) {
	                	if(!jk.isMusta()) {
		                    maarat[jk.getVari().ordinal()]++;
	                	}
	                }
	                int max = maarat[0];
	                //Loop through the array  
	                for (int i = 0; i < maarat.length; i++) {  
	                    //Compare elements of array with max  
	                   if(maarat[i] > max) {  
	                       max = maarat[i];
	                   }
	                   
	                }
	                ehdokashyvyys+=max;
        		}
        		*/
        		
        		// Musta on pakko käyttää ennen viimeistä korttia
        		if(ehdokaslyotava.get(0).isMusta()) {
        			if(kasi.size()==1) {
        				ehdokashyvyys+=100;
        			}
        			if(kasi.size()==2) {
        				ehdokashyvyys+=5;
        			}      
        			if(kasi.size()>2) {
        				ehdokashyvyys-=kasi.size()+2;
        			}  
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
