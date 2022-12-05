// Pelin kaikille yhteiset tapahtumat:
// Jako, lyönnit ja nostot.

// Jako on kirjattava tapahtuma, jotta aloituskortti (l) tiedetään. 
// Myös jakaja (p) ja pelaajien määrä (p+1) saadaan tietoon.
// Jakaja on aina suurin numero. Seuraava pelaaja on p-1 tai 0.
// Pelaaja ei tee tilanteessa valintaa.
// p:3 l:[PLUS_4, PUN_N5] v:null n:2 k:7

// Lyönti on tapahtuma, ja siihen liittyvät nostot kirjataan.
// Pelaaja tekee mahdollisesti valinnan.
// Mustan kortin lyödessä pitää ilmoittaa väri (v).
// Pelaaja poistuu pelistä kun k == 0.
// p:0 l:[SIN_N5, KEL_N5] v:null n:0 k:5

// Sekin on tieto, että ei voi laittaa mitään.
// Viimeksi lyödyt on vaikeampi löytää, 
// koska tämä rikkoo säännön, jossa viimeiseksi laitettu olisi aina päällimmäisenä.
// Pelaaja ei tee valintaa.
// p:1 l:null v:null n:3 k:2

// Plussakortin lyömistä seuraava nosto on tapahtuma, koska pelaajan tilanne muuttuu.
// Myös viimeksi lyödyt on vaikeampi löytää
// Pelaaja ei tee valintaa.
// p:2 l:null v:null n:4 k:6

// Ohitetuksi tuleminen ei ole tapahtuma.

// Pelistä lähteneet eivät aiheuta tapahtumia

// login käyttäjät:
// tekoälyt
// tilastointi
// tarkastus

import java.util.Vector;

public class Logi {

    final int pelaaja;
    final Vector<Kortti> lyonti;
    final Vari vari;
    final int nostot;
    final int kasikoko;

    // Tiedottaa pelaajalle tapahtumasta.
    public Logi(int pelaaja, Vector<Kortti> lyonti, 
            Vari vari, int nostot, int kasikoko){

        this.pelaaja=pelaaja;
        this.lyonti=lyonti;
        this.vari=vari;
        this.nostot=nostot;
        this.kasikoko=kasikoko;

    }

    public String toString(){
       return("p:" + pelaaja + " l:" + lyonti
                 + " v:" + vari + " n:" + nostot + " k:" + kasikoko);
    }
}