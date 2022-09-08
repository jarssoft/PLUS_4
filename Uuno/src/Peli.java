import java.util.Vector;
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
    
    // Palautta true, jos saman pelaajan lyömän kortin %poisto päälle voi laittaa vielä kortin %lyotava.
    public static boolean voiLyodaLisäksi(final Kortti lyotava, final Kortti poisto) {
        if(lyotava.isMusta() || poisto.isMusta()){
            return false;
        }
        return lyotava.getMerkki()==poisto.getMerkki();
    }



    // Pelisilmukka perustuu linkitetyn listan iteraattoriin.
    // Se huolehtii pelaajien välisestä vuorottelusta ja tiedottamisesta.
    public static void peli(LinkedList<Pelaaja> pelaajat) {

        KööriIterator<Pelaaja> ki = new KööriIterator<Pelaaja>(pelaajat);
        while(true){

            // Suoritetaan vuoro

            final Logi logi = ki.next().teeVuoro();

            // Tiedotetaan kaikille pelaajille

            for(Pelaaja p: pelaajat){
                p.tapahtuma(logi);
            }

            Tilasto.tilastoi(logi);            

            // Kortit menettänyt pelaaja poistuu

            if(logi.kasikoko==0){
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
        
    }
    
    public static void main(String[] args) {

        LinkedList<Pelaaja> pelaajat = new LinkedList<Pelaaja>();      
        for(int p=0;p<4;p++){
            pelaajat.add(new Pelaaja());
        }

        for(int t=0;t<100;t++){
        	//alustetaan
        	Pöytä.pöytä.reset();
            for(Pelaaja p: pelaajat){
                p.nosta(7);
            }            
            assert(Pöytä.pöytä.size() == 108 - pelaajat.size()*7);
            peli(new LinkedList<Pelaaja>(pelaajat));
            assert(Pöytä.pöytä.size() == 108);

        }
        Tilasto.yhteenveto();

    }

}
