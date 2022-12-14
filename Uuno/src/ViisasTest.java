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
	private final Kortti PUN_N5 = Kortti.testiKortti(Vari.PUNAINEN, Merkki.N5);
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

		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 1, new Kortti[] {SIN_N5}, 1));
		
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
		
		testaa( new Kortti[]{VIH_N5, JOKERI}, 
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
	    
	    // Vältetään antamasta vuoroa sille jolla on uuno-tilanne.
		{
			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {PUN_N5, VIH_N5, SIN_N5, VIH_N5, SIN_N5, PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N2}, 0));		

			testaa( new Kortti[]{PUN_OH, PUN_N2, PUN_N5},
		    		new Kortti[]{PUN_OH});
		}
		{
			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {PUN_N5, VIH_N5, SIN_N5, VIH_N5, SIN_N5, PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N2}, 0));		

			testaa( new Kortti[]{PUN_N2, PUN_N5, PUN_SV},
		    		new Kortti[]{PUN_SV});
		}
		{
			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {PUN_N2}, 0));		
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N5, VIH_N5, SIN_N5, VIH_N5, SIN_N5, PUN_N5}, 0));

			testaa( new Kortti[]{PUN_N2, PUN_SV, PUN_OH},
		    		new Kortti[]{PUN_N2});
		}
		
		// Annetaan puuttuva kortti
		
		{
			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.UNO, 0, new Kortti[] {PUN_N5, VIH_N5, SIN_N5, VIH_N5, SIN_N5, PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 2, new Kortti[] {PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {}, 3));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N2}, 0));

			testaa( new Kortti[]{PUN_N5, SIN_N2},
		    		new Kortti[]{PUN_N5});
		}
		{
			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.UNO, 0, new Kortti[] {PUN_N5, VIH_N5, SIN_N5, VIH_N5, SIN_N5, PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 2, new Kortti[] {PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {}, 3));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {KEL_N5}, 0));

			testaa( new Kortti[]{KEL_N1, KEL_N5, KEL_N8},
		    		new Kortti[]{KEL_N5});
		}
		
		// Annetaan plussa-kortti sille, jolla on uuno-tilanne
		
		{
			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.UNO, 0, new Kortti[] {PUN_N5, VIH_N5, SIN_N5, VIH_N5, SIN_N5, PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N2}, 0));

			testaa( new Kortti[]{PUN_N2, PUN_PL, PUN_OH},
		    		new Kortti[]{PUN_PL});
		}
		{
			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.UNO, 0, new Kortti[] {PUN_N5, VIH_N5, SIN_N5, VIH_N5, SIN_N5, PUN_N5}, 0));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_N2}, 0));

			testaa( new Kortti[]{PUN_N2, PLUS_4, PUN_OH},
		    		new Kortti[]{PLUS_4});
		}
		
	}

	void testaa(Kortti[] kasi, Kortti[] odotettu) {		
    			
		testaa(kasi, odotettu, (Vari)null);
		
	}
	
	void testaa(Kortti[] kasi, Kortti[] odotettu, Vari vari) {		
		
		Vector<Kortti> odotettuV = new Vector<Kortti>(Arrays.asList(odotettu));		
		Vector<Kortti> kasiV = new Vector<Kortti>(Arrays.asList(kasi));
		for(Kortti o: odotettuV) {
			assert(kasiV.contains(o));
		}
		
		for(int t=0;t<2;t++)
		{
    		Collections.reverse(kasiV);
    		Lyonti lyonti = viisas.getKortti(kasiV);
	    	assertEquals(odotettuV.toString(), lyonti.getKortit().toString());
	    	if(vari!=null) {
	    		assertEquals(vari.toString(), lyonti.getVari().toString());
	    	}
		}
		
	}
	void testaa(Kortti[] kasi, Vari vari) {		
				
		Vector<Kortti> kasiV = new Vector<Kortti>(Arrays.asList(kasi));
		
		for(int t=0;t<2;t++)
		{
    		Collections.reverse(kasiV);    	    	
	    	assertEquals(vari, viisas.getKortti(kasiV).getKortit().lastElement().getVari());
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
			
	    	Vector<Kortti> lyotavaV = viisas.getKortti(kasiV).getKortit();   
	    	//assertEquals(odotettu1V.toString(), lyotavaV.toString());
	    	assertTrue(lyotavaV.toString().equals(odotettu1V.toString()) 
	    			|| lyotavaV.toString().equals(odotettu2V.toString()) );
		}
		
	}
	
	@Test
	void testGetVari() {
		
		viisas.tapahtuma(new Tapahtuma(Teko.JAK, 0, new Kortti[] {PUN_N5}, 1));
		
		// Valitaan väri, joka on ylinen käsikorttien kanssa
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
		{
		    Kortti[] kasi    = {PLUS_4, SIN_N6};			
			assertEquals(Vari.SININEN, viisas.getKortti(new Vector<Kortti>(Arrays.asList(kasi))).getVari());
		}

		// Valitaan väri, joka on ylinen käsikorttien kanssa
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
		{
		    Kortti[] kasi    = {JOKERI, KEL_N5};
			viisas.getKortti(new Vector<Kortti>(Arrays.asList(kasi)));
			assertEquals(Vari.KELTAINEN, viisas.getKortti(new Vector<Kortti>(Arrays.asList(kasi))).getVari());
		}
	    
	    // Valitaan väri joka on itsellä, mutta ei vastustajalla
		{

			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 1, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {}, 3));

			testaa( new Kortti[]{SIN_N5, VIH_N5, KEL_N5, JOKERI}, 
		    		new Kortti[]{JOKERI}, Vari.KELTAINEN);
			
			//assertEquals(Vari.KELTAINEN, viisas.getVari());
		}
		{

			viisas.tapahtuma(new Tapahtuma(Teko.JAK, 1, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {KEL_N5}, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {JOKERI}, Vari.VIHREA, 1));
			viisas.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {}, 3));

			testaa( new Kortti[]{SIN_N5, VIH_N5, KEL_N5, JOKERI}, 
		    		new Kortti[]{JOKERI}, Vari.VIHREA);
			
			//assertEquals(Vari.KELTAINEN, viisas.getVari());
		}
	}

}
