package ru.mirea.pkmn.kryukovakn.web.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PkmnHttpClient {
    Retrofit client;
    PokemonTcgAPI tcgAPI;

    public PkmnHttpClient(){
        client = new Retrofit.Builder()
                .baseUrl("https://api.pokemontcg.io")
                .addConverterFactory(JacksonConverterFactory.create(new JsonMapper()))
                .build();

        tcgAPI = client.create(PokemonTcgAPI.class);
    }

    public JsonNode getPokemonCard(String name, String number) throws IOException {
        String requestQuery = "name:\""+name+"\"" + " " + "number:"+number;

        Response<JsonNode> response = tcgAPI.getPokemon(requestQuery).execute();

        return response.body();
    }
    public Set<String> loadAttackDescriptions(String cardName, String cardNumber) {
        Set<String> descriptions = new HashSet<>();
        PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();

        try {
            JsonNode cardNode = pkmnHttpClient.getPokemonCard(cardName, cardNumber);

            if (cardNode.has("attacks")) {
                for (JsonNode attack : cardNode.get("attacks")) {
                    if (attack.has("text")) {
                        String description = attack.get("text").asText();
                        descriptions.add(description);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return descriptions;
    }
}
