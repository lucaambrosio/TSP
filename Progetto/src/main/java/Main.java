import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Citta> cittaList;

    public static void main(String[] args){
        cittaList = new ArrayList<>();
        Integer dimesione=0;
        System.out.println("ciao mondo!!!");
        String nome=args[0];
        System.out.println("il nume del file e\' :"+nome);
        ClassLoader classLoader = new Main().getClass().getClassLoader();
        File file = new File(classLoader.getResource(nome).getFile());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i=0;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                i++;
                if(line.equals("EOF")){
                    break;
                }
                if(i<8){
                    if(i==4) {
                        System.out.println(line);
                        dimesione=Integer.parseInt(line.split(" ")[1]);

                    }
                }else{
                    //System.out.println(line);
                    try{
                        String[] splittata=line.split(" ");
                        Integer numero_riga=Integer.parseInt(splittata[0]);
                        Double coordx=Double.parseDouble(splittata[1]);
                        Double coordy= Double.parseDouble(splittata[2]);
                        System.out.println("il numero della riga e\'"+numero_riga);
                        System.out.println("la coordinata X e\' : "+coordx);
                        System.out.println("la coordinata Y e\' : "+coordy);
                        System.out.println();
                        //aggiungo le varie città
                        cittaList.add(new Citta(numero_riga,coordx,coordy));
                    }catch (NumberFormatException e){
                        String[] splittata=line.split(" ");
                        Integer numero_riga=Integer.parseInt(splittata[1]);
                        Double coordx=Double.parseDouble(splittata[2]);
                        Double coordy= Double.parseDouble(splittata[3]);
                        System.out.println("il numero della riga e\'"+numero_riga);
                        System.out.println("la coordinata X e\' : "+coordx);
                        System.out.println("la coordinata Y e\' : "+coordy);
                        System.out.println();
                        //aggiungo le varie città
                        cittaList.add(new Citta(numero_riga,coordx,coordy));
                    }


                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //trovo tutte le varie distanze
        Integer[][] distanze=new Integer[dimesione][dimesione];
        for(int i=0;i<dimesione;i++){
            for(int j=0;j<dimesione;j++){
                distanze[i][j]=distanza(cittaList.get(i), cittaList.get(j));
            }
        }


        //stampo la matrice
        //stampo la matrice delle distanze
        for (int i = 0; i < distanze.length; i++) {
            for (int j = 0; j < distanze[i].length; j++) {
                    System.out.print(distanze[i][j] + " ");
            }
            System.out.println();
        }
        //creo algoritmo e mi ritoena il viaggio
        Algoritmo algoritmo = new Algoritmo(distanze);
        //applico l'algoritmo
        List<Integer> viaggio=algoritmo.apply();
        //nella variabile viaggio avrò i vari nodi suoi quali passare
        //adesso stampiamo il viaggio che il nostro algoritmo ha calcolato
        //con il nostro NN il viaggio migliore e\':
        List<Citta> cittaviaggio= new ArrayList<Citta>();
        for(int i=0;i<viaggio.size();i++){
            System.out.println(viaggio.get(i));
            cittaviaggio.add(cittaList.get(viaggio.get(i)));
        }
        Integer distanzatotale= 0;

        distanzatotale = lunghezzaviaggio(viaggio);
        System.out.println("la distanza totale e\' : "+distanzatotale);
        //in viaggio ho il viaggio con NN
        //adesso eseguo l'algoritmo twoopt
        List<Citta> migliore=TwoOpt.alternate(cittaviaggio);
        System.out.println("la lunghezza con il 2 opt algoritmo e\' : "+ lunghezzaviaggiocitta(migliore));

    }

    public static Integer lunghezzaviaggio(List<Integer> viaggio) {
        Integer distanzatotale=0;
        for(int i=0;i<cittaList.size();i++){
            System.out.println("la"+i+ " citta\' e\' :" + viaggio.get(i));
            if(i!=cittaList.size()-1){
                Integer distanza =0;
                distanza=distanza(cittaList.get(i),cittaList.get(i+1));
                distanzatotale+=distanza;
                //System.out.println("la distanza tra "+ i + " e " + i+1 +" e\' "+distanza );
            }else{
                System.out.print("");
                Integer distanza =0;
                distanza=distanza(cittaList.get(0),cittaList.get(i));
                distanzatotale+=distanza;
            }


        }
        return distanzatotale;
    }


    public static Integer lunghezzaviaggiocitta(List<Citta> viaggio) {
        Integer distanzatotale=0;
        for(int i=0;i<cittaList.size();i++){
            if(i!=cittaList.size()-1){
                Integer distanza =0;
                distanza=distanza(viaggio.get(i),viaggio.get(i+1));
                distanzatotale+=distanza;
            }else{
                Integer distanza =0;
                distanza=distanza(viaggio.get(0),viaggio.get(i));
                distanzatotale+=distanza;
            }


        }
        return distanzatotale;
    }
    //funzione che calcola le distanze
    public static Integer distanza(Citta citta, Citta citta1) {
        //distanza euclidea
        return (int) Math.round(Math.sqrt(Math.pow(citta.getCoordx()-citta1.getCoordx(),2)+Math.pow(citta.getCoordy()-citta1.getCoordy(),2)));
    }
}
