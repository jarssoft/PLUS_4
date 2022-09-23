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
            this.poisto=logi.lyonti.lastElement();
        }
    }

    int hyvyys(Vector<Kortti> ehdokaslyotava, Vector<Kortti> ehdokasjatettava) {
    	
    	// Pienempi käsi on parempi (-0.17)
        
    	int ehdokashyvyys = ehdokaslyotava.size() * -2;
    	
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään (-0.01, -1)
    	
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
		
		// Jätetään viimeiseksi kaksi samanväristä (0, +1) (0, 0)
		if(ehdokasjatettava.size()==2) {
			if(!ehdokasjatettava.get(0).isMusta() && !ehdokasjatettava.get(1).isMusta()) {
				if(ehdokasjatettava.get(0).getVari() == ehdokasjatettava.get(1).getVari()) {
					ehdokashyvyys+=3;
				}
			}
		}
		
		// Yritetään aina vaihtaa väriä (-0.01, 0), (0, -0.3)

		if(!viimeinen.isMusta() && !poisto.isMusta()) {
    		if(viimeinen.getVari().toString() != this.poisto.getVari().toString()) {
    			ehdokashyvyys+=1;
    		}
		}
		
		// Jos käteen jää yli kaksi korttia, yritetään poistaa yleisin väri. (+0.06, +4), (+0.03, +3)
		/*
		if(ehdokasjatettava.size()>2) {        			
			//ratkaistaan yleisimmän värin korttimäärä
			int maarat[] = {0,0,0,0};
            for (Kortti jk : ehdokasjatettava) {
            	if(!jk.isMusta()) {
                    maarat[jk.getVari().ordinal()]++;
            	}
            }
            int max = maarat[0];
            //Loop through the array  
            for (int i = 1; i < maarat.length; i++) {  
                //Compare elements of array with max  
               if(maarat[i] > max) {  
                   max = maarat[i];
               }
               
            }
            //rangaistaan määrän mukaisesti
            ehdokashyvyys-=max;
		}*/
		
		// Ei jätetä mustia viimeisiksi (-0.02, -0.3)
		
		if(ehdokasjatettava.size()==1 
				&& ehdokasjatettava.firstElement().isMusta()) {
			ehdokashyvyys-=10;
		}
		if(ehdokasjatettava.size()==2 
				&& ehdokasjatettava.firstElement().isMusta() 
				&& ehdokasjatettava.lastElement().isMusta()) {
			ehdokashyvyys-=10;
		}
		if(ehdokasjatettava.size()>=3
				&& ehdokaslyotava.firstElement().isMusta()) {
			ehdokashyvyys-=1;
		}
		
		return ehdokashyvyys;
    	
    }
    
    // Pyytää tekoälyä tekemään valinnan.
    @Override
	public Vector<Kortti> getKortti(Vector<Kortti> kasi){
    
        assert(poisto!=null);
        assert(!kasi.isEmpty());      
        
        Vector<Kortti> paraslyotava = new Vector<Kortti>();
        int parashyvyys = -1000;

        for (Kortti k : kasi) {
        	            
        	if(Peli.voiLyoda(k, this.poisto, this.vari, kasi.size()==1)){
        		
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
	        	
                int ehdokashyvyys = hyvyys(ehdokaslyotava, ehdokasjatettava);
	        	
	        	if(ehdokashyvyys>parashyvyys) {
	        		paraslyotava=new Vector<Kortti>(ehdokaslyotava);
	        		parashyvyys=ehdokashyvyys;
	        	}
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
