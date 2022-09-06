import java.util.Collections;
import java.util.Stack;

// Pöydällä oleva korttipino, josta voi ottaa tai laskea kortteja.
public class Pino {

    protected Stack<Kortti> stack = new Stack<>();

    // Palauttaa intanssin, jossa on kaikki kortit
    public static Pino uusiPakka() {
        Pino pakka = new Pino();
        pakka.stack = Kortti.kortit();
        return pakka;
    }

    public Kortti pop(){
        return stack.pop();
    }

    public void push(Kortti kortti){
        stack.push(kortti);
    }

    public void sekoita(){
        Collections.shuffle(stack);
    }

    public int size(){
        return stack.size();
    }
}
