import java.util.Vector;

// Ainut osa joka tekee päätöksiä.
public class Viisas extends Tekoäly {

	private Vari poistovari;
	private Kortti poisto;
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
    
    /** Palauttaa lyötävien ja jätettävien korttien yhdistelmän hyvyyden,
     *  kun pelitilanteesta ei tiedetä mitään muuta. */
    int hyvyys(Vector<Kortti> lyotava, Vari vari, Vector<Kortti> jatettava) {
    	    	
    	//System.out.println("Lyötävä: "+lyotava+", Vari: "+vari+", Jatettava: "+jatettava);
    	
    	Tilannekuva uusikuva = new Tilannekuva(kuva);
    	    	
		uusikuva.tapahtuma(new Tapahtuma(
				Teko.LÖI, 
				kuva.getNextVastustaja(), 
				lyotava, vari, lyotava.size()));
    	
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
		
		// Ei haluta antaa vuoroa vastustajalle, jolla on uuno
		
		if(uusikuva.getNextKorttimaara()<4) {
			hyvyys-=1;
		}
		if(uusikuva.getNextKorttimaara()<2) {
			hyvyys-=2;
		}
		
		// Annetaan plussa-kortti vastustajalle jolla on uuno
		
		if((!viimeinen.isMusta() && viimeinen.getMerkki() == Merkki.PLUS2) ||
					viimeinen.isPlus4()) {			
	    	
			// Selvitetään, mikä vastustaja saisi plussat
			Tilannekuva normikuva = new Tilannekuva(kuva);			
	    	Kortti SIN_N0 = Kortti.testiKortti(Vari.SININEN, Merkki.N0);
	    	Vector<Kortti> lyoty = new Vector<Kortti>();
			lyoty.add(SIN_N0);
	    	normikuva.tapahtuma(new Tapahtuma(
					Teko.LÖI, 
					kuva.getNextVastustaja(), 
					lyoty, null, 1));
	    	
			if(normikuva.getNextKorttimaara()<4) {
				hyvyys+=1;
			}
			if(normikuva.getNextKorttimaara()<2) {
				hyvyys+=2;
			}
		}
		
		if(viimeinen.isMusta()) {
			
			// Väri, joka on itsellä.
			
			boolean loytyyItselta = false;
	    	for(Kortti k: jatettava) {
	    		if(!k.isMusta()) {
	    			if(vari == k.getVari()) {
	    				loytyyItselta = true;
	    			}
	    		}
	    	}	
	    	
	    	// Väri, jota ei ole vastustajalla.
	    	
	    	boolean loytyyVastustajalta = true;

			if(uusikuva.getNextPuuttuva()!=null) {
				if(uusikuva.getNextPuuttuva().getVari()==vari)
					loytyyVastustajalta=false;
			}
			
			//System.out.println("Itse:"+loytyyItselta+", Vastus:"+loytyyVastustajalta);
			
			if(loytyyItselta) {
				if(!loytyyVastustajalta) {
					hyvyys+=1;
				}
			}else {
				hyvyys-=1;
			}

		}else {
			
			// Väri tai, jota ei ole vastustajalla.
			
			if(uusikuva.getNextPuuttuva()!=null) {
				if(uusikuva.getNextPuuttuva().getVari()==viimeinen.getVari()) {
					hyvyys+=2;
				}
				if(uusikuva.getNextPuuttuva().getMerkki()==viimeinen.getMerkki()) {
					hyvyys+=2;
				}
			}

		}
		
		return hyvyys;
    	
    }
    
    // Pyytää tekoälyä tekemään valinnan.
    @Override
	public Lyonti getKortti(Vector<Kortti> kasi){
    
        assert(poisto!=null);
        assert(!kasi.isEmpty());      
    	
        return paras(kasi, poisto, poistovari);
    }

}
