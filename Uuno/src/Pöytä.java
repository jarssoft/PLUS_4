

// Pöydällä olevat kortit. Tämä on lähellä Queue-rajapinnan toteutusta,
// jossa ensimmäiseksi lyödyt kortit tulevat ensimmäisenä nostetuksi.
// Kortit kuitenkin sekoitetaan jonossa, joten FIFO ei ole täydellinen.
public class Pöytä {

    static Pöytä pöytä = new Pöytä();

    private PinoYlöspäin poistopakka;
    private Pino nostopakka;

    // Luo nosto- ja poistopakan aloituskortilla.
    public Pöytä(){
        this.nostopakka = Pino.uusiPakka();
        this.nostopakka.sekoita();
        this.poistopakka = new PinoYlöspäin();
    }

    // Nostaa kortin ja sekoittaa pakan tarvittaessa uudelleen
    public Kortti nosta(){
        if(nostopakka.size()==0){
            final int yhteensa = poistopakka.size();
            final Kortti paallimmainen = poistopakka.pop();
            reset();            
            poistopakka.push(paallimmainen);
            assert(poistopakka.size()==1);
            assert(poistopakka.peek()==paallimmainen);
            assert(nostopakka.size()==yhteensa-1);

            System.out.println("Sekoitettiin uudestaan.");
        }
        return this.nostopakka.pop();
    }

    public void lyö(Kortti kortti){
        this.poistopakka.push(kortti);
    }

    public Kortti peek(){
        return this.poistopakka.peek();
    }

    public boolean jaettu(){
        return this.poistopakka.size() > 0;
    }

    public void reset(){
        while(poistopakka.size()>0){
            nostopakka.push(poistopakka.pop());
        }
        nostopakka.sekoita();
    }

    public int size(){
        return this.poistopakka.size()+this.nostopakka.size();
    }

}
