package ma.emsi.chibel.tp2chibel;

/**
 * Assistant IA qui peut utiliser des outils pour connaître les prévisions météo.
 * L'implémentation est faite par LangChain4j (AiServices).
 */
public interface AssistantMeteo {
    // Prend un message de l'utilisateur et retourne une réponse du LLM.
    String chat(String userMessage);
}
