public class GetD {

    public  static int totalDistance(Citta [] arratPath){
        int somma=0;
        for (int i =0 ; i < arratPath.length-1; i++){
            somma+=Main.distanza(arratPath[i],arratPath[i+1]);
        }
        return somma;
    }

}
