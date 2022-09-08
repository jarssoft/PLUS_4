import java.util.ArrayList;

public class Tilasto {
	
	static int vuoro=0;
	static int pelaajia=4;
	
	/** Nollan pelaamien vuorojen määrä. */	
	static ArrayList<Integer> nollanvuorot_l = new ArrayList<Integer>();
	static int nollanvuorot=0;
	
	/** Nollan pelissäoloaikana pelattujen vuorojen määrä. */
	static ArrayList<Integer> nollapelissa_l = new ArrayList<Integer>();
		
	/** Nollan sijoitus. */
	static ArrayList<Integer> nollansijoitus_l = new ArrayList<Integer>();
	
	static int pelit=0;
	
    public static void tilastoi(Logi logi) {
    	
    	assert(vuoro>0 || logi.kasikoko==7);
    	
    	if(logi.pelaaja.equals("0")) {
    		nollanvuorot++;
    		if(logi.kasikoko==0) {
    			nollansijoitus_l.add(5-pelaajia);
    			nollapelissa_l.add(vuoro);
    			nollanvuorot_l.add(nollanvuorot);
    			nollanvuorot=0;    			
    		}
    	}
    	
    	if(logi.kasikoko==0) {
    		System.out.print(vuoro+"\t");
    		System.out.println(logi);
    		pelaajia--;
    		if(pelaajia==0) {    			
    			vuoro=0;
    			pelaajia=4;
    			System.out.println("------------------------------------------");
    			pelit++;
    		}
    	}
    	vuoro++;
    	
    }
    
    public static double keskiarvo(ArrayList<Integer> al) {
        double sum = 0.0;
        double length = al.size();

        for(double num : al) {
            sum += num;
        }

        return sum/length;
    }
    
    public static double keskihajonta(ArrayList<Integer> al) {
        double standardDeviation = 0.0;
        double length = al.size();
        double mean = keskiarvo(al);

        for(double num: al) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        
        return Math.sqrt(standardDeviation/length);
    }
    
    public static void yhteenveto() {
    	
    	System.out.println("Pelien määrä: " + String.valueOf(pelit));
    	System.out.println("Nollan pelaamien vuorojen määrä: " + String.valueOf(keskiarvo(nollanvuorot_l)) + " +-" + String.valueOf(keskihajonta(nollanvuorot_l)));
    	System.out.println("Nollan pelissäoloaikana pelattujen vuorojen määrä: " + String.valueOf(keskiarvo(nollapelissa_l)) + " +-" + String.valueOf(keskihajonta(nollapelissa_l)));
    	System.out.println("Nollan sijoitus: " + String.valueOf(keskiarvo(nollansijoitus_l)) + " +-" + String.valueOf(keskihajonta(nollansijoitus_l)));
    	
    }
}
