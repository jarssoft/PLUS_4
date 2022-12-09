import java.util.ArrayList;
import java.util.Comparator;

/**
 - Voitot
 - Vuorot
 - Uunojen määrä
 - Ei voi laittaa mitään -tilanteet
 - Nostot
 - Keskimääräinen korttimäärä (ei voida laskea tässä)
 - Kerralla lyötyjen korttien määrä
 - Sama väri
 - Sama merkki
 */
public class Tilasto {
	
	static Comparator<Integer> comp = Comparator.naturalOrder();
	
	/** Uunojen määrä. */
	static ArrayList<Integer> vuoro_l = new ArrayList<Integer>();
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
	static int uunot = 0;
	
	static int nostot = 0;
	
	static int eivoilaittaa = 0;
	
	static int pelit=0;
	
	static int suurinkäsikoko=0;
	
	static int samaVäri=0;
	
	static int musta=0;
	
	static int samaMerkki=0;
	
	static int vastustajanväri=0;
	
	static Kortti viim;
	
    public static void tilastoi(Pelaaja pelaaja, Tapahtuma logi) {
    	
    	//assert(vuoro>0 || logi.kasikoko==7);

		if(logi.tapahtuma==Teko.JAK) {
			
			if(vuoro>0) {
				vuoro_l.add(vuoro);
			}
			
			vuoro=0;
			
			pelaajiaYht=pelaajia=logi.pelaaja+1;

			pelit++;
		}

		
    	System.out.print(vuoro+"\t");
		System.out.println(logi);
		//System.out.println(p.getÄly() instanceof Viisas);
		
		
    	if(pelaaja.getÄly() instanceof Tyhmä) {
    		nollanvuorot++;
    		
    		if(viim != null) {
	    		if(logi.nostot > 0) {
	    			nostot++;
	    		}else{    		
	    			if(logi.lyonti.firstElement().isMusta()) {
	    				musta++;
	    			}else {
	    				if(!viim.isMusta()) {
				    		if(logi.lyonti.firstElement().getVari() == viim.getVari()) {
				    			samaVäri++;
				    		}
				    		if(logi.lyonti.firstElement().getMerkki() == viim.getMerkki()) {
				    			samaMerkki++;
				    		}
	    	    		}else {
	    	    			vastustajanväri++;
	    	    		}
	    			}
	    		}
    		}
    				
    		if(logi.tapahtuma==Teko.UNO) {    	
    			uunot++;
    		}
    		
    		if(logi.tapahtuma==Teko.VTO) {    			
    			nollanvuorot_l.add(nollanvuorot);
    			nollapelissa_l.add(vuoro);
    			nollansijoitus_l.add(pelaajiaYht-pelaajia+1);    			
    			//nollanvuorot=0;
    			

    			
    		}
    		
        	//if(logi.kasikoko>suurinkäsikoko) {
        	//	suurinkäsikoko=logi.kasikoko;
        	//}
    	}
		
    	if(!logi.lyonti.isEmpty()) {
    		viim  = logi.lyonti.lastElement();
    	}
    	
    	if(logi.tapahtuma==Teko.VTO) {
    		pelaajia--;			
    	}
    	vuoro++;
    }
        
    static String formatvalue(ArrayList<Integer> al) {
    	return String.format("%.2f", Tunnusluvut.keskiarvo(al))
    			+ ", hajonta: " + String.format("%.2f", Tunnusluvut.keskihajonta(al))
    			+ ", min: " + Tunnusluvut.getMin(al)
    			+ ", max: " + Tunnusluvut.getMax(al);
    }
    
    public static void yhteenveto() {   
    	System.out.println("------------------------");
    	System.out.println("Pelitilasto             ");
    	System.out.println("------------------------");
    	System.out.println("Kaikki pelaajat:");
    	System.out.println("  Pelaajia: " + String.valueOf(pelaajiaYht));
    	System.out.println("  Pelien määrä: " + String.valueOf(pelit));
    	System.out.println("  Vuorot per peli: " + formatvalue(vuoro_l));    	
    	System.out.println();
    	System.out.println("Tekoälyn Viisas -tilasto (pelit keskimäärin):");    
    	System.out.println("  Sijoitus: " + formatvalue(nollansijoitus_l));
    	System.out.println("  Pelatut vuorot per peli: " + (double)nollanvuorot/pelit);
    	System.out.println("  Uunot per peli: " + (double)uunot/pelit);
    	System.out.println("  Lyönnit (pelatuista vuoroista): ");    	
    	System.out.println("    Sama väri: " + String.format("%.2f", (double)samaVäri*100/nollanvuorot)+"%");
    	System.out.println("    Sama merkki: " + String.format("%.2f", (double)samaMerkki*100/nollanvuorot)+"%");
    	System.out.println("    Musta: " + String.format("%.2f", (double)musta*100/nollanvuorot)+"%");
    	System.out.println("    Vastustajan valitsema väri: " + String.format("%.2f", (double)vastustajanväri*100/nollanvuorot)+"%");
    	System.out.println("    Ei voi laittaa mitään -tilanteet: " + String.format("%.2f", (double)nostot*100/nollanvuorot)+"%");
    	
    }
}
