import java.util.Vector;

public class Pelaaja  {

    public void nosta(int m){
        for(int i=0; i<m; i++){
            kasi.add(Pöytä.pöytä.nosta());
        }
    }

    public void tapahtuma(Tapahtuma logi){
        aly.tapahtuma(logi);
    }

    private void lyo(Vector<Kortti> lyotava){
        for(Kortti k : lyotava){
            Pöytä.pöytä.lyö(k);
        }
    }

    // Lyö kaikki kortit pinoon. Kutsutaan pelin loppuessa.
    public void lyoKaikki()  {
        lyo(kasi);
        kasi.clear();
    }

    private Vector<Kortti> kasi = new Vector<>();
    private Tekoäly aly;
    private int id;
    private static int counter = 0;

    public Pelaaja(Tekoäly äly){
        this.id=(counter++);
       	this.aly = äly;        
    }

    public String toString(){
        return Integer.toString(id);
    }
    
    Tekoäly getÄly() {
    	return aly;
    }

    Tapahtuma teeVuoro(){

        int nosto = 0;
        Vector<Kortti> lyotava = new Vector<Kortti>();
        Vari vari = null;
        Teko tapahtuma=null;
        
        if(!Pöytä.pöytä.jaettu()){
        	
        	tapahtuma=Teko.JAK;
            
            // Nostetaan aloituskortti niin kauan kun se ei ole musta
            Kortti k;
            do{
                nosto++;
                k = Pöytä.pöytä.nosta();
                lyotava.add(k);                
            } while(k.isMusta());     

        }else{
        	
        	

            // Yritetään kolme kertaa, ja nostetaan toisesta kerrasta lähtien.
            for(;; nosto++){  
                  
                if(nosto>0){
                    nosta(1);
                }

                // Jos löytyy lyötävä, siirretään se pöytään
                lyotava = aly.getKortti(kasi);
                if(!lyotava.isEmpty()){
                    kasi.removeAll(lyotava);
                    if(lyotava.get(0).isMusta()){
                        vari = aly.getVari();
                    }
                    if(kasi.size()==1) {
                    	tapahtuma=Teko.UNO;
                    }else if(kasi.size()==0){
                    	tapahtuma=Teko.VTO;
                    }else {
                    	tapahtuma=Teko.LÖI;
                    }
                    	
                    break;
                }
                if(nosto==3){
                	tapahtuma=Teko.OHI;
                    break;
                }

            }
        }

        lyo(lyotava);

        // Kirjataan tapahtuma (liittyy läheisesti pelaajaan)
        return new Tapahtuma(tapahtuma, id, lyotava, vari, nosto, kasi.size());
    }

}
