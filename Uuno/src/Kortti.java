import java.util.Stack;

enum Vari {
        PUNAINEN, KELTAINEN, VIHREA, SININEN;
}

enum Merkki {
        N0, N1, N2, N3, N4, N5, N6, N7, N8, N9, 
        OHITUS, SUUNNANVAIHTO, PLUS2;
}
 
/**
Luokkaa käyttävällä komponentilla on pääsy vain saamaan joukon kaikista korteista,
ja metodit tutkimaan yksittäisten korttien ominaisuuksia). Joukon järjestyksellä ei ole 
merkitystä ja se voi vaihdella.

Sisäinen toteutus mallintaa kortteja numerolla 0-123 (getTaulukko()), joka viittaa fyysiseen korttiin. 
Kortit on hahmotettu selkeyden vuoksi 14x8 taulukossa. Vaikka samanlaiset kortit
voi sisäisessä toteutuksessa erottaa numerona, niin ulospäin ne näyttävät samoilta.
**/
public class Kortti {

    final static int numberOfCards = 108+4;

    private static Kortti[] getTaulukko(){

        Kortti[] kortit = new Kortti[numberOfCards];

        
        for (int ki=0; ki<numberOfCards;ki++) {
            kortit[ki] = new Kortti(ki);

            /*System.out.print(kortit[ki].toString()+"  ");
            if(ki%14==13){
                System.out.println();
            }*/
            assert(kortit[ki].toString().length()==6);
        }

        assert(kortit[100] != null);
        assert(kortit[13] != null);

        assert(kortit[13].isMusta());
        assert(kortit[13+14].isMusta());
        assert(!kortit[8].isMusta());

        assert(kortit[numberOfCards-1].isPlus4());

        assert(kortit[4*14].isTyhja());
        assert(!kortit[2*14].isTyhja()); 

        assert(kortit[8].getVari() == Vari.PUNAINEN);
        assert(kortit[8+14].getVari() == Vari.KELTAINEN);

        assert(kortit[0].getMerkki() == Merkki.N0);
        assert(kortit[9].getMerkki() == Merkki.N9);
        assert(kortit[9+14*5].getMerkki() == Merkki.N9);
        assert(kortit[11].getMerkki() == Merkki.SUUNNANVAIHTO);

        assert(testiKortti(Vari.KELTAINEN, Merkki.SUUNNANVAIHTO).getMerkki() == Merkki.SUUNNANVAIHTO); 
        assert(testiKortti(Vari.SININEN, Merkki.N5).getVari() == Vari.SININEN);
        assert(testiKorttiPlus4().isPlus4());
        assert(testiKorttiJokeri().isJokeri());
        return kortit;

    }

    // Luodaan pino, jossa on kaikkien korttien oliot    
    public static Stack<Kortti> kortit(){

        Stack<Kortti> kokopakka = new Stack<Kortti>();
        for (Kortti kortti: getTaulukko()) {
            if(!kortti.isTyhja()){
                kokopakka.push(kortti);
            }
        }

        assert(kokopakka.size()==108);

        return kokopakka;
    }

    private final int korttinro;

    private Kortti(final int korttinro) {
    	assert(korttinro>=0 && korttinro<numberOfCards);
        this.korttinro = korttinro ;
    } 
    
    public static Kortti testiKortti(Vari vari, Merkki merkki) {
    	return new Kortti(vari.ordinal()*14 + merkki.ordinal());    	
    }
    
    public static Kortti testiKorttiPlus4() {
    	return new Kortti(14*4 + 13);    	
    }
    
    public static Kortti testiKorttiJokeri() {
    	return new Kortti(13);    	
    }
    
    private int sarake(){
        return korttinro % 14;
    }

    private int rivi(){
        return korttinro / 14;
    }

    public boolean isTyhja(){
        return sarake() == 0 && rivi()>=4;
    }

    public boolean isMusta(){
        assert(!isTyhja());
        return sarake() == 13;
    }

    public boolean isJokeri(){
        assert(!isTyhja());
        return isMusta() && rivi()<4;
    }

    public boolean isPlus4(){
        assert(!isTyhja());
        return isMusta() && rivi()>=4;
    }

    public Vari getVari(){
        assert(!isMusta() && !isTyhja());
        return Vari.values()[rivi()%4];
    }

    public Merkki getMerkki(){
        assert(!isMusta() && !isTyhja());
        return Merkki.values()[sarake()];
    }

    // Palauttaa kuusimerkkisen tunnuksen, joka kuvaa korttia.
    public String toString() {
        if(isTyhja()){
            return "------";
        }
        if(isMusta()){
            if(isJokeri()){
                return "JOKERI";
            }else{
                return "PLUS_4";
            }
        }else{
            return getVari().name().substring(0, 3) + 
                    "_" +
                    getMerkki().name().substring(0, 2);
        }
    }

}
