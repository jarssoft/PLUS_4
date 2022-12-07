import java.util.Vector;

enum Teko {
    JAK, LÖI, UNO, OHI, VTO, POISTUI, PALASI;
}

/**
Yhden pelin kaikille yhteiset kirjatut tapahtumat.

Pelaajat on numeroitu istumajärjestyksen mukaan niin, että 
suurin jakaa ja pienin aloittaa (jos erikoiskorttia ei pelata).

Jako on kirjattava tapahtuma, jotta aloituskortti (l) tiedetään. 
Myös jakaja (p) ja pelaajien määrä (p+1) saadaan tietoon.
Jakaja on aina suurin numero. Seuraava pelaaja on p-1 tai 0.
Poikkeus, jossa mustan lisäksi voi lyödä lisää kortteja.

t: JAK, p:3 l:[PLUS_4, PUN_N5] v:null n:2

Lyönti on tapahtuma, ja siihen liittyvät nostot kirjataan.
Lyönti on LÖI, paitsi uuno-tilanteessa UNO tai viimeisellä kortilla VTO.
Mustan kortin lyödessä ilmoitetaan väri (v).

t: LÖI, p:0 l:[SIN_N5, KEL_N5] v:null n:0

Sekin on tapahtuma ja uusi tieto, että ei voi laittaa mitään.
Viimeksi lyödyt on vaikeampi löytää, 
koska tämä rikkoo säännön, jossa viimeiseksi laitettu olisi aina päällimmäisenä.

t: OHI, p:1 l:null v:null n:3

Plussakortin lyömistä seuraava nosto ennalta arvattavissa.
Se voi olla tapahtuma.
Myös viimeksi lyödyt on vaikeampi löytää

p:2 l:null v:null n:2

Luovutukselle tai paluulle peliin on varattu tapahtumat POISTU ja PALASI.

Ohitetuksi tuleminen ei ole tapahtuma.
Pelin voittaneet eivät aiheuta tapahtumia.
*/
public class Tapahtuma {

	final Teko tapahtuma;
    final int pelaaja;
    final Vector<Kortti> lyonti;
    final Vari vari;
    final int nostot;

    // Tiedottaa pelaajalle tapahtumasta.
    public Tapahtuma(Teko tapahtuma, int pelaaja, Vector<Kortti> lyonti, 
            Vari vari, int nostot, int kasikoko){

    	assert(pelaaja>=0);
    	assert(pelaaja<=9);
    	assert(nostot>=0);
    	assert(nostot<=16);
    	assert(kasikoko>=0);
    	assert(!lyonti.isEmpty() || nostot==3);
    	assert(lyonti.isEmpty() || !lyonti.get(0).isMusta() || tapahtuma==Teko.JAK || vari!=null);
    	
    	assert(kasikoko<=108);

    	this.tapahtuma=tapahtuma;
        this.pelaaja=pelaaja;
        this.lyonti=lyonti;
        this.vari=vari;
        this.nostot=nostot;

    }

    public String toString(){
       return("t:" + tapahtuma + " p:" + pelaaja + " l:" + lyonti
                 + " v:" + vari + " n:" + nostot);
    }
}