import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.scene.paint.Color.*;


public class Main extends Application {
    static int distanza_finale=0;
    private int borderX = 1000;
    private int borderY = 600;
    private static List<Citta> cittaList;
    private static List<Integer> path;
    private static List<Citta> arrayPath2OP;

    public static void main(String[] args){
            //inizio a settare i parametri
        Integer iterazioni =10;
        ArrayList<Solution> soluzioni = new ArrayList<>();
        for(int iii=0;iii<iterazioni;iii++) {
            Partenza.setStartTime();
            Long seed = System.currentTimeMillis();
            Partenza.setR(seed);
            cittaList = new ArrayList<>();
            Integer dimesione = 0;
            Integer best=0;
            String nome = args[0];
            ClassLoader classLoader = new Main().getClass().getClassLoader();
            File file = new File(classLoader.getResource(nome).getFile());
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    i++;
                    if (line.equals("EOF")) {
                        break;
                    }
                    if (i < 8) {
                        if (i == 4) {
                            try{
                                System.out.println(line);
                                dimesione = Integer.parseInt(line.split(" ")[1]);
                            }catch (NumberFormatException e){
                                System.out.println(line);
                                dimesione = Integer.parseInt(line.split(" ")[2]);
                            }


                        }
                        if (i == 6) {
                            System.out.println(line);
                            best = Integer.parseInt(line.split(" ")[2]);
                            System.out.println("il viggio ottimo costa : "+ best);
                            Partenza.opt=best;

                        }
                    } else {
                        //System.out.println(line);
                        try {
                            String[] splittata = line.split(" ");
                            Integer numero_riga = Integer.parseInt(splittata[0]);
                            Double x = Double.parseDouble(splittata[1]);
                            Double y = Double.parseDouble(splittata[2]);
                            cittaList.add(new Citta(numero_riga, x, y));
                        } catch (NumberFormatException e) {
                            String[] splittata = line.split(" ");
                            Integer numero_riga = Integer.parseInt(splittata[1]);
                            Double x = Double.parseDouble(splittata[2]);
                            Double y = Double.parseDouble(splittata[3]);
                            //aggiungo le varie città
                            cittaList.add(new Citta(numero_riga, x, y));
                        }


                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //trovo tutte le varie distanze
            Integer[][] distanze = new Integer[dimesione][dimesione];
            for (int i = 0; i < dimesione; i++) {
                for (int j = 0; j < dimesione; j++) {
                    distanze[i][j] = distanza(cittaList.get(i), cittaList.get(j));
                }
            }


            //System.out.println("---------------------------------------NN------------------------------------------------");

            //creo algoritmo e mi ritoena il viaggio
            Algoritmo algoritmo = new Algoritmo(distanze);
            //applico l'algoritmo
            Integer[] viaggio = algoritmo.apply();
            //nella variabile viaggio avrò i vari nodi suoi quali passare
            //adesso stampiamo il viaggio che il nostro algoritmo ha calcolato
            //con il nostro NN il viaggio migliore e\':
            Citta[] cittaviaggio = new Citta[viaggio.length + 1];
            for (int i = 0; i < viaggio.length; i++) {
                cittaviaggio[i] = (cittaList.get(viaggio[i]));
            }
            cittaviaggio[cittaviaggio.length - 1] = cittaviaggio[0];

            //in viaggio ho il viaggio con NN
            //adesso eseguo l'algoritmo twoopt


            Citta[] migliore = TwoOpt.alternate(cittaviaggio);
            path = new ArrayList<>();
            //System.out.println("--------------------------------------------------Two Opt----------------------------------------");

            //array di appoggio per permettere la rappresentazione grafica del percorso trovato
            arrayPath2OP = Stream.of(migliore).collect(Collectors.toList());

            //System.out.println("------------------------------------Simulated Annealing------------------------------------------");
            //GetD.setCittaList(cittaList);

            AlgoritmoSA algoritmoSA = new AlgoritmoSA(migliore);
            Citta[] finale = algoritmoSA.doIt();
            distanza_finale = GetD.totalDistance(finale);
            System.out.println("la distanza finale e\' : " + distanza_finale);
            System.out.println("il SEED utilizzato e\': " + seed);
            soluzioni.add(new Solution(distanza_finale,seed));
            //utilizzato solo per rappresentazione grafica
            arrayPath2OP = Stream.of(finale).collect(Collectors.toList());

            //scrivo la soluzione
            //questo file mi permette di utilizzare tourChecker.py
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(new File(nome + ".tour"));
                File file_in = new File(classLoader.getResource(nome).getFile());
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file_in));
                for (int i = 0; i < 4; i++) {
                    printWriter.println(bufferedReader.readLine());
                }
                printWriter.println("TOUR_SECTION");
                for (int i = 0; i < finale.length - 1; i++) {
                    printWriter.println(finale[i].getId());
                }
                printWriter.println("-1");
                printWriter.println("EOF");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            printWriter.close();
            //System.out.println("ho finito!!!!!");
        }

        Integer minore=Integer.MAX_VALUE;
        int index=-1;
        for(int i=0;i<iterazioni;i++){
            if(soluzioni.get(i).distanza<minore){
                minore=soluzioni.get(i).distanza;
                index=i;
            }

        }

        System.out.println("il viaggio migliore e\' : "+soluzioni.get(index).getDistanza());
        System.out.println("il seed utilizzato e\' : "+soluzioni.get(index).getSeed());



        launch(args);

    }




    //funzione che calcola le distanze
    public static Integer distanza(Citta citta, Citta citta1) {
        //distanza euclidea
        double x = Math.abs(citta.getX() - citta1.getX());
        double y = Math.abs(citta.getY() - citta1.getY());
        return (int) Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }





    @Override
    public void start(Stage primaryStage) {
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (Citta city : cittaList) {
            if (city.getX() > maxX) maxX = city.getX();
            if (city.getY() > maxY) maxY = city.getY();
        }
        double factorX = borderX / maxX, factorY = borderY / maxY;
        BorderPane root = new BorderPane();
        int translation =0;
        printCityPoint((float) factorX, (float) factorY, root, translation);
        printLine(arrayPath2OP, factorX, factorY, root, translation);
        Scene scene = new Scene(root, borderX, borderY + translation * 2);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void printCityPoint(float fattoreX, float fattoreY, BorderPane root, int traslazione) {
        for (Citta c : cittaList) {
            Circle p = new Circle((float) c.getX().doubleValue() * fattoreX, (float) c.getY().doubleValue() * fattoreY + traslazione, 4, GREEN);
            root.getChildren().add(p);
        }
    }

    private void printLine(List<Citta> arrayPath, double fattoreX, double fattoreY, BorderPane root, int traslazione) {
        for (int i = 1; i < arrayPath.size(); i++) {
            Line l = new Line(arrayPath.get(i - 1).getX() * fattoreX,
                    arrayPath.get(i - 1).getY() * fattoreY + traslazione,
                    arrayPath.get(i).getX() * fattoreX,
                    arrayPath.get(i).getY() * fattoreY + traslazione);
            root.getChildren().add(l);
        }
    }

    private static void shuffleArray(Citta[] array)
    {
        int index=0;
        Citta temp;
        Random random = Partenza.r;
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }






}
