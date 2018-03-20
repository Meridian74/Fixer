package controller;

import java.io.File;
import modell.FileLista;

public final class FileValogato {
   private static String fileUtvonal;
   private static String [] fileokListaja;
   
   /* SINGLETON tervezési modell szerint -- csak belülről lesz a konstruktor elérhető, 
   ezáltal csak egy példány lesz a memóriában ebből a számoló osztályból */ 
   public static final FileValogato INSTANCE = new FileValogato();
   
   private FileValogato() {
      setFileUtvonal();
      setFileokListaja();
   }
   
   public void setFileokListaja() {
      System.out.println("Fileok elérésének helye: " + fileUtvonal); 
      File feliratokHelye = new File(fileUtvonal);
      fileokListaja = feliratokHelye.list(new FileLista("srt"));  // .srt kiterjesztésre szűrés
   }
   
   public void setFileUtvonal() {
      this.fileUtvonal = "C:/--feliratok/";
   }
   
   public String [] getFileokListaja() {
      return fileokListaja;
   }
   
   public String getFileUtvonal() {
      return fileUtvonal;
   }
   
   public void mentesiMappaLetrehozas(String utvonal) {
      // mentési mappa inicializálás a munkakönyvtáron belül, ide lesz majd mentve a javított feliratok
      File mappa = new File(utvonal, "FIXED");
      if(!mappa.exists()) {
         if(mappa.mkdir())
            System.out.println("A FIXED mappa létrejött, ahová a fileok mentése történik.\n");
         else 
            System.out.println("A mentési mappa nem tudott létrejönni!\n");
      }
      else
         System.out.println("A mentési mappa már létezett, nem kellett létrehozni.\n");
   }
   
}
