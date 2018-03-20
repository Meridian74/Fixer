package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import modell.FeliratMezo;

public final class FeliratBeolvaso {
   private static int sorszam;
   private static int sorokSzama;
   private static String egySor;
   private static String idotartam;
   private static String szoveg;
   private static File olvasandoFile;
   private static boolean voltHiba;
   
   /* SINGLETON tervezési modell szerint -- csak belülről lesz a konstruktor elérhető, 
   ezáltal csak egy példány lesz a memóriában ebből a számoló osztályból */  
   public static final FeliratBeolvaso INSTANCE = new FeliratBeolvaso();

   private FeliratBeolvaso() {
      init();
   }
   
   private void init() {
      voltHiba = false;
      sorszam = -1;
      sorokSzama = -1;
      egySor = null;
      olvasandoFile = null;
   }
   
   public ArrayList<FeliratMezo> beolvasas(String eleresiUtvonal) {   
      
      ArrayList<FeliratMezo> feliratok = new ArrayList<>(); // létrehozunk egy dinamikus listát a feliratmezők számára
      
      olvasandoFile = new File(eleresiUtvonal);             // felirat-file megnyitása 
      BufferedReader br = null;
      try {
         br = new BufferedReader(new FileReader(olvasandoFile));
         System.out.println("Megnyitottam a " + eleresiUtvonal + " file.");
         
         // a feliratSor beolvasásának elkezdése - soronként értelmezve
         boolean jelzo = true;                        // hibajelző kapcsoló.
         while (jelzo && br.ready()) {                // addig amíg van még van a fileban következő sor és nincs hiba
            
            // Egy feliratmező első (sor)eleme egy sorszámnak kell(ene) lennie - de több üres sor is lehet előtte, ami NEM HIBA!
            jelzo = false;                            // az üres (vagy hibás) sorok átugrálás, amíg nincs egy sorszám
            
            while (!jelzo && br.ready()) {            
               egySor = br.readLine(); 
               sorokSzama++;           
               try {
                  sorszam = Integer.parseInt(egySor); // ha sikerült számmá konvertálni, akkor megvan a feliratSor sorszáma         
                  jelzo = true;
               } 
               catch (NumberFormatException e) {
                  System.out.println("  FIGYELEM! A [" + sorokSzama + "]. sorban sorszámnak kellett volna következnie!");
                  System.out.println("  Az előző jó sorszám: " + sorszam + "\n");
                  voltHiba = true;
                  // ha nem sikerült számmá konvertálni, akkor ez vagy üres sor volt, vagy hibás a file formátuma, de
                  // a sorszám keresése esetén még nem állunk meg, ha azt nem találjuk meg, más hiba esetén úgyis megáll később.
               }
            }
            
            // A sorszám után egy időintervallumnak kell(ene) következnie.
            if (jelzo && br.ready()) {                // amennyiben van még a fileban sor, akkor haladunk tovább
               egySor = br.readLine();
               sorokSzama++;  
               if (egySor.length() > 28) {            // az időformátum "00:00:08,940 --> 00:00:14,370", azaz legalább 29 karakter hosszú 
                  
                  if (egySor.substring(12, 17).equals(" --> "))   // ha van ilyen " --> " benne a megfeleő helyen, akkor
                     idotartam = egySor;                          // ez egy időtartam sor.
                                                                  // !! MÁS HIBALEHETŐSÉG ITT MOST NINCS ELLENŐRIZVE !!                                
                  else {
                     System.out.println("  HIBA! Az utolsó jó felirat sorszáma: " + (sorszam - 1));
                     System.out.println("  [" + sorokSzama + "]. sorban nem találtam meg a '-->' jelzést!");
                     System.out.println("  A hibás karakterstring amit helyette kaptam: {" + egySor.substring(13, 16) + "}");
                     System.out.println("  A file további olvasása megszakítva.");  
                     voltHiba = true;
                     jelzo = false;                   // megállítjuk a file feldolgozását.
                  }
                  
               } 
               else {
                  System.out.println("  HIBA! Az utolsó jó felirat sorszáma: " + (sorszam - 1));
                  System.out.println("  A [" + sorokSzama + "]. sor egy hiányos (rövid string) időintervallum sor!");
                  System.out.println("  A file további olvasása megszakítva.");  
                  voltHiba = true;
                  jelzo = false;                      // megállítjuk a file feldolgozását.
               }
               
            }
            
            // Ha van még sor - és ha eddig nem volt hiba, akkor most már a feliratSor szövege kell hogy következzen.
            // Ami akár üres is lehet, nem számít - de legalább egy üres sornak következnie kell majd, azért hogy
            // teljes legyen egy feliratmező. Sorszám, időintervallum, és egy feliratszöveg.
            if (jelzo && br.ready()) { 
               egySor = br.readLine();                // Beolvassuk a következő sort.
               sorokSzama++;
               
               if (egySor == "") {                    // ha volt még beolvasható sor, de az üres volt, akkor kiirjuk az alábbiakat...       
                  System.out.println("  FIGYELEM! Az előző jó felirat sorszáma: " + (sorszam - 1));
                  System.out.println("  A [" + sorokSzama + "]. sorban a feliratsor ÜRES volt!\n");
                  voltHiba = true;
                  egySor = " ";
               }                          
               szoveg = egySor;                       // feliratmező felirat szövegelemét elmentjük...
               feliratok.add(new FeliratMezo(sorszam, idotartam, szoveg)); // bővítjük a felirat adatlistát egy feliratmező elemmel.
            }
            else if (jelzo) {                         // ha nem volt további sor, akkor egy üres sort azért elmentünk...
               egySor = " ";
               szoveg = egySor;                       // feliratmező felirat szövegelemét elmentjük...
               feliratok.add(new FeliratMezo(sorszam, idotartam, szoveg)); // bővítjük a felirat adatlistát egy feliratmező elemmel.
               
               System.out.println("  FIGYELEM! Az előző jó felirat sorszáma: " + (sorszam - 1));
               System.out.println("  A [" + sorokSzama + "]. sor után véget ért a file, "
                                    + "de még kellett volna felirat szövegnek lennie!");
               System.out.println("  Egy üres szövegmező lett letárolva!");
               voltHiba = true;
               jelzo = false;
            }

            if (jelzo && br.ready()) { 
               egySor = br.readLine();                // minimum egy üres sor elválasztja a két feliratmezőt, ezt beolvassuk;
               sorokSzama++;
            }
            
         } // end of while -- ha a jelzo értéke true, folytatjuk a következő feliratmező soronkénti beolvasásával.
         
      } 
      catch (IOException ex) {
         System.out.println("I/O hiba!\n" + ex + "\n");
      }
      finally {
         try {
            if (br!=null) {
               br.close();
               if (!voltHiba)
                  System.out.println("  A felirat file hibamentes volt.");
               System.out.println("Lezártam a " + eleresiUtvonal + " file-t.\n");
            }
         } 
         catch (IOException ex) {
            System.out.println("File lezárási hiba a " + eleresiUtvonal + " file-nál!\n" + ex + "\n");
         }
      }
      init();                                         // olvasás után a segédadatok törlése, alaphelyzetbe kerülés
      return feliratok;                               // visszadjuk a beolvasott feliratmezők LISTÁJÁT
   }
   
}
