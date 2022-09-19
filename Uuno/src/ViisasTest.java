import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Vector;
import org.junit.jupiter.api.Test;

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
	private final Kortti PUN_N2 = Kortti.testiKortti(Vari.PUNAINEN, Merkki.N2);
	private final Kortti PLUS_4 = Kortti.testiKorttiPlus4();
	private final Kortti JOKERI = Kortti.testiKorttiJokeri();
	
	private Viisas viisas = new Viisas();
	
	@Test
	void testTapahtuma() {
		//fail("Not yet implemented");
	}
    
	@Test
	void testGetKortti() {
		assertEquals(true, JOKERI.isJokeri());
		assertEquals(true, JOKERI.isMusta());
		

		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N5);
		viisas.tapahtuma(new Logi("0", lyoty, null, 0, 5));
		
		// Maksimoidaan jääviin kortteihin erilaisten värien määrä.
		{
		    Kortti[] kasi     =  {VIH_N5, SIN_N2, PUN_N2};
		    Kortti[] odotettu = {VIH_N5};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}{
		    Kortti[] kasi     = {VIH_N5, SIN_N5, SIN_N2};
		    Kortti[] odotettu = {SIN_N2};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}{
		    Kortti[] kasi     = {VIH_N5, SIN_N5, VIH_N5b, SIN_N2};
		    Kortti[] odotettu = {SIN_N2};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}
		
		// Yritetään aina vaihtaa väriä.
		{
		    Kortti[] kasi     = {SIN_N2, VIH_N5};
		    Kortti[] odotettu = {VIH_N5};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}{
		    Kortti[] kasi     = {SIN_N5, VIH_N5, SIN_N2, VIH_N2};
		    Kortti[] odotettu = {SIN_N5, VIH_N5};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}
		
		// Säästetään musta loppuun.
		{		
		    Kortti[] kasi     = {PLUS_4, VIH_N5, SIN_N2, PUN_N2, KEL_N1};
		    Kortti[] odotettu = {VIH_N5};
			assertArrayEquals(odotettu, viisas.getKortti(kasi));
		}{		
		    Kortti[] kasi     = {SIN_N6, PUN_N2, KEL_N1, JOKERI};
		    Kortti[] odotettu = {SIN_N6};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}
		
		// Käytetään musta ennen viimeistä.
		{
		    Kortti[] kasi     = {VIH_N5, PLUS_4};
		    Kortti[] odotettu = {PLUS_4};
			assertArrayEquals(odotettu, viisas.getKortti(kasi));
		}{
		    Kortti[] kasi     = {VIH_N5, SIN_N5, JOKERI};
		    Kortti[] odotettu = {JOKERI};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}{
		    Kortti[] kasi     = {VIH_N5, SIN_N5, VIH_N5b, JOKERI};
		    Kortti[] odotettu = {JOKERI};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}{
		    Kortti[] kasi     = {PUN_N2, SIN_N2, PLUS_4, JOKERI};
		    Kortti[] odotettu = {PLUS_4};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}
		
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
		{
		    Kortti[] kasi     = {VIH_N5, SIN_N5, VIH_N2};
		    Kortti[] odotettu = {SIN_N5, VIH_N5};
			assertArrayEquals(odotettu, viisas.getKortti(kasi));
		}{
		    Kortti[] kasi     = {SIN_N2, VIH_N7, VIH_N2}; //vain eri järjestys
		    Kortti[] odotettu = {SIN_N2, VIH_N2};
			assertArrayEquals(odotettu, viisas.getKortti(kasi));
		}
		
		// Jätetään viimeiseksi kaksi samanväristä.
		{
		    Kortti[] kasi     = {VIH_N5, SIN_N6, SIN_N2};
		    Kortti[] odotettu = {VIH_N5};
			assertArrayEquals(odotettu, viisas.getKortti(kasi));
		}{
		    Kortti[] kasi     = {VIH_N2, SIN_N6, VIH_N5};
		    Kortti[] odotettu = {SIN_N6};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}
		
		// Jos käteen jää yli kaksi korttia, yritetään poistaa yleisin väri.
		{
		    Kortti[] kasi     = {SIN_N6, VIH_N5, VIH_N7, VIH_N2};
		    Kortti[] odotettu = {VIH_N5};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}{
		    Kortti[] kasi     = {SIN_N2, SIN_N0, SIN_N6, VIH_N5};
		    Kortti[] odotettu = {SIN_N2};
		    assertEquals(Arrays.toString(odotettu), Arrays.toString(viisas.getKortti(kasi)));
		}
	}

	@Test
	void testGetVari() {
		
		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(Kortti.testiKortti(Vari.PUNAINEN, Merkki.N5));
		viisas.tapahtuma(new Logi("0", lyoty, null, 0, 5));
		
		// Valitaan väri, joka on ylinen käsikorttien kanssa
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
		{
		    Kortti[] kasi    = {PLUS_4, SIN_N6};
			viisas.getKortti(kasi);
			assertEquals(Vari.SININEN, viisas.getVari());
		}
	}

}
