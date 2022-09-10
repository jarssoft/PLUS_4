import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

class ViisasTest {

	@Test
	void testTapahtuma() {
		fail("Not yet implemented");
	}

	@Test
	void testGetKortti() {
		//Pöytä.pöytä.lyö(Kortti.testiKortti(Vari.SININEN, Merkki.N5));
		Viisas viisas = new Viisas();
		
		Vector<Kortti> lyoty = new Vector<Kortti>();
		lyoty.add(Kortti.testiKortti(Vari.SININEN, Merkki.N5));
		viisas.tapahtuma(new Logi("0", lyoty, null, 0, 5));
		
		Vector<Kortti> kasi = new Vector<Kortti>();
		kasi.add(Kortti.testiKortti(Vari.SININEN, Merkki.N2));
		kasi.add(Kortti.testiKortti(Vari.PUNAINEN, Merkki.N2));
		kasi.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
		
		Vector<Kortti> odotettu = new Vector<Kortti>();
		odotettu.add(Kortti.testiKortti(Vari.VIHREA, Merkki.N5));
		
		assertEquals(odotettu.toString(), viisas.getKortti(kasi).toString());
	}

	@Test
	void testGetVari() {
		fail("Not yet implemented");
	}

}
