import java.io.Serializable;

public  class TestStreamServ  implements Serializable {

    private  static  final  long serialVersionUID =  1350092881346723535L;

    private String nom, prenom ;

    private  int salaire ;

    public TestStreamServ(String nom, String prenom) {
       this.nom = nom ;
       this.prenom = prenom ;
   }

    public String toString() {
      StringBuffer sb =  new StringBuffer() ;
       return sb.append(nom).append(" ").append(prenom).toString() ;
   }
}