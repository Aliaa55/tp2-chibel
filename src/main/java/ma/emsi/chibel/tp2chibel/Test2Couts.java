package ma.emsi.chibel.tp2chibel;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.output.TokenUsage;

/**
 * Test 2 : coût en tokens d'une requête.
 */
public class Test2Couts {

    // Prix pour 1 million de tokens du modèle gemini-3.5-flash-lite (en dollars)
    private static final double PRIX_MILLION_TOKENS_ENTREE = 0.30;
    private static final double PRIX_MILLION_TOKENS_SORTIE = 2.50;

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        String question = "Quelle est la capitale du Maroc ?";
        ChatResponse reponse = modele.chat(UserMessage.from(question));

        System.out.println("Question : " + question);
        System.out.println("Réponse : " + reponse.aiMessage().text());

        // Nombre de tokens utilisés
        TokenUsage usage = reponse.tokenUsage();
        int inputTokens = usage.inputTokenCount();
        int outputTokens = usage.outputTokenCount();
        int totalTokens = usage.totalTokenCount();
        System.out.println("Tokens en entrée : " + inputTokens);
        System.out.println("Tokens en sortie : " + outputTokens);
        System.out.println("Total des tokens : " + totalTokens);

        // Coût en dollars de la requête
        double cout = inputTokens * PRIX_MILLION_TOKENS_ENTREE / 1_000_000
                + outputTokens * PRIX_MILLION_TOKENS_SORTIE / 1_000_000;
        System.out.printf("Coût de la requête : %.8f $%n", cout);
        // Nombre de requêtes similaires pour dépenser 1 dollar
        System.out.printf("Nombre de requêtes pour dépenser 1 $ : %.0f%n", 1 / cout);
    }
}
