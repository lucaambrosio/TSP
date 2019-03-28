


import java.util.ArrayList;
import java.util.List;

public class TwoOpt {
        public static Citta[] alternate(Citta[] citta) {
            Citta[] nuovoviaggio;
            double migliorDist = GetD.totalDistance(citta);
            double nuovadistanza;
            int swaps = 1;
            int miglioramenti = 0;
            int iterazioni = 0;
            long comparazioni = 0;

            while (swaps != 0) { //ciclo fino a quando non apporto più cambiamenti
                swaps = 0;


                for (int i = 1; i < citta.length - 2; i++) {
                    for (int j = i + 1; j < citta.length - 1; j++) {
                        comparazioni++;
                        //controlla la distanza tra A e B + la distanza tra C e D e la confronta con la distanza tra A e C + la distanza tra B e D
                        // se è migliore chiamo il metodo di swap che mi cambia le citta di passaggio
                        if (Main.distanza(citta[i],citta[i-1]) + Main.distanza(citta[j + 1],(citta[j])) >=
                                (Main.distanza(citta[i],(citta[j + 1]) )+ Main.distanza(citta[i - 1],(citta[j]))))
                        {

                            nuovoviaggio = swap(citta, i, j); //pass arraylist and 2 points to be swapped.

                            nuovadistanza = GetD.totalDistance(nuovoviaggio);

                            if (nuovadistanza < migliorDist) { //if the swap results in an improved distance, increment counters and update distance/tour
                                citta = nuovoviaggio;
                                migliorDist = nuovadistanza;
                                swaps++;
                                miglioramenti++;
                            }
                        }
                    }
                }
                iterazioni++;
            }
            //stampo le statisti che dell'algoritmo
/*           System.out.println("comparazioni effettuate: " + comparazioni);
            System.out.println("Miglioramenti effettuati: " + miglioramenti);
            System.out.println("Iterazioni del algoritmo: " + iterazioni);*/
            return citta;
        }

        private static Citta[] swap(Citta[] citta, int i, int j) {
            //esegue un 2 opt swap invertendo l'ordine dei punti tra i e j

            //prendere array fino al primo punto i e aggiungere a nuovoviaggio
            int size = citta.length;
            Citta[] nuovoviaggio = new Citta[size];
            int cont=0;
            for (int c = 0; c <= i - 1; c++) {
                nuovoviaggio[cont]=citta[c];
                cont++;
            }

            //Inverte l'ordine tra 2 punti passati i e j e aggiungere a nuovoviaggio
            int dec = 0;
            for (int c = i; c <= j; c++) {
                nuovoviaggio[cont]=citta[j - dec];
                cont++;
                dec++;
            }

            //aggiungi array dal punto j alla fine a nuovoviaggio

            for (int c = j + 1; c < size; c++) {
                nuovoviaggio[cont]=citta[c];
                cont++;
            }

            return nuovoviaggio;
        }
    }
