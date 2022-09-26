// Pelin kaikille yhteiset tapahtumat.

// Jako on kirjattava tapahtuma, jotta aloituskortti tiedetään. 
// Myös jakaja saadaan tarvittaessa tietoon.
// Toisaalta pelaaja ei tee tilanteessa (tahallista) valintaa.
// p:0 l:[PLUS_4, PUN_N5] v:null n:2 k:7

// Sekin on tieto, että ei voi laittaa mitään.
// Pelaaja ei tee valintaa.
// Viimeksi lyödyt on vaikeampi löytää
// p:0 l:null v:null n:3 k:2

// Plussakortin aiheuttama nosto on tapahtuma, koska pelaajan tilanne muuttuu.
// Toisaalta pelaaja ei tee tilanteessa valintaa, eikä uutta tietoa synny.
// Myös viimeksi lyödyt on vaikeampi löytää
// p:0 l:null v:null n:4 k:6

// Ohitetuksi tuleminen ei ole tapahtuma, eikä suunnanvaihdon 
// seurauksena välistä jääneitä voida järkevästi merkitä.

// Pelistä lähteneet eivät aiheuta tapahtumia

// login käyttäjät:
// tekoälyt
// tilastointi
// tarkastus

import java.util.Vector;

public class Logi {

    final String pelaaja;
    final Vector<Kortti> lyonti;
    final Vari vari;
    final int nostot;
    final int kasikoko;

    // Tiedottaa pelaajalle tapahtumasta.
    public Logi(String pelaaja, Vector<Kortti> lyonti, 
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