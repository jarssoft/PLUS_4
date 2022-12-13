/** Tekoälyn kuva pelin tapahtumista. */
public class Tilannekuva {
	
    private Kortti edellinen;
	    
    private int suunta=1;
    private int nopeus=1;

    private int pelaajaindex=0;
    private int nextpelaaja=0;
        
    /** Pelaajien määrä pelissä. */
    private int pelaajamäärä=0;
    private int poistunut=0;
    
    /** Pelaajien korttien määrä. */
    private int korttimaara[];
        
    /** Kortti, joka pelaajilta puuttuu. */
    private Kortti puuttuva[];
    
    public Tilannekuva() {}
    
    /** Kopio tilannekuvasta. */
    public Tilannekuva(Tilannekuva tk) {
    	
    	this.edellinen = tk.edellinen;
    	this.suunta = tk.suunta;
    	this.nopeus = tk.nopeus;
    	this.pelaajaindex = tk.pelaajaindex;
    	this.nextpelaaja = tk.nextpelaaja;
    	this.pelaajamäärä = tk.pelaajamäärä;
    	this.poistunut = tk.poistunut;
    	this.korttimaara = new int[this.pelaajamäärä];
        this.puuttuva = new Kortti[this.pelaajamäärä];
    	
        for (int i = 0; i < korttimaara.length; i++) {
        	this.korttimaara[i] = tk.korttimaara[i];
        	this.puuttuva[i] = tk.puuttuva[i];
        }
        
    }
    
    /** Muuttaa kuvaa tapahtuman mukaan. */
	public void tapahtuma(Tapahtuma logi){    	

		Kortti poisto = null; 
		
        if(!logi.lyonti.isEmpty()){
            poisto = logi.lyonti.lastElement(); 
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

        	assert(logi.pelaaja == nextpelaaja);
        	
        	pelaajaindex = logi.pelaaja;
	
        }
        
        int muutos = logi.nostot - logi.lyonti.size();
        korttimaara[pelaajaindex] += muutos;
        //System.out.println(pelaajaindex+" puuttuu "+puuttuva[pelaajaindex]);
        
        if(muutos>0) {
        	puuttuva[pelaajaindex]=null;
        }
        
        if(poisto!= null && !poisto.isMusta() && poisto.getMerkki() == Merkki.SUUNNANVAIHTO) {        	
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
		        if(!poisto.isMusta()){
		    		if(poisto.getMerkki() == Merkki.OHITUS) {        	
		    			nopeus = 1 + logi.lyonti.size();
		    		}
		    		if(poisto.getMerkki() == Merkki.PLUS2) {  
		    			int nextvastustaja=getNextVastustaja();
		    			korttimaara[nextvastustaja] += 2 * logi.lyonti.size();
		    			puuttuva[nextvastustaja]=null;
		    			nopeus = 2;
		    		}
		        }else {
		        	if(poisto.isPlus4()){
		        		int nextvastustaja=getNextVastustaja();
		        		korttimaara[nextvastustaja] += 4;
		        		puuttuva[nextvastustaja]=null;
		        		nopeus = 2;
		        	}
		        }
	        }else {
	        	if(edellinen!= null && !edellinen.isMusta()) {
		        	System.out.println("puuttuu "+edellinen);
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
    	assert(poistunut < pelaajamäärä);
    	
    	//lasketaan tyhjät
    	int hypyt=0;
    	for(int i = pelaajaindex + suunta; i != pelaajaindex + suunta * (nopeus + hypyt + 1); i = i + suunta) {
    		if(korttimaara[Math.floorMod(i, pelaajamäärä)] == 0) {
    			hypyt++;
    		}
    	}
    	    	
    	return Math.floorMod(pelaajaindex + suunta * (nopeus + hypyt), pelaajamäärä);    	
    }
    
    int getNextKorttimaara() {
    	return korttimaara[getNextVastustaja()];
    }

	public Kortti getNextPuuttuva() {
		return puuttuva[getNextVastustaja()];
	}
}
