
public class Tilannekuva {
	
    private int pelaajamäärä=0;
    private int poistunut=0;
    private int suunta=1;
    private int nopeus=1;

    
    private int pelaajaindex=0;
    private int nextpelaaja=0;
    
	private Kortti poisto;
    
    // Pelaajien oletettu korttimäärä
    private int korttimaara[];
    
    // Kortti, joka pelaajilta puuttuu
    private Kortti puuttuva[];
    
    private Kortti edellinen;
    
    // Muuttaa kuvaa tapahtuman mukaan
	public void tapahtuma(Tapahtuma logi){    	
    	
        if(!logi.lyonti.isEmpty()){
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
}
