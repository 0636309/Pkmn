package ru.mirea.pkmn.kryukovakn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mirea.pkmn.kryukovakn.web.jdbc.DatabaseServiceImpl;

import java.io.IOException;
import java.sql.SQLException;


@SpringBootApplication // Обязательная аннотация!
public class PkmnApplication {
    public static final long serialVersionUID = 1L;

    public static void main(String[] args) throws IOException, SQLException {


        // 4 практика

        /*String filePath = "src\\main\\resources\\Lesha.txt";
        CardImport cardImport_ = new CardImport(filePath);
        Card loadedCard = cardImport_.loadCard();

        if (loadedCard != null) {
            System.out.println("\u001b[38;5;183mВывод из текстового файла (без описания JSON):\u001b[38;5;15m");
            System.out.println(loadedCard);
        } else {
            System.out.println("Данные не найдены.");
        }

        if (loadedCard.getEvolvesFrom() != null) {
            System.out.println("Эволюционирует из:");
            System.out.println(loadedCard.getEvolvesFrom());

        }
        PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();

        JsonNode card = pkmnHttpClient.getPokemonCard(loadedCard.getName(), loadedCard.getNumber());
        System.out.println("\u001b[38;5;183mВывод JSON структуры покемона: \u001b[38;5;15m");
        System.out.println(card.toPrettyString());

        Set<String> attackSkillsText = card.findValues("attacks")
                .stream()
                .flatMap(attack -> attack.findValues("text").stream()) // выводим поле text из атакующих умений
                .map(JsonNode::toPrettyString)
                .collect(Collectors.toSet());*/

        // Вывод структуры с умениями
        //System.out.println(attackSkillsText);
        /*List<String> attackDescriptions = new ArrayList<>(attackSkillsText);


        List<AttackSkill> skills = loadedCard.getSkills();
        int size = Math.min(skills.size(), attackDescriptions.size());

        for (int i = 0; i < size; i++) {
            skills.get(i).setDescription(attackDescriptions.get(i));
        }

        System.out.println("\u001b[38;5;183mВывод c добавлением описания: \u001b[38;5;15m");
        System.out.println(loadedCard);
        // stop
        CardExport cardExport = new CardExport();
        cardExport.saveCard(loadedCard);

        if (loadedCard.getEvolvesFrom() != null) {
            CardExport cardEvolvesExport = new CardExport();
            cardEvolvesExport.saveCard(loadedCard.getEvolvesFrom());

        }
        String loadFilePath = "Kangaskhan.crd";
        CardImport cardImport = new CardImport(loadFilePath);
        Card loadedCarddes = cardImport.deserializeCard(loadFilePath);
        if (loadedCarddes != null) {
            System.out.println("\u001b[38;5;183mДесериализация карты:\u001b[38;5;15m");
            System.out.println(loadedCarddes);
        }*/

        // 2 задание
        DatabaseServiceImpl dsi = new DatabaseServiceImpl();
        //dsi.createPokemonOwner(loadedCard.getPokemonOwner());
        //dsi.saveCardToDatabase(loadedCard);
        // выше все ок

        //System.out.println("\u001b[38;5;183mПоиск карты покемона по имени в базе данных.\u001b[38;5;15m");
        //System.out.println(dsi.getCardFromDatabase("Glastrier"));
        //System.out.println(dsi.getCardFromDatabase("Cloyster"));
        //System.out.println(dsi.getStudentFromDatabase("Kryukova Kristina Nikolaevna"));

        SpringApplication.run(PkmnApplication.class, args); // Обязательный метод

    }
}