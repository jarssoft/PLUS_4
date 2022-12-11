import java.util.Vector;

// Ainut osa joka tekee päätöksiä.
public class Viisas extends Tekoäly {

	private Vari poistovari;
	private Kortti poisto;
	private Vari lyotavavari;
	private Tilannekuva kuva = new Tilannekuva();

    // Tiedottaa pelaajalle tapahtumasta.
    @Override
	public void tapahtuma(Tapahtuma logi){    
    	
        if(!logi.lyonti.isEmpty()){
            this.poistovari=logi.vari;
            this.poisto=logi.lyonti.lastElement();
        }
        kuva.tapahtuma(logi);
    }
    
    int numberOfPlayers() {
    	return kuva.numberOfPlayers();
    }
    
    int getNextVastustaja() {
    	return kuva.getNextVastustaja(); 	
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
        
        Vector<Kortti> lyotava = paras(kasi, poisto, poistovari);
        
        if(!lyotava.isEmpty() && lyotava.get(0).isMusta()) {
        	lyotavavari=Vari.PUNAINEN;
        	
	    	for(Kortti k: kasi) {
	    		if(!k.isMusta()) {
	    			lyotavavari = k.getVari();
	    		}
	    	}

	    	// Väri, jota vastustajalla ei ole
	    	
	    	Tilannekuva uusikuva = new Tilannekuva(kuva);

			uusikuva.tapahtuma(new Tapahtuma(Teko.LÖI, 
					kuva.getNextVastustaja(), 
					lyotava, lyotavavari, 1));
			
			if(uusikuva.getNextPuuttuva()!=null) {
				lyotavavari=uusikuva.getNextPuuttuva().getVari();
			}

        }
    	
        return lyotava;
    }
    
    // Värivalinta, joka kysytään älyltä mustan kortin jälkeen.
    @Override
	public Vari getVari(){
    	return lyotavavari;
    }

}
