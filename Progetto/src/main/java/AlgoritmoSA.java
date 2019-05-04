import java.util.Arrays;


public class AlgoritmoSA {
    public static Citta[] arrayPath;

    public AlgoritmoSA(Citta[] arrayPath2OP) {
        arrayPath = arrayPath2OP;
    }

    public static Citta[] doIt() {
        double temperature = 100, coolingRate = 0.95;
        Citta[] current = arrayPath;
        Citta[] best = current;
        while ((System.currentTimeMillis() - Partenza.startTime) < 150000 || GetD.totalDistance(best)<Partenza.opt) {
            if(GetD.totalDistance(best)<Partenza.opt){
                System.err.println("ho trovato il viaggio ottimale");
            }
            for (int i = 0; i < 100; i++) {
                Citta[] solution = TwoOpt.alternate(swap(current));
                int distNext =GetD.totalDistance(solution);
                int distCurrent = GetD.totalDistance(current);
                if (distNext < distCurrent) {
                    current = solution;
                    if (distNext < GetD.totalDistance(best)) {
                        best = solution;
                    }
                } else if (randomaVerifica(distNext, distCurrent, temperature)) {
                    current = solution;
                }
            }
            temperature *= coolingRate;
        }
        return best;
    }

    private static boolean randomaVerifica(int next, int current, double temp) {
        double r = Partenza.r.nextDouble();
        double difference = -((double) next - (double) current);
        double d = Math.pow(Math.E, (difference / temp));
        return (r < d);
    }

    public static Citta[] swap(Citta[] current) {
        Citta[] appoggio = new Citta[current.length];
        int a = 0, b = 1, c = 2, d = 3;
        int posizioni[] = new int[4];

        for (int i = 0; i < posizioni.length; i++) {
            posizioni[i] = Partenza.r.nextInt(current.length - 1);

        }

        Arrays.sort(posizioni);
        int contatoreVettore = 0;
        appoggio[contatoreVettore] = current[posizioni[a]];
        contatoreVettore++;
        for (int i = posizioni[c] + 1; i <= posizioni[d]; i++) {
            appoggio[contatoreVettore] = current[i];
            contatoreVettore++;
        }
        for (int i = posizioni[b] + 1; i <= posizioni[c]; i++) {
            appoggio[contatoreVettore] = current[i];
            contatoreVettore++;
        }
        for (int i = posizioni[a] + 1; i <= posizioni[b]; i++) {
            appoggio[contatoreVettore] = current[i];
            contatoreVettore++;
        }
        for (int i = posizioni[d] + 1; i < current.length - 1; i++) {
            appoggio[contatoreVettore] = current[i];
            contatoreVettore++;
        }
        for (int i = 0; i < posizioni[a]; i++) {
            appoggio[contatoreVettore] = current[i];
            contatoreVettore++;
        }
        //System.out.println("Lunghezza" + current.length + " " + posizioni[a] + " " + posizioni[b] + " " + posizioni[c] + " " + posizioni[d] + " Contavettore " + contatoreVettore);
        appoggio[contatoreVettore++] = current[posizioni[a]];
        return appoggio;
    }

}