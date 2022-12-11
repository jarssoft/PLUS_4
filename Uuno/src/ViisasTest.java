import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Arrays;
import java.util.Vector;

class ViisasTest {

	private final Kortti SIN_N0 = Kortti.testiKortti(Vari.SININEN, Merkki.N0);
	private final Kortti SIN_N2 = Kortti.testiKortti(Vari.SININEN, Merkki.N2);
	private final Kortti SIN_N5 = Kortti.testiKortti(Vari.SININEN, Merkki.N5);
	private final Kortti SIN_N6 = Kortti.testiKortti(Vari.SININEN, Merkki.N6);
	private final Kortti VIH_N7 = Kortti.testiKortti(Vari.VIHREA, Merkki.N7);
	private final Kortti VIH_N2 = Kortti.testiKortti(Vari.VIHREA, Merkki.N2);
	private final Kortti VIH_N5 = Kortti.testiKortti(Vari.VIHREA, Merkki.N5);
	private final Kortti VIH_N5b = Kortti.testiKortti(14*6+5);
	private final Kortti KEL_N1 = Kortti.testiKortti(Vari.KELTAINEN, Merkki.N1);	
	private final Kortti KEL_N5 = Kortti.testiKortti(Vari.KELTAINEN, Merkki.N5);
	private final Kortti KEL_N8 = Kortti.testiKortti(Vari.KELTAINEN, Merkki.N8);
	private final Kortti PUN_N2 = Kortti.testiKortti(Vari.PUNAINEN, Merkki.N2);
	private final Kortti PLUS_4 = Kortti.testiKorttiPlus4();
	private final Kortti JOKERI = Kortti.testiKorttiJokeri();
	
	private final Kortti PUN_SV = Kortti.testiKortti(Vari.PUNAINEN, Merkki.SUUNNANVAIHTO);
	private final Kortti PUN_OH = Kortti.testiKortti(Vari.PUNAINEN, Merkki.OHITUS);
	private final Kortti PUN_PL = Kortti.testiKortti(Vari.PUNAINEN, Merkki.PLUS2);
	
	private Viisas viisas = new Viisas();
	    
	@Test
	void testGetKortti() {
		
		assertEquals(true, JOKERI.isJokeri());
		assertEquals(true, JOKERI.isMusta());
		
		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N5);
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 1, lyoty, null, 1));
		
		// Maksimoidaan jääviin kortteihin erilaisten värien määrä.
		
		testaa( new Kortti[]{VIH_N5, SIN_N2, PUN_N2}, 
	    		new Kortti[]{VIH_N5});
		
		testaa( new Kortti[]{VIH_N5, SIN_N5, SIN_N2, SIN_N6, SIN_N0}, 
				Vari.SININEN);
		
		testaa( new Kortti[]{VIH_N5, SIN_N5, VIH_N5b, SIN_N2}, 
	    		new Kortti[]{SIN_N2});
		
		// Yritetään aina vaihtaa väriä.
		
		testaa( new Kortti[]{SIN_N2, VIH_N5}, 
	    		Vari.VIHREA);
		
		testaa( new Kortti[]{SIN_N2, SIN_N6, KEL_N5, KEL_N8}, 
	    		Vari.KELTAINEN);
		
		testaa( new Kortti[]{VIH_N7, SIN_N5, KEL_N5, PUN_N2}, 
	    		Vari.KELTAINEN);
		
		testaa( new Kortti[]{VIH_N5, SIN_N5, VIH_N5b, PUN_N2, KEL_N8}, 
				Vari.VIHREA);
		
		// Säästetään musta loppuun.
		
		testaa( new Kortti[]{PLUS_4, VIH_N5, SIN_N2, PUN_N2, KEL_N1}, 
	    		new Kortti[]{VIH_N5});
		
		testaa( new Kortti[]{SIN_N6, PUN_N2, KEL_N1, JOKERI}, 
	    		new Kortti[]{SIN_N6});
		
		// Käytetään musta ennen viimeistä.

		testaa( new Kortti[]{VIH_N5, PLUS_4}, 
	    		new Kortti[]{PLUS_4});
		
		testaa( new Kortti[]{VIH_N5, SIN_N5, JOKERI}, 
	    		new Kortti[]{JOKERI});
		
		testaa( new Kortti[]{VIH_N5, SIN_N5, VIH_N5b, JOKERI}, 
	    		new Kortti[]{JOKERI});
		
		testaa( new Kortti[]{PUN_N2, SIN_N2, PLUS_4, JOKERI}, 
	    		new Kortti[]{PLUS_4},
	    		new Kortti[]{JOKERI});
		
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.

		testaa( new Kortti[]{VIH_N5, SIN_N5, VIH_N2}, 
	    		new Kortti[]{SIN_N5, VIH_N5});
	    
	    testaa( new Kortti[]{SIN_N2, VIH_N7, VIH_N2}, 
	    		new Kortti[]{SIN_N2, VIH_N2});
	    
	    testaa( new Kortti[]{SIN_N5, VIH_N5, KEL_N5, KEL_N8}, 
	    		Vari.KELTAINEN);
	    
	    testaa( new Kortti[]{SIN_N5, VIH_N5, KEL_N5, VIH_N7}, 
	    		Vari.VIHREA);
	    
	    testaa( new Kortti[]{SIN_N5, VIH_N5, VIH_N5b, KEL_N5, KEL_N8, KEL_N1, PUN_N2}, 
	    		Vari.KELTAINEN);
				
		// Jätetään viimeiseksi kaksi samanväristä (Huonontaa tilannetta.)

		//testaa( new Kortti[]{VIH_N5, SIN_N6, SIN_N2}, 
		//		Vari.VIHREA);
	    
	    //testaa( new Kortti[]{VIH_N2, SIN_N6, VIH_N5}, 
	    //		new Kortti[]{SIN_N6});
		
		// Jos käteen jää yli kaksi korttia, yritetään poistaa yleisin väri (Huonontaa tilannetta merkittävästi.)
	    /*
		testaa( new Kortti[]{SIN_N6, VIH_N5, VIH_N7, VIH_N2}, 
				Vari.VIHREA);
	    
	    testaa( new Kortti[]{KEL_N1, KEL_N5, KEL_N8, SIN_N0, SIN_N6}, 
	    		Vari.KELTAINEN);
	    
	    testaa( new Kortti[]{KEL_N1, KEL_N5, SIN_N2, SIN_N0, SIN_N6}, 
	    		Vari.SININEN);
	    		*/

	}

	void testaa(Kortti[] kasi, Kortti[] odotettu) {		
    			
		Vector<Kortti> odotettuV = new Vector<Kortti>(Arrays.asList(odotettu));		
		Vector<Kortti> kasiV = new Vector<Kortti>(Arrays.asList(kasi));
		for(Kortti o: odotettuV) {
			assert(kasiV.contains(o));
		}
		
		for(int t=0;t<2;t++)
		{
    		Collections.reverse(kasiV);    	
	    	Vector<Kortti> lyotavaV = viisas.getKortti(kasiV);    	
	    	assertEquals(odotettuV.toString(), lyotavaV.toString());
		}
		
	}
	
	void testaa(Kortti[] kasi, Vari vari) {		
				
		Vector<Kortti> kasiV = new Vector<Kortti>(Arrays.asList(kasi));
		
		for(int t=0;t<2;t++)
		{
    		Collections.reverse(kasiV);    	
	    	Vector<Kortti> lyotavaV = viisas.getKortti(kasiV);    	
	    	assertEquals(vari, lyotavaV.lastElement().getVari());
		}
		
	}
	
	void testaa(Kortti[] kasi, Kortti[] odotettu1, Kortti[] odotettu2) {		
		
		Vector<Kortti> odotettu1V = new Vector<Kortti>(Arrays.asList(odotettu1));		
		Vector<Kortti> odotettu2V = new Vector<Kortti>(Arrays.asList(odotettu2));
		Vector<Kortti> kasiV = new Vector<Kortti>(Arrays.asList(kasi));
		for(Kortti o: odotettu1V) {
			assert(kasiV.contains(o));
		}
		for(Kortti o: odotettu2V) {
			assert(kasiV.contains(o));
		}
		
		for(int t=0;t<2;t++)
		{
			Collections.reverse(kasiV);
			
	    	Vector<Kortti> lyotavaV = viisas.getKortti(kasiV);   
	    	//assertEquals(odotettu1V.toString(), lyotavaV.toString());
	    	assertTrue(lyotavaV.toString().equals(odotettu1V.toString()) 
	    			|| lyotavaV.toString().equals(odotettu2V.toString()) );
		}
		
	}
	
	@Test
	void testGetVari() {
		
		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(Kortti.testiKortti(Vari.PUNAINEN, Merkki.N5));
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 0, lyoty, null, 1));
		
		// Valitaan väri, joka on ylinen käsikorttien kanssa
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
		{
		    Kortti[] kasi    = {PLUS_4, SIN_N6};
			viisas.getKortti(new Vector<Kortti>(Arrays.asList(kasi)));
			assertEquals(Vari.SININEN, viisas.getVari());
		}
		
		// Valitaan väri, joka on ylinen käsikorttien kanssa
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
		{
		    Kortti[] kasi    = {PLUS_4, SIN_N6};
			viisas.getKortti(new Vector<Kortti>(Arrays.asList(kasi)));
			assertEquals(Vari.SININEN, viisas.getVari());
		}
		
	}
	
	@Test
	void getNextVastustajaJaKorttimäärä() {
				
		// Vuoron ennustaminen ///////////////////////////////////////////////////
		
		// JAK
		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N0);
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 5, lyoty, null, 1));
		assertEquals(6, viisas.numberOfPlayers());		
		viisas = new Viisas();		
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(3, viisas.numberOfPlayers());
		assertEquals(0, viisas.getNextVastustaja());
		
		// LÖI
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(1, viisas.getNextVastustaja());
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, lyoty, null, 0));
		assertEquals(2, viisas.getNextVastustaja());
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, null, 0));
		assertEquals(0, viisas.getNextVastustaja());
		
		// Suunnanvaihtokortti
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_SV);
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(2, viisas.getNextVastustaja());
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, null, 0));
		assertEquals(0, viisas.getNextVastustaja());
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(1, viisas.getNextVastustaja());
		
		// Ohituskortti
		viisas = new Viisas();
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_OH);
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(1, viisas.getNextVastustaja());		
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, lyoty, null, 0));
		assertEquals(0, viisas.getNextVastustaja());		
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_OH);
		lyoty.add(PUN_OH);
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(0, viisas.getNextVastustaja());
		
		// Plussa
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_PL);
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(2, viisas.getNextVastustaja());
		lyoty = new Vector<Kortti>();
		lyoty.add(PLUS_4);
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, Vari.KELTAINEN, 0));
		assertEquals(1, viisas.getNextVastustaja());
		
		// Pelaajien korttimäärän ennustaminen ///////////////////////////////////////
		
		// Voitot
		viisas = new Viisas();		
		lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N0);
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(0, viisas.getNextVastustaja());
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		viisas.tapahtuma(new Tapahtuma(Teko.VTO, 0, lyoty, null, 0));
		assertEquals(1, viisas.getNextVastustaja());
		lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N0);
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, lyoty, null, 0));
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, null, 0));
		assertEquals(1, viisas.getNextVastustaja());
				
		// Voitot + Suunnanvaihto
		viisas = new Viisas();		
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_SV);
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(1, viisas.getNextVastustaja());
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		viisas.tapahtuma(new Tapahtuma(Teko.VTO, 1, lyoty, null, 0));
		assertEquals(2, viisas.getNextVastustaja());
		
		// Vastustajien korttien ennustaminen //////////////////////////////////////
		
		// Lyödään vastustajalta puuttuva kortti (SIN_N5)
		
		lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N5);
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 1, lyoty, null, 1));
		lyoty.clear();
		viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 3));
				
		testaa( new Kortti[]{KEL_N8, PLUS_4, PUN_N2}, 
	    		new Kortti[]{PLUS_4});
		//assertEquals(Vari.SININEN, viisas.getVari());
	}

}
