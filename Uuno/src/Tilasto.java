
public class Tilasto {
	
	static int vuoro=0;
	static int pelaajia=4;
	
	static int nollankierrokset=0;
	
    public static void tilastoi(Logi logi) {
    	//if(logi.pelaaja.equals("0")) {
    	
    	assert(vuoro>0 || logi.kasikoko==7);
    	
    	if(logi.pelaaja.equals("0")) {
    		nollankierrokset++;
    	}
    	
    	if(logi.kasikoko==0) {
    		System.out.print(vuoro+"\t");
    		System.out.println(logi);
    		pelaajia--;
    		if(pelaajia==0) {
    			vuoro=0;
    			pelaajia=4;
    			System.out.println("------------------------------------------");
    		}
    	}
    	vuoro++;
    }
    
    public static void yhteenveto() {
    	System.out.println("Kierroksia (pelaaja 0): " + String.valueOf(nollankierrokset));
    }
}
