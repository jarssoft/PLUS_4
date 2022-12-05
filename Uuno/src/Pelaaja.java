import java.util.Vector;

public class Pelaaja  {

    public void nosta(int m){
        for(int i=0; i<m; i++){
            kasi.add(Pöytä.pöytä.nosta());
        }
    }

    public void tapahtuma(Logi logi){
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

    public Pelaaja(){
        this.id=(counter++);
        if(this.id==0) {
        	aly = new Viisas();        
        }else {
        	aly = new Tyhmä();
        }
    }

    public String getNimi(){
        return Integer.toString(id);
    }

    Logi teeVuoro(){

        int nosto = 0;
        Vector<Kortti> lyotava = new Vector<Kortti>();
        Vari vari = null;
        
        if(!Pöytä.pöytä.aloitettu()){            
            
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
                    break;
                }
                if(nosto==3){
                    break;
                }

            }
        }

        lyo(lyotava);

        // Kirjataan tapahtuma (liittyy läheisesti pelaajaan)
        return new Logi(id, lyotava, vari, nosto, kasi.size());
    }

}
