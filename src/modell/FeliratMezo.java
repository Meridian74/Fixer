package modell;

public class FeliratMezo {
   private String sorszam;
   private String idoIntervallum;
   private String szoveg;
   
   public FeliratMezo(int sor, String ido, String szoveg) {
      setSorszam(Integer.toString(sor));
      setIdoIntervallum(ido);
      setSzoveg(szoveg);
   }
   
   public FeliratMezo(String sor, String ido, String szoveg) {
      setSorszam(sor);
      setIdoIntervallum(ido);
      setSzoveg(szoveg);
   }
   
   public String getSorszam() {
      return sorszam;
   }

   public void setSorszam(String sorszam) {
      this.sorszam = sorszam;
   }

   public String getIdoIntervallum() {
      return idoIntervallum;
   }

   public void setIdoIntervallum(String idoIntervallum) {
      this.idoIntervallum = idoIntervallum;
   }

   public String getSzoveg() {
      return szoveg;
   }

   public void setSzoveg(String szoveg) {
      this.szoveg = szoveg;
   }
   
   @Override
   public String toString() {
      return (this.getSorszam() + "\n" 
            + this.getIdoIntervallum() + "\n"
            + this.getSzoveg() + "\n");
   }
   
}
