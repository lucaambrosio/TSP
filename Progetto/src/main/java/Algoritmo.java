import java.util.ArrayList;
import java.util.List;

public class Algoritmo {
    private Integer[][] distanze;

    public Algoritmo(Integer[][] distanze) {
        this.distanze = distanze;
    }

    public Integer[][] getDistanze() {
        return distanze;
    }

    public void setDistanze(Integer[][] distanze) {
        this.distanze = distanze;
    }

    //metodo che restituisce il percorso calcolato con nn
    public Integer[] apply(){
        Integer[] lista;
        lista= new Integer[distanze.length];
        System.out.println("numero elementi : "+distanze.length);
        int cont=0;
        Integer partenza = Partenza.r.nextInt(distanze.length);
        System.out.println("partenza : "+partenza);
        lista[cont]=partenza;
        cont++;
        int next=partenza;
        for(int i=0;i<distanze.length-1;i++){
            if(i==0){
                next=trovaminimoinunariga(lista,partenza);
                lista[cont]=next;
                cont++;
                //System.out.println(next);
            }else{
                next=trovaminimoinunariga(lista,next);
                lista[cont]=next;
                cont++;
            }
        }
        return lista;
    }

    private int trovaminimoinunariga(Integer[] lista, int i) {
        int min=Integer.MAX_VALUE;
        int next=i;
        for (int j = 0; j < distanze[i].length; j++) {
            if(j==i){
                continue;
            }else{
                if(min>distanze[i][j] && (!contains(lista,j)) ){
                    min=distanze[i][j];
                    next=j;
                }
            }
            }
       return next;
    }

    private static boolean contains(Integer[] lista, int j) {
        boolean flag= false;
        for(int i=0;i<lista.length;i++){
            if(lista[i]!=null){
                if(lista[i]==j){
                    flag=true;
                }
            }
        }
        return flag;
    }
}
