package ma.emsi.chibel.tp2chibel;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

/**
 * Test 3 : un traducteur, avec PromptTemplate et Prompt.
 */
public class Test3Traducteur {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        // Template avec une seule variable (nécessairement nommée "it") : le texte à traduire
        PromptTemplate template1 = PromptTemplate.from("Traduis le texte suivant en anglais : {{it}}");
        Prompt prompt1 = template1.apply("Le chat dort sur le canapé.");
        System.out.println("Prompt : " + prompt1.text());
        System.out.println("Réponse : " + modele.chat(prompt1.text()));
        System.out.println("--------------------------------------------------");

        // Template avec 2 variables : la langue de traduction et le texte à traduire
        PromptTemplate template2 = PromptTemplate.from("Traduis le texte suivant en {{langue}} : {{texte}}");
        Prompt prompt2 = template2.apply(Map.of(
                "langue", "espagnol",
                "texte", "Le chat dort sur le canapé."
        ));
        System.out.println("Prompt : " + prompt2.text());
        System.out.println("Réponse : " + modele.chat(prompt2.text()));
    }
}
