import java.util.ArrayList;
import java.util.Comparator;

public class Tilasto {
	
	static Comparator<Integer> comp = Comparator.naturalOrder();
	static int vuoro=0;
	static int pelaajia=0;
	static int pelaajiaYht=0;
	
	/** Nollan pelaamien vuorojen määrä. */	
	static ArrayList<Integer> nollanvuorot_l = new ArrayList<Integer>();
	static int nollanvuorot=0;
	
	/** Nollan pelissäoloaikana pelattujen vuorojen määrä. */
	static ArrayList<Integer> nollapelissa_l = new ArrayList<Integer>();
		
	/** Nollan sijoitus. */
	static ArrayList<Integer> nollansijoitus_l = new ArrayList<Integer>();
	
	/** Keskimääräinen käsikoko. */
	static ArrayList<Integer> käsikoko_l = new ArrayList<Integer>();

	/** Nostojen määrä. */
	static ArrayList<Integer> nostot_l = new ArrayList<Integer>();

	/** Uunojen määrä. */
	static ArrayList<Integer> uunot_l = new ArrayList<Integer>();
	
	static int pelit=0;
	
	static int suurinkäsikoko=0;
	
    public static void tilastoi(Pelaaja pelaaja, Tapahtuma logi) {
    	
    	//assert(vuoro>0 || logi.kasikoko==7);

		if(logi.tapahtuma==Teko.JAK) {
			vuoro=0;
			pelaajiaYht=pelaajia=logi.pelaaja+1;
			System.out.println("------------------------------------------");
			pelit++;
		}
		
    	System.out.print(vuoro+"\t");
		System.out.println(logi);
		//System.out.println(p.getÄly() instanceof Viisas);
		
		
    	if(pelaaja.getÄly() instanceof Viisas) {
    		nollanvuorot++;
    		if(logi.tapahtuma==Teko.VTO) {    			
    			nollanvuorot_l.add(nollanvuorot);
    			nollapelissa_l.add(vuoro);
    			nollansijoitus_l.add(pelaajiaYht-pelaajia+1);    			
    			nollanvuorot=0;    			
    		}
    		
        	//if(logi.kasikoko>suurinkäsikoko) {
        	//	suurinkäsikoko=logi.kasikoko;
        	//}
    	}
		
    	if(logi.tapahtuma==Teko.VTO) {
    		pelaajia--;			
    	}
    	vuoro++;
    }
        
    static String formatvalue(ArrayList<Integer> al) {
    	return String.valueOf(Tunnusluvut.keskiarvo(al)) 
    			+ " +-" + String.valueOf(Tunnusluvut.keskihajonta(al))
    			+ ", Med: " + Tunnusluvut.median(al, comp);
    }
    
    public static void yhteenveto() {    	
    	System.out.println("Pelien määrä: " + String.valueOf(pelit));
    	System.out.println("Nollan pelaamien vuorojen määrä: " + formatvalue(nollanvuorot_l));
    	System.out.println("Nollan pelissäoloaikana pelattujen vuorojen määrä: " +  formatvalue(nollapelissa_l));
    	System.out.println("Nollan sijoitus: " + formatvalue(nollansijoitus_l));
    	//System.out.println("Suurin käsikoko: " + suurinkäsikoko);
    }
}
