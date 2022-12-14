import static org.junit.jupiter.api.Assertions.*;
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
		Tilannekuva kuva=new Tilannekuva();
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 5, new Kortti[] {SIN_N0}, 1));
		assertEquals(6, kuva.numberOfPlayers());		
		kuva = new Tilannekuva();		
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {SIN_N0}, 1));
		assertEquals(3, kuva.numberOfPlayers());
		assertEquals(0, kuva.getNextVastustaja());
		
		// LÖI
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {SIN_N0}, 0));
		assertEquals(1, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {SIN_N0}, 0));
		assertEquals(2, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, new Kortti[] {SIN_N0}, 0));
		assertEquals(0, kuva.getNextVastustaja());
		
		// Suunnanvaihtokortti
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {PUN_SV}, 0));
		assertEquals(2, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, new Kortti[] {PUN_SV}, 0));
		assertEquals(0, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {PUN_SV, PUN_SV}, 0));
		assertEquals(1, kuva.getNextVastustaja());
		
		// Ohituskortti
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {PUN_OH}, 1));
		assertEquals(1, kuva.getNextVastustaja());		
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {PUN_OH}, 0));
		assertEquals(0, kuva.getNextVastustaja());		
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {PUN_OH, PUN_OH}, 0));
		assertEquals(0, kuva.getNextVastustaja());
		
		// Plussa
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {PUN_PL}, 0));
		assertEquals(2, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, new Kortti[] {PLUS_4}, Vari.KELTAINEN, 0));
		assertEquals(1, kuva.getNextVastustaja());
		
		// Pelaajien korttien loppumisen ennustaminen ///////////////////////////////////////
		
		// Voitot		
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {SIN_N0}, 1));
		assertEquals(0, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.VTO, 0, new Kortti[] {SIN_N0,SIN_N0,SIN_N0,SIN_N0,SIN_N0,SIN_N0,SIN_N0}, 0));
		assertEquals(1, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {SIN_N0}, 0));
		kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, new Kortti[] {SIN_N0}, 0));
		assertEquals(1, kuva.getNextVastustaja());
				
		// Voitot + Suunnanvaihto	
		kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {PUN_SV}, 1));
		assertEquals(1, kuva.getNextVastustaja());
		kuva.tapahtuma(new Tapahtuma(Teko.VTO, 1, new Kortti[] {PUN_SV,PUN_SV,PUN_SV,PUN_SV,PUN_SV,PUN_SV,PUN_SV}, 0));
		assertEquals(2, kuva.getNextVastustaja());
	}
	
	@Test
	void testGetNextPuuttuva() {
		// Mustan kortin lyödessä valitaa väri jota vastustajalla ei ole.
		{
			Tilannekuva kuva=new Tilannekuva();
			kuva.tapahtuma(new Tapahtuma(Teko.JAK, 2, new Kortti[] {SIN_N5}, 1));
			assertEquals(3, kuva.numberOfPlayers());
			assertEquals(0, kuva.getNextVastustaja());
			kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 0, new Kortti[] {SIN_N5}, 1));
			//Pelaaja 1 ilmoittaa OHI-tilanteen.
			kuva.tapahtuma(new Tapahtuma(Teko.OHI, 1, new Kortti[] {}, 3));
			assertEquals(2, kuva.getNextVastustaja());
			kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 2, new Kortti[] {PLUS_4}, Vari.SININEN, 0));
			assertEquals(1, kuva.getNextVastustaja());
			assertEquals(Vari.SININEN, kuva.getNextPuuttuva().getVari());
		}
		{
			Tilannekuva kuva=new Tilannekuva();
			kuva.tapahtuma(new Tapahtuma(Teko.JAK, 1, new Kortti[] {PUN_N2}, 1));
			kuva.tapahtuma(new Tapahtuma(Teko.OHI, 0, new Kortti[] {}, 3));
			kuva.tapahtuma(new Tapahtuma(Teko.LÖI, 1, new Kortti[] {JOKERI}, Vari.SININEN, 0));
			assertEquals(0, kuva.getNextVastustaja());
			assertEquals(Vari.PUNAINEN, kuva.getNextPuuttuva().getVari());
		}
	}
	
}
