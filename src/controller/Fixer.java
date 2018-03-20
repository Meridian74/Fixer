package controller;

import java.util.ArrayList;
import modell.FeliratMezo;

public class Fixer {
   private static FeliratBeolvaso feliratOlvaso;
   private static FileValogato feliratLista;
   private static Transzformator feliratAtalakito;
   private static FeliratKiiro feliratKiiro;
         
   public static void main(String[] args) {
      feliratOlvaso = FeliratBeolvaso.INSTANCE;    // beolvasó programrész betöltése
      feliratLista = FileValogato.INSTANCE;        // feliratok kiválasztásának programja
      feliratAtalakito = Transzformator.INSTANCE;  // ez a rész alakítja át az egysoros feliratokat 2 sorosokra
      feliratKiiro = FeliratKiiro.INSTANCE;        // ez a programrész menti el a javított feliratokat

      String utvonal = feliratLista.getFileUtvonal();       // a munkakönyvtár útvonala
      feliratLista.mentesiMappaLetrehozas(utvonal);         // a mentési mappa létrehozása a munkakönyvtáron belül
      String [] fileok = feliratLista.getFileokListaja();   // az ".srt" kiterjesztésű file-ok listája a munkakönyvtárban
      
      // végigmegyünk a kapott file-ok listáján
      for(String feliratFile : fileok) {
         // létrehozunk két adatlistát aminek elemei FeliratMezo-k.
         ArrayList<FeliratMezo> eredeti = new ArrayList<>();   
         ArrayList<FeliratMezo> javitott = new ArrayList<>();
         
         // A felirat adatlistánk feltöltése FeliratMezo-kkel.
         eredeti = feliratOlvaso.beolvasas(utvonal + feliratFile);   
         
         // az eredeti felirat megjavítása
         javitott = feliratAtalakito.atalakito(eredeti);
         
         // a javitott felirat kiírása fileba
         feliratKiiro.feliratMentes((utvonal + "FIXED/" + feliratFile), javitott);   
         
      }
   }
}
