package ma.emsi.chibel.tp2chibel;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import ma.emsi.chibel.tp2chibel.outil.meteo.MeteoTool;

import java.util.Scanner;

/**
 * Test 7 : utilisation d'outils par le LLM.
 */
public class Test7Outils {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(20)) // L'utilisation des outils génère des messages
                        .tools(new MeteoTool())  // Ajout de l'outil
                        .build();

        conversationAvec(assistant);
    }

    // Conversation avec l'assistant ; taper "fin" pour arrêter
    private static void conversationAvec(AssistantMeteo assistant) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("==================================================");
                System.out.println("Posez votre question : ");
                String question = scanner.nextLine();
                if (question.isBlank()) {
                    continue;
                }
                System.out.println("==================================================");
                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }
                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
                System.out.println("==================================================");
            }
        }
    }
}
