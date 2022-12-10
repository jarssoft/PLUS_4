import java.util.Vector;

// Ainut osa joka tekee päätöksiä.
public class Viisas extends Tekoäly {

	private Vari vari;
	private Kortti poisto;
    
    private int pelaajamäärä=0;
    private int poistunut=0;
    private int suunta=1;
    private int nopeus=1;
    
    private int pelaajaindex=0;
    private int nextpelaaja=0;
    
    // Pelaajien oletettu korttimäärä
    private int korttimaara[];
    
    // Kortti, joka pelaajilta puuttuu
    private Kortti puuttuva[];
    
    private Kortti edellinen;

    // Tiedottaa pelaajalle tapahtumasta.
    @Override
	public void tapahtuma(Tapahtuma logi){    	
    	
        if(!logi.lyonti.isEmpty()){
            this.vari=logi.vari;
            this.poisto=logi.lyonti.lastElement();
        }
        
        if(logi.tapahtuma == Teko.JAK) {
        
        	poistunut=0;
        	pelaajaindex = logi.pelaaja;
            pelaajamäärä = logi.pelaaja + 1;
            suunta=1;
            nopeus=1;
            
            korttimaara = new int[pelaajamäärä];
            puuttuva = new Kortti[pelaajamäärä];
            
            for (int i = 0; i < korttimaara.length; i++) {
            	korttimaara[i] = 7;
            }
        	
        }else {
        	
        	if(logi.pelaaja != nextpelaaja) {        	
        		System.out.println(logi.pelaaja + " != " + nextpelaaja + "############################");
        	}
        	//assert(logi.pelaaja == nextpelaaja);
        	
        	pelaajaindex = logi.pelaaja;
        	//pelaajaindex = getNextVastustaja();
	
        }
        
        int muutos = logi.nostot - logi.lyonti.size();
        korttimaara[pelaajaindex] += muutos;
        //System.out.println(pelaajaindex+" puuttuu "+puuttuva[pelaajaindex]);
        
        if(muutos>0) {
        	puuttuva[pelaajaindex]=null;
        }
        
        if(!this.poisto.isMusta() && this.poisto.getMerkki() == Merkki.SUUNNANVAIHTO) {        	
        	suunta *= (int)Math.pow(-1, logi.lyonti.size());
        }
        
        nopeus = 1;
        
        if(logi.tapahtuma == Teko.VTO) {
        	assert(korttimaara[pelaajaindex] == 0);
        	korttimaara[pelaajaindex] = 0;
        	poistunut++;
        }else {
        	assert(korttimaara[pelaajaindex] > 0);
        }
        
        if(poistunut<pelaajamäärä) {
        	
	        if(!logi.lyonti.isEmpty()){
		        if(!this.poisto.isMusta()){
		    		if(this.poisto.getMerkki() == Merkki.OHITUS) {        	
		    			nopeus = 1 + logi.lyonti.size();
		    		}
		    		if(this.poisto.getMerkki() == Merkki.PLUS2) {  
		    			int nextvastustaja=getNextVastustaja();
		    			korttimaara[nextvastustaja] += 2 * logi.lyonti.size();
		    			puuttuva[nextvastustaja]=null;
		    			nopeus = 2;
		    		}
		        }else {
		        	if(this.poisto.isPlus4()){
		        		int nextvastustaja=getNextVastustaja();
		        		korttimaara[nextvastustaja] += 4;
		        		puuttuva[nextvastustaja]=null;
		        		nopeus = 2;
		        	}
		        }
	        }else {
	        	if(!this.poisto.isMusta()) {
	        		puuttuva[pelaajaindex]=edellinen;
	        	}
	        }
        
        	nextpelaaja = getNextVastustaja();
        }
        
        edellinen = poisto;

    }
    
    int numberOfPlayers() {
    	return pelaajamäärä;
    }
    
    int getPelaajaIndex() {
    	return pelaajaindex;
    }
    
    int getNextVastustaja() {
    	
    	assert(poistunut<pelaajamäärä);
    	
    	//lasketaan tyhjät
    	int hypyt=0;
    	for(int i = pelaajaindex + suunta; i != pelaajaindex + suunta * (nopeus + hypyt + 1); i = i + suunta) {
    		if(korttimaara[Math.floorMod(i, pelaajamäärä)] == 0) {
    			hypyt++;
    		}
    	}
    	    	
    	return Math.floorMod(pelaajaindex + suunta * (nopeus + hypyt), pelaajamäärä);    	
    }
    
    /*
    int hyvyys(Vector<Kortti> lyotava, Vector<Kortti> jatettava, int seuraavankorttimäärä) {
    
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
        
        Vector<Kortti> lyotava = paras(kasi, poisto, vari);
        
        if(!lyotava.isEmpty() && lyotava.get(0).isMusta()) {
        	vari=Vari.PUNAINEN;
	    	for(Kortti k: kasi) {
	    		if(!k.isMusta()) {
	    			vari = k.getVari();
	    		}
	    	}
        }
    	
        return lyotava;
    }
    
    // Värivalinta, joka kysytään älyltä mustan kortin jälkeen
    @Override
	public Vari getVari(){
    	return vari;
    }

}
