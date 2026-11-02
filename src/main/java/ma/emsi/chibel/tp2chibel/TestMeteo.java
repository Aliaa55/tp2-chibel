package ma.emsi.chibel.tp2chibel;

import ma.emsi.chibel.tp2chibel.outil.meteo.MeteoTool;

/**
 * Teste la méthode isPluiePrevue de la classe MeteoTool (sans LLM).
 */
public class TestMeteo {

    public static void main(String[] args) {
        MeteoTool meteoTool = new MeteoTool();
        // Paris : latitude 48,85 et longitude 2,35
        double latitude = 48.85;
        double longitude = 2.35;
        System.out.println("Pluie prévue à Paris pour 3 jours à partir d'aujourd'hui : "
                + meteoTool.isPluiePrevue(latitude, longitude, 0, 3));
        System.out.println("Pluie prévue à Paris pour 2 jours à partir de demain : "
                + meteoTool.isPluiePrevue(latitude, longitude, 1, 2));
    }
}
