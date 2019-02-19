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

    //per adesso ho intezione di applicare un algoritmo nearest neigthboard
    public List<Integer> apply(){
        List<Integer> lista = new ArrayList<>();
        lista.add(0);
        int next=0;
        for(int i=0;i<distanze.length;i++){
            if(i==0){
                next=trovaminimoinunariga(lista,0);
                lista.add(next);
            }else{
                next=trovaminimoinunariga(lista,next);
                lista.add(next);
            }
            //System.out.println(next);
        }
        return lista;
    }

    private int trovaminimoinunariga(List<Integer> lista, int i) {
        int min=Integer.MAX_VALUE;
        int next=i;
        for (int j = 0; j < distanze[i].length; j++) {
            if(j==i){
                continue;
            }else{
                if(min>distanze[i][j] && (!lista.contains(j)) ){
                    //System.out.println("scambio");
                    min=distanze[i][j];
                    next=j;
                }
            }
            }
       return next;
    }
}
