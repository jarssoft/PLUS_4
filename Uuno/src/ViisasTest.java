import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

class ViisasTest {

	@Test
	void testTapahtuma() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetKortti() {
		//Pöytä.pöytä.lyö(Kortti.testiKortti(Vari.SININEN, Merkki.N5));
		Viisas viisas = new Viisas();
		
		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(Kortti.testiKortti(Vari.SININEN, Merkki.N5));
		viisas.tapahtuma(new Logi("0", lyoty, null, 0, 5));
		
		// Mahdollisimman vähän kortteja kerralla:
		{
			Vector<Kortti> kasi = new Vector<Kortti>();
			kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N2));
			kasi.add(Kortti.testiKortti(Vari.PUNAINEN, Merkki.N2));
			Vector<Kortti> odotettu = new Vector<Kortti>();
			odotettu.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			assertEquals(odotettu.toString(), viisas.getKortti(kasi).toString());
		}
		// Säästetään musta loppuun
		{
			Vector<Kortti> kasi = new Vector<Kortti>();
			kasi.add(Kortti.testiKorttiPlus4());
			kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N6));
			kasi.add(Kortti.testiKortti(Vari.PUNAINEN, Merkki.N2));
			kasi.add(Kortti.testiKortti(Vari.KELTAINEN, Merkki.N1));			
			Vector<Kortti> odotettu = new Vector<Kortti>();
			odotettu.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			assertEquals(odotettu.toString(), viisas.getKortti(kasi).toString());
		}
		// Käytetään musta ennen viimeistä
		{
			Vector<Kortti> kasi = new Vector<Kortti>();
			kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			kasi.add(Kortti.testiKorttiPlus4());
			Vector<Kortti> odotettu = new Vector<Kortti>();
			odotettu.add(Kortti.testiKorttiPlus4());
			assertEquals(odotettu.toString(), viisas.getKortti(kasi).toString());
		}
		// Jätetään viimeiseksi kortti, joka sopii käteenjäävään.
		{
			Vector<Kortti> kasi = new Vector<Kortti>();
			kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N5));
			kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N2));
			Vector<Kortti> odotettu = new Vector<Kortti>();
			odotettu.add(Kortti.testiKortti(Vari.SININEN, Merkki.N5));
			odotettu.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			assertEquals(odotettu.toString(), viisas.getKortti(kasi).toString());
		}
		// Jätetään viimeiseksi kaksi samanväristä
		{
			Vector<Kortti> kasi = new Vector<Kortti>();
			kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N6));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N2));
			Vector<Kortti> odotettu = new Vector<Kortti>();
			odotettu.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			assertEquals(odotettu.toString(), viisas.getKortti(kasi).toString());
		}
		// Jos käteen jää yli kolme korttia, yritetään poistaa yleisin väri
		{
			Vector<Kortti> kasi = new Vector<Kortti>();
			kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N6));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N2));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N1));
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.SUUNNANVAIHTO));
			Vector<Kortti> odotettu = new Vector<Kortti>();
			odotettu.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
			assertNotEquals(odotettu.toString(), viisas.getKortti(kasi).toString());
		}
	}

	@Test
	void testGetVari() {
		
		Viisas viisas = new Viisas();
		
		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(Kortti.testiKortti(Vari.PUNAINEN, Merkki.N5));
		viisas.tapahtuma(new Logi("0", lyoty, null, 0, 5));
		
		// Valitaan väri, joka on ylinen käsikorttien kanssa
		// Jätetään viimeiseksi kortti, joka sopii käteen jäävään.
		{
			Vector<Kortti> kasi = new Vector<Kortti>();
			kasi.add(Kortti.testiKorttiPlus4());
			kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N6));
			viisas.getKortti(kasi);
			assertEquals(Vari.SININEN, viisas.getVari());
		}
	}

}
