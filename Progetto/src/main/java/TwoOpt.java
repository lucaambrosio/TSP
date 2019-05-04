
public class TwoOpt {

    public static Citta[] alternate(Citta[] citta) {

        int change, mini = 0, minj = 0, minChange;
        int giri = 0;
        do {
            minChange = Integer.MAX_VALUE;
            for (int i = 0; i < citta.length - 1; i++) {
                for (int j = i + 1; j < citta.length - 1; j++) {
                    change = Main.distanza(citta[i],citta[j]) + Main.distanza(citta[i + 1],(citta[j+1]))-
                            Main.distanza(citta[i],(citta[i + 1]) )- Main.distanza(citta[j],(citta[j+1]));
                    if (minChange > change) {
                        minChange = change;
                        mini = i;
                        minj = j;
                        citta = swap(citta,mini, minj);
                    }

                }
            }

            giri++;

        } while (minChange < 0);
        return citta;
    }









    private static Citta[] swap(Citta[] citta ,int i, int j) {
        Citta[] appoggio = new Citta[citta.length];
        for (int v = 0; v <= i; v++) {
            appoggio[v] = citta[v];
        }
        int conta = 0;
        for (int v = i + 1; v <= j; v++) {
            appoggio[v] = citta[j - conta];
            conta++;
        }
        for (int v = j + 1; v < citta.length - 1; v++) {
            appoggio[v] = citta[v];
        }
        appoggio[appoggio.length - 1] = appoggio[0];
        return appoggio;
    }
    }
