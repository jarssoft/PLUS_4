import java.util.Vector;

public interface Tekoäly {

	/** Tiedottaa pelaajalle tapahtumasta. */
	void tapahtuma(Tapahtuma logi);

	/** Pyytää tekoälyä tekemään valinnan. */
	Vector<Kortti> getKortti(Vector<Kortti> kasi);

	/** Värivalinta, joka kysytään älyltä mustan kortin jälkeen. */
	Vari getVari();

}