import java.util.Collections;
import java.util.LinkedList;

public class Peli {

    // Palauttaa true, jos kortin %lyotava voi laittaa, kun poistopinossa 
    // on lyonti %edellinen.
    public static boolean voiLyoda(final Kortti lyotava, final Lyonti edellinen, boolean uuno) {
        if(lyotava.isMusta()){
            return !uuno;
        }
        if(edellinen.isMusta()){
            return lyotava.getVari() == edellinen.getVari();
        }
        return lyotava.getVari() == edellinen.getVari()  
                || lyotava.getMerkki() == edellinen.getMerkki();
    }
    
    // Palauttaa true, jos saman pelaajan lyömän kortin %poisto päälle voi laittaa vielä kortin %lyotava.
    public static boolean voiLyodaLisäksi(final Kortti lyotava, final Kortti poisto) {
        if(lyotava.isMusta() || poisto.isMusta()){
            return false;
        }
        return lyotava.getMerkki()==poisto.getMerkki();
    }

    // Pelisilmukka perustuu linkitetyn listan iteraattoriin.
    // Se huolehtii pelaajien välisestä vuorottelusta ja tiedottamisesta.
    public static void peli(LinkedList<Pelaaja> pelaajat) {

    	System.out.println("------------------------------------------");
    	
    	// Alustetaan
    			
    	boolean jaa=true;
    	Pöytä.pöytä.reset();
        for(Pelaaja p: pelaajat){
            p.nosta(7);
        }            
        assert(Pöytä.pöytä.size() == 108 - pelaajat.size()*7);
                   
        // Pelaajat vaihtavat paikkoja ennen jokaista peliä
        
    	Collections.shuffle(pelaajat);
    	int id=0;
        for(Pelaaja p: pelaajat){
            p.setId(id++);
        }   
                
    	assert(pelaajat != null);
        KööriIterator<Pelaaja> ki = new KööriIterator<Pelaaja>(pelaajat);
        while(true){

        	Pelaaja pel=ki.next();
        	Tapahtuma logi;
        	
            // Suoritetaan vuoro
        	do {
        	
	            logi = pel.teeVuoro(jaa);
	
	            // Tiedotetaan
	
	            for(Pelaaja p: pelaajat){
	                p.tapahtuma(logi);
	            }
	
	            Tilasto.tilastoi(pel, logi);
	            
	            //jako on suoritettu, kun ei musta
	            
	            if(logi.tapahtuma==Teko.JAK && !logi.lyonti.isMusta()) {
	            	jaa=false;
	            }
	            
        	} while(jaa);

            // Korttinsa menettänyt pelaaja poistuu

            if(logi.tapahtuma==Teko.VTO){
                ki.remove();
                if(pelaajat.size()==0){
                    break;
                }
            }

            // Käsitellään erikoiskortit
                
            if(!logi.lyonti.isEmpty()){
                Lyonti lyotava=logi.lyonti;
                if(!lyotava.isMusta()){
                    if(lyotava.getMerkki()==Merkki.OHITUS){
                        ki.ohitus(lyotava.size());
                    }
                    if(lyotava.getMerkki()==Merkki.SUUNNANVAIHTO){
                        ki.suunnanvaihto(lyotava.size());
                    }
                    if(lyotava.getMerkki()==Merkki.PLUS2){
                        Pelaaja spelaaja = ki.next();
                        spelaaja.nosta(lyotava.size()*2);
                    }
                }else{
                    if(lyotava.isPlus4()){
                        Pelaaja spelaaja = ki.next();
                        spelaaja.nosta(4);
                    }
                }
            }
        }
        
        for(Pelaaja p: pelaajat){
            p.lyoKaikki();
        }
        pelaajat.clear();
        
        assert(Pöytä.pöytä.size() == 108);
        
    }
    
    public static void main(String[] args) {

        LinkedList<Pelaaja> pelaajat = new LinkedList<Pelaaja>();        
        pelaajat.add(new Pelaaja(new Viisas()));
        pelaajat.add(new Pelaaja(new Tyhmä()));
        //pelaajat.add(new Pelaaja(new Käyttäjä()));

        for(int t=0;t<10000;t++){
            peli(new LinkedList<Pelaaja>(pelaajat));
        }
        
        Tilasto.yhteenveto();

    }

}
