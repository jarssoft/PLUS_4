import java.util.Vector;

// Ainut osa joka tekee päätöksiä.
public class Viisas implements Tekoäly {

    protected Vari vari;
    protected Kortti poisto;
    
    protected int pelaajamäärä=0;
    protected int suunta=1;
    protected int nopeus=1;
    
    private int pelaajaindex=0;
    private int highestindex=0;
    private int lowestindex=0;
    
    String oikea, vasen;

    // Tiedottaa pelaajalle tapahtumasta.
    @Override
	public void tapahtuma(Logi logi){    	
    	
    	
    	
        if(!logi.lyonti.isEmpty()){
            this.vari=logi.vari;
            this.poisto=logi.lyonti.lastElement();
        }
        
        if(pelaajamäärä==0) {
        	
            pelaajamäärä=logi.pelaaja+1;
        	
        }else {
        	
        	pelaajaindex += suunta*nopeus;
        	pelaajaindex = Math.floorMod(pelaajaindex, pelaajamäärä);
	
        }
        
        
        if(!this.poisto.isMusta() && this.poisto.getMerkki() == Merkki.SUUNNANVAIHTO) {        	
        	suunta *= (int)Math.pow(-1, logi.lyonti.size());
        }
        
        nopeus=1;
        if(!this.poisto.isMusta()){
    		if(this.poisto.getMerkki() == Merkki.OHITUS) {        	
    			nopeus = 1 + logi.lyonti.size();
    		}
    		if(this.poisto.getMerkki() == Merkki.PLUS2) {        	
    			nopeus = 2;
    		}
        }else {
        	if(this.poisto.isPlus4()){
        		nopeus = 2;
        	}
        }
        
    }
    
    int numberOfPlayers() {
    	return pelaajamäärä;
    }
    
    int getPelaajaIndex() {
    	return pelaajaindex;
    }
    
    int getNextVastustaja() {   	
    	return Math.floorMod(pelaajaindex + suunta, pelaajamäärä);    	
    }
    
    
    
    /*
    int hyvyys(Vector<Kortti> lyotava, Vector<Kortti> jatettava, Vastustaja ohitettava, Vastustaja seuraava) {
    
    	//ei anneta vuoroa sille, jolla on vähän kortteja
    	
    	//pyritään antamaan puuttuva kortti
    	  
    	//annetaan plussakortti, jos vähän kortteja
    	 
    }*/
    
    /** Palauttaa lyötävien ja jätettävien korttien yhdistelmän hyvyyden,
     *  kun pelitilanteesta ei tiedetä mitään muuta. */
    int hyvyys(Vector<Kortti> lyotava, Vector<Kortti> jatettava) {
    	
    	// Pienempi käsi on parempi (-0.17)
        
    	int hyvyys = lyotava.size() * -2;
    	
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään (-0.01, -1)
    	
		Kortti viimeinen = lyotava.lastElement();
		if(!viimeinen.isMusta()) {
            for (Kortti jk : jatettava) {
            	if(!jk.isMusta()) {
                    if(viimeinen.getVari() == jk.getVari()){
                    	hyvyys+=1;
                    }
            	}
            }
		}
		
		// Yritetään aina vaihtaa väriä (-0.01, 0), (0, -0.3)

		if(!viimeinen.isMusta() && !poisto.isMusta()) {
    		if(viimeinen.getVari().toString() != this.poisto.getVari().toString()) {
    			hyvyys+=1;
    		}
		}
		
		// Jos käteen jää yli kaksi korttia, yritetään poistaa yleisin väri. (+0.06, +4), (+0.03, +3)
		/*
		if(jatettava.size()>2) {        			
			//ratkaistaan yleisimmän värin korttimäärä
			int maarat[] = {0,0,0,0};
            for (Kortti jk : jatettava) {
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
            hyvyys-=max;
		}
		
		
		// Jätetään viimeiseksi kaksi samanväristä (0, +1) (0, 0)
		
		if(jatettava.size()==2) {
			if(!jatettava.get(0).isMusta() && !jatettava.get(1).isMusta()) {
				if(jatettava.get(0).getVari() == jatettava.get(1).getVari()) {
					//hyvyys+=3;
				}
			}
		}
		*/
		
		// Säästetään mustia tosipaikan varalle
		
		if(jatettava.size()>=3
				&& lyotava.firstElement().isMusta()) {
			hyvyys-=1;
		}
		
		// Ei jätetä mustia viimeisiksi (-0.02, -0.3)
		
		if(jatettava.size()==1 
				&& jatettava.firstElement().isMusta()) {
			hyvyys-=10;
		}
		if(jatettava.size()==2 
				&& jatettava.firstElement().isMusta() 
				&& jatettava.lastElement().isMusta()) {
			hyvyys-=10;
		}
		
		return hyvyys;
    	
    }
    
    // Pyytää tekoälyä tekemään valinnan.
    @Override
	public Vector<Kortti> getKortti(Vector<Kortti> kasi){
    
        assert(poisto!=null);
        assert(!kasi.isEmpty());      
        
        Vector<Kortti> paraslyotava = new Vector<Kortti>();
        int parashyvyys = Integer.MIN_VALUE;

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
