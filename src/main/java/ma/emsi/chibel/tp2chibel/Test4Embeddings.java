package ma.emsi.chibel.tp2chibel;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;
import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

/**
 * Test 4 : similarité de 2 phrases par la métrique du cosinus appliquée à leurs embeddings.
 * Compare un modèle d'embeddings intégré à LangChain4j et un modèle externe de Google.
 */
public class Test4Embeddings {

    // Couples de phrases dont on veut calculer la similarité
    private static final String[][] COUPLES = {
            {"Bonjour, comment allez-vous ?", "Salut, quoi de neuf ?"},
            {"J'adore ce film, il est génial.", "Je déteste ce film, il est nul."},
            {"Le chat dort sur le canapé.", "Un félin se repose sur le sofa."},
            {"Le chat dort sur le canapé.", "La bourse de Casablanca a baissé aujourd'hui."},
            {"Il pleut beaucoup à Rabat ce matin.", "Le temps est très humide à Rabat aujourd'hui."},
            {"J'apprends à programmer en Java.", "La recette du couscous demande de la semoule."}
    };

    public static void main(String[] args) {
        // Modèle d'embeddings intégré à LangChain4j (ne coûte rien en tokens, entraîné avec des textes en anglais)
        EmbeddingModel modeleIntegre = new BgeSmallEnV15EmbeddingModel();

        // Modèle d'embeddings externe de Google, plus performant pour le français
        String cle = System.getenv("GEMINI_KEY");
        EmbeddingModel modeleGoogle = GoogleGenAiEmbeddingModel.builder()
                .apiKey(cle)
                .modelName("gemini-embedding-001")
                .taskType(GoogleGenAiEmbeddingModel.TaskTypeEnum.SEMANTIC_SIMILARITY)
                .timeout(Duration.ofSeconds(2)) // 100 ms ne suffisait pas (timeout)
                .build();

        for (String[] couple : COUPLES) {
            System.out.println("Phrase 1 : " + couple[0]);
            System.out.println("Phrase 2 : " + couple[1]);
            System.out.printf("Similarité (modèle intégré bge-small-en-v1.5) : %.4f%n",
                    similarite(modeleIntegre, couple[0], couple[1]));
            System.out.printf("Similarité (modèle Google gemini-embedding-001) : %.4f%n",
                    similarite(modeleGoogle, couple[0], couple[1]));
            System.out.println("--------------------------------------------------");
        }
    }

    // Calcule la similarité cosinus entre les embeddings de 2 phrases
    private static double similarite(EmbeddingModel modele, String phrase1, String phrase2) {
        Response<Embedding> reponse1 = modele.embed(phrase1);
        Response<Embedding> reponse2 = modele.embed(phrase2);
        Embedding emb1 = reponse1.content();
        Embedding emb2 = reponse2.content();
        return CosineSimilarity.between(emb1, emb2);
    }
}
