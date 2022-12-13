import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

class TilannekuvaTest {

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
	
	@Test
	void testGetNextVastustaja() {
		
		// JAK
		Vector<Kortti> lyoty = new Vector<Kortti>();
		Tilannekuva kuva=new Tilannekuva();
		lyoty.add(SIN_N0);
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 5, lyoty, null, 1));
		assertEquals(6, kuva.numberOfPlayers());		
		kuva = new Tilannekuva();		
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(3, kuva.numberOfPlayers());
		assertEquals(0, kuva.getNextVastustaja());
		
		// LÖI
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(1, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, lyoty, null, 0));
		assertEquals(2, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, null, 0));
		assertEquals(0, kuva.getNextVastustaja());
		
		// Suunnanvaihtokortti
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_SV);
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(2, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, null, 0));
		assertEquals(0, kuva.getNextVastustaja());
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(1, kuva.getNextVastustaja());
		
		// Ohituskortti
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_OH);
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(1, kuva.getNextVastustaja());		
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, lyoty, null, 0));
		assertEquals(0, kuva.getNextVastustaja());		
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_OH);
		lyoty.add(PUN_OH);
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(0, kuva.getNextVastustaja());
		
		// Plussa
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_PL);
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 0));
		assertEquals(2, kuva.getNextVastustaja());
		lyoty = new Vector<Kortti>();
		lyoty.add(PLUS_4);
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, Vari.KELTAINEN, 0));
		assertEquals(1, kuva.getNextVastustaja());
		
		// Pelaajien korttien loppumisen ennustaminen ///////////////////////////////////////
		
		// Voitot		
		lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N0);
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(0, kuva.getNextVastustaja());
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		lyoty.add(SIN_N0);
		kuva.tapahtuma(new Tapahtuma(Teko.VTO, 0, lyoty, null, 0));
		assertEquals(1, kuva.getNextVastustaja());
		lyoty = new Vector<Kortti>();
		lyoty.add(SIN_N0);
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, lyoty, null, 0));
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, null, 0));
		assertEquals(1, kuva.getNextVastustaja());
				
		// Voitot + Suunnanvaihto	
		lyoty = new Vector<Kortti>();
		lyoty.add(PUN_SV);
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
		assertEquals(1, kuva.getNextVastustaja());
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		lyoty.add(PUN_SV);
		kuva.tapahtuma(new Tapahtuma(Teko.VTO, 1, lyoty, null, 0));
		assertEquals(2, kuva.getNextVastustaja());
	}
	
	@Test
	void testGetNextPuuttuva() {
		// Mustan kortin lyödessä valitaa väri jota vastustajalla ei ole.
		{
			Vector<Kortti> lyoty = new Vector<Kortti>();
			Tilannekuva kuva=new Tilannekuva();
			lyoty = new Vector<Kortti>();
			lyoty.add(SIN_N5);
			kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, lyoty, null, 1));
			assertEquals(3, kuva.numberOfPlayers());
			assertEquals(0, kuva.getNextVastustaja());
			kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, lyoty, null, 1));
			//Pelaaja 1 ilmoittaa OHI-tilanteen.
			lyoty.clear();
			kuva.tapahtuma(new Tapahtuma(Teko.OHI, 1, lyoty, null, 3));
			assertEquals(2, kuva.getNextVastustaja());
			lyoty.clear();
			lyoty.add(PLUS_4);
			kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, lyoty, Vari.SININEN, 0));
			assertEquals(1, kuva.getNextVastustaja());
			assertEquals(Vari.SININEN, kuva.getNextPuuttuva().getVari());
		}
		{
			Vector<Kortti> lyoty = new Vector<Kortti>();
			Tilannekuva kuva=new Tilannekuva();
			lyoty = new Vector<Kortti>();
			lyoty.add(PUN_N2);
			kuva.tapahtuma(new Tapahtuma(Teko.JAK, 1, lyoty, null, 1));
			lyoty.clear();
			kuva.tapahtuma(new Tapahtuma(Teko.OHI, 0, lyoty, null, 3));
			lyoty.add(JOKERI);
			kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, lyoty, Vari.SININEN, 0));
			assertEquals(0, kuva.getNextVastustaja());
			assertEquals(Vari.PUNAINEN, kuva.getNextPuuttuva().getVari());
		}

	}

	
}
