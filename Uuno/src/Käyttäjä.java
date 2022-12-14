import java.util.Vector;
import java.util.Scanner;  // Import the Scanner class

public class Käyttäjä extends Tekoäly {

	private Lyonti poisto;
	private int numero=0;
	
	private Vector<Lyonti> vaihtoehdot = new Vector<Lyonti>();
	
    // Tiedottaa pelaajalle tapahtumasta.
    @Override
	public void tapahtuma(Tapahtuma logi){
        if(!logi.lyonti.isEmpty()){
        	poisto=logi.getLyonti();
        }
    }

	@Override
	Lyonti getKortti(Vector<Kortti> kasi) {	
	    
        assert(poisto.getKortit().lastElement()!=null);
        assert(!kasi.isEmpty());
        
        System.out.println("   Sinun käsi: "+kasi);
        
        
        numero=0;
        vaihtoehdot.clear();

        Lyonti l = paras(kasi, poisto);
        
        if(vaihtoehdot.isEmpty()) {
        	System.out.println("   Ei sopivaa korttia.");
        	return l;
        }
        
        System.out.println("   Valitse vaihtoehto:");
        
        Scanner myObj = new Scanner(System.in);
        
        int valinta = -1;
        
        while(valinta<0 || valinta>=vaihtoehdot.size()) {
        	valinta = Integer.valueOf(myObj.nextLine());
        }
        
        return vaihtoehdot.get(valinta);
	}

	@Override
	int hyvyys(Lyonti lyonti, Vector<Kortti> jatettava) {
		
		System.out.println("      "+(numero++)+": "+lyonti.getKortit()+" "+lyonti.getVari());
		vaihtoehdot.add(lyonti);
		
		return 0;
	}

}
