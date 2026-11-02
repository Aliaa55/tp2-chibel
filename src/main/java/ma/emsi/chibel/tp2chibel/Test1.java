package ma.emsi.chibel.tp2chibel;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

/**
 * Test 1 : pour se chauffer...
 * Pose des questions simples au LLM et affiche ses réponses.
 */
public class Test1 {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        // Création du modèle avec un builder
        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite") // gemini-2.5-flash n'est plus disponible
                .temperature(0.7)
                .build();

        // Question simple
        poser(modele, "Quelle est la capitale du Maroc ?");

        // L'heure qu'il est
        poser(modele, "Quelle heure est-il ?");

        // Présentation, puis demande du nom dans une deuxième question
        poser(modele, "Bonjour, je m'appelle Aliaa Chibel.");
        poser(modele, "Comment je m'appelle ?");
    }

    // Pose une question au modèle et affiche la question et la réponse
    private static void poser(ChatModel modele, String question) {
        String reponse = modele.chat(question);
        System.out.println("Question : " + question);
        System.out.println("Réponse : " + reponse);
        System.out.println("--------------------------------------------------");
    }
}
