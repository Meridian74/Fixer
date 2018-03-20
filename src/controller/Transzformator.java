package controller;

import java.util.ArrayList;
import modell.FeliratMezo;

public final class Transzformator {
   //field változók
   private static FeliratMezo elsoElem;
   private static FeliratMezo masodikElem;
   
   /* SINGLETON tervezési modell szerint -- csak belülről lesz a konstruktor elérhető, 
   ezáltal csak egy példány lesz a memóriában ebből a számoló osztályból */    
   public final static Transzformator INSTANCE = new Transzformator();
   
      private Transzformator() {
         init();
      }
      
      private void init() {
         this.elsoElem = null;
         this.masodikElem = null;
      }
      
      public ArrayList<FeliratMezo> atalakito(ArrayList<FeliratMezo> eredetiFelirat) {
         
         ArrayList<FeliratMezo> javitottFelirat = new ArrayList<>(); // létrehozunk egy dinamikus listát a javitott felirat számára
         
         // az első feliratelem változatlan marad - ez így lesz majd jó.
         elsoElem = eredetiFelirat.get(0);
         elsoElem.setSzoveg(elsoElem.getSzoveg() + "\n");            // a szöveg után legyen egy üres sor is.
         javitottFelirat.add(elsoElem);
         
         // a további feliratelemeket már kettesével vesszük elő
         int i = 1;
         int size = eredetiFelirat.size();
         while (i < size) {
            elsoElem = eredetiFelirat.get(i);
            if ((i + 1) < size) {
               masodikElem = eredetiFelirat.get(i + 1);
            }
            else {   // ha már nincs több feliratmezo elem, akkor egy üres sort teszünk a végéhez.
               masodikElem.setSzoveg("");
            }
            // a feliratokat ketteséve összevonjuk, úgy, hogy vesszük az első sorszámát és időintervallumát
            // és a szöveg (felirat) részéhez a másodiknak csupán a szöveg (feliratát) hozzáadjuk, egy új sorban
            // és csak az így összepárosított feliratmezőt mentjük el csak az új adatlistába
            elsoElem.setSzoveg(elsoElem.getSzoveg() + "\n" + masodikElem.getSzoveg() + "\n");
            javitottFelirat.add(elsoElem);
            i = i + 2;  // kettesével haladunk, mivel két-két feliratot párosítunk össze a fentebb írt módon.
         }
         
         return javitottFelirat; // visszaadjuk az új, és már kijavított feliratot.
      }
   
}
