import java.util.Vector;
import java.util.Collections;
import java.util.LinkedList;

public class Peli {

    // Palauttaa true, jos kortin %lyotava voi laittaa, kun poistopinossa 
    // on %poisto, ja sanottu väri on %vari.
    public static boolean voiLyoda(final Kortti lyotava, final Kortti poisto, final Vari vari, boolean uuno) {
        if(lyotava.isMusta()){
            return !uuno;
        }
        if(poisto.isMusta()){
            return lyotava.getVari() == vari;
        }
        return lyotava.getVari()==poisto.getVari()  
                || lyotava.getMerkki()==poisto.getMerkki();
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

    	// Alustetaan
    	
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
        	
            // Suoritetaan vuoro

            final Tapahtuma logi = pel.teeVuoro();

            // Tiedotetaan

            for(Pelaaja p: pelaajat){
                p.tapahtuma(logi);
            }

            Tilasto.tilastoi(pel, logi);            

            // Korttinsa menettänyt pelaaja poistuu

            if(logi.tapahtuma==Teko.VTO){
                ki.remove();
                if(pelaajat.size()==0){
                    break;
                }
            }

            // Käsitellään erikoiskortit
                
            if(!logi.lyonti.isEmpty()){
                Vector<Kortti> lyotava=logi.lyonti;
                if(!lyotava.get(0).isMusta()){
                    if(lyotava.get(0).getMerkki()==Merkki.OHITUS){
                        ki.ohitus(lyotava.size());
                    }
                    if(lyotava.get(0).getMerkki()==Merkki.SUUNNANVAIHTO){
                        ki.suunnanvaihto(lyotava.size());
                    }
                    if(lyotava.get(0).getMerkki()==Merkki.PLUS2){
                        Pelaaja spelaaja = ki.next();
                        spelaaja.nosta(lyotava.size()*2);
                    }
                }else{
                    if(lyotava.get(0).isPlus4()){
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
        for(int p=0;p<4;p++){
            pelaajat.add(new Pelaaja(new Tyhmä()));
        }

        for(int t=0;t<1000;t++){
            peli(new LinkedList<Pelaaja>(pelaajat));
        }
        
        Tilasto.yhteenveto();

    }

}
