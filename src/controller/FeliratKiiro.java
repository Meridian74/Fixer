package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modell.FeliratMezo;

public final class FeliratKiiro {
   
   // field változók
   private static File irandoFile;
   
   /* SINGLETON tervezési modell szerint -- csak belülről lesz a konstruktor elérhető, 
   ezáltal csak egy példány lesz a memóriában ebből a számoló osztályból */  
   public final static FeliratKiiro INSTANCE = new FeliratKiiro();
   
   private FeliratKiiro() {
      init();
   }

   private void init() {
      irandoFile = null;
   }

   
   public void feliratMentes(String eleresiUtvonal, ArrayList<FeliratMezo> felirat) {
      irandoFile = new File(eleresiUtvonal);
      if(irandoFile.exists()){
         if(irandoFile.delete())
            System.out.println("A " + eleresiUtvonal + " fájl törlésre került.");
         else 
            System.out.println("A " + eleresiUtvonal + " fájl törlésre nem sikerült!");
      }
      
      if(!irandoFile.exists()) {
         try {
            if(irandoFile.createNewFile())
               System.out.println("A " + eleresiUtvonal + " fájl létrehozva.");
         }
         catch(IOException ex) {
            System.out.println("A " + eleresiUtvonal + " fájl létrehozásánál hiba történt!");
         }
      }
      else
         System.out.println("A " + eleresiUtvonal + " fájl már létezik - de probléma lépett fel!");
         
      BufferedWriter bw = null;
      try {
         bw = new BufferedWriter(new FileWriter(irandoFile));

         for(FeliratMezo feliratMezo : felirat) {
            bw.write(feliratMezo.toString());
         }
         
      } catch (IOException ex) {
         System.out.println("I/O hiba!\n" + ex + "\n");
      }
      finally {
         try {
            if (bw!=null) {
               bw.close();
               System.out.println("Sikerült menteni és lezárni a " + eleresiUtvonal + " file-t.\n");
            }
         } 
         catch (IOException ex) {
            System.out.println("File lezárási hiba a " + eleresiUtvonal + " file-nál!\n" + ex + "\n");
         }
      }
   }
}
