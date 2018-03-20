package modell;

import java.io.File;
import java.io.FilenameFilter;

public class FileLista implements FilenameFilter {
   private String kiterjesztes;
   
   public FileLista(String kiterjesztes) {
      this.kiterjesztes = kiterjesztes.toUpperCase();
   }

   @Override
   public boolean accept(File dir, String nev) {
      return nev.toUpperCase().endsWith(kiterjesztes);
   }

}
