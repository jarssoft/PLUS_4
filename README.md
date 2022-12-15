# PLUS_4
Uno-korttipeli-simulaattori.

## Pelaajat ja tekoäly

Simulaattorin pelaajat periytyvät Tekoäly-luokasta:

- Tyhmä, joka toimii satunnaisuuden varassa.
- Viisas, joka toimii kovakoodattujen ehtojen avulla. Tekoäly on testattu esimerkkiaineistolla, joka on syötetty JUnit:iin.
- Käyttäjä-luokka on tekstipohjainen rajapinta ihmispelaajalle.

Tällä hetkellä Viisas voittaa peleistä noin 2/3, kun se pelaa kaksistaan Tyhmää vastaan.

## Kääntäminen

<pre>
cd Uuno/src
javac Peli.java
java Peli
</pre>

## Statistiikka

Pelitilaston avulla voi oppia uusia asioita Uno-korttipelistä.

<pre>
------------------------
Pelitilasto
------------------------
Kaikki pelaajat:
  Pelaajia: 2
  Pelien määrä: 1000
  Vuorot per peli: 68,34, hajonta: 51,78, min: 10, max: 388

Tekoälyn Viisas -tilasto (pelit keskimäärin):
  Sijoitus: 1,32, hajonta: 0,47, min: 1, max: 2
  Pelatut vuorot per peli: 20.251
  Uunot per peli: 1.657
  Lyönnit (pelatuista vuoroista): 
    Sama väri: 50,95%
    Sama merkki: 9,88%
    Musta: 5,06%
    Vastustajan valitsema väri: 8,32%
    Ei voi laittaa mitään -tilanteet: 27,06%

Tekoälyn Tyhmä -tilasto (pelit keskimäärin):
  Sijoitus: 1,64, hajonta: 0,48, min: 1, max: 2
  Pelatut vuorot per peli: 47.43
  Uunot per peli: 3.004
  Lyönnit (pelatuista vuoroista): 
    Sama väri: 48,44%
    Sama merkki: 4,35%
    Musta: 3,65%
    Vastustajan valitsema väri: 5,74%
    Ei voi laittaa mitään -tilanteet: 38,29%
</pre>

## Logi

Jokaisesta pelatusta vuorosta (ei plussa-korttien jälkeisistä nostoista) kirjoitetaan merkintä logiin. Logissa on viisi saraketta:

- t = Tapahtuman tyyppi (JAK = jako, LÖI = tavallinen lyönti, UNO = lyönti jossa jää yksi kortti, VTO = viimeisen kortin lyönti, OHI = pelaaja ei voinut lyödä korttia.)
- p = Pelaajan numero vuorojärjestyksessä, alkaen nollasta niin, että pelin suunta on alussa pienemmästä suurimpaan. Suurimman numeron omaava pelaaja toimii jakajana. Pelaajien määrä on siis p+1.
- l = Lista lyödyistä korteista lyhenteillä, jotka ovat muodossa XXX_YY (X=väri, Y=merkki), JOKERI tai PLUS_4.
- v = Pelaajan ilmoittama väri, jos musta kortti pelataan.
- n = Pelaajan nostot.

<pre>
0	t:JAK p:1 l:[SIN_N7] v:null n:1
1	t:LÖI p:0 l:[SIN_N6] v:null n:0
2	t:LÖI p:1 l:[SIN_N2] v:null n:0
3	t:LÖI p:0 l:[KEL_N2] v:null n:0
4	t:LÖI p:1 l:[KEL_N3] v:null n:0
5	t:LÖI p:0 l:[PUN_N3] v:null n:0
6	t:LÖI p:1 l:[PUN_N9] v:null n:0
7	t:LÖI p:0 l:[VIH_N9] v:null n:1
8	t:LÖI p:1 l:[VIH_N4, SIN_N4] v:null n:0
9	t:LÖI p:0 l:[SIN_N7] v:null n:0
10	t:UNO p:1 l:[SIN_N5] v:null n:0
11	t:LÖI p:0 l:[SIN_N8] v:null n:0
12	t:VTO p:1 l:[SIN_N8] v:null n:0
13	t:UNO p:0 l:[SIN_PL] v:null n:0
14	t:LÖI p:0 l:[JOKERI] v:KELTAINEN n:0
15	t:LÖI p:0 l:[KEL_N0] v:null n:1
16	t:LÖI p:0 l:[KEL_OH, VIH_OH] v:null n:2
17	t:UNO p:0 l:[VIH_N8] v:null n:0
18	t:VTO p:0 l:[VIH_N3] v:null n:0
</pre>
