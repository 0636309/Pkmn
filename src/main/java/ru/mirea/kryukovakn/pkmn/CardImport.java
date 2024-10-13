package ru.mirea.kryukovakn.pkmn;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardImport {
    private String filePath;

    public CardImport(String filePath) {
        this.filePath = filePath;
    }

    public Card loadCard() {
        Card card = new Card();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            card.setPokemonStage(PokemonStage.valueOf(br.readLine().toUpperCase()));
            card.setName(br.readLine());
            card.setHp(Integer.parseInt(br.readLine().trim())); // по-разному, у кого-то могут быть пробелы, у кого-то нет
            card.setEnergyType(EnergyType.valueOf(br.readLine().toUpperCase()));
            String evolvesFrom = br.readLine();
            if (!evolvesFrom.equals("-")) {
                card.setEvolvesFrom(loadEvolvesFrom(evolvesFrom));
            }
            String[] abilitiesData = br.readLine().split(",");
            List<AttackSkill> skills = new ArrayList<>();
            for (String ability : abilitiesData) {
                String[] abilityParts = ability.split("/");
                skills.add(new AttackSkill(abilityParts[1].trim(), "", abilityParts[0].trim(), Integer.parseInt(abilityParts[2].trim()))); // Удаляем пробелы
            }
            card.setSkills(skills);

            card.setWeaknessType(EnergyType.valueOf(br.readLine().toUpperCase()));
            card.setResistanceType(br.readLine().trim().equals("-") ? null : EnergyType.valueOf(br.readLine().toUpperCase().trim())); // Удаляем пробелы
            card.setRetreatCost(br.readLine().trim());
            card.setGameSet(br.readLine().trim());
            card.setRegulationMark(br.readLine().trim().charAt(0));
            card.setPokemonOwner(parseOwner(br.readLine().trim()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return card;
    }


    private Card loadEvolvesFrom(String evolvesFrom) {
        Card evolvesFromCard = new Card();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ltyut\\IdeaProjects\\Pkmn\\src\\main\\resources\\" + evolvesFrom))) {
            evolvesFromCard = new Card();
            evolvesFromCard.setPokemonStage(PokemonStage.valueOf(br.readLine().toUpperCase()));
            evolvesFromCard.setName(br.readLine());
            evolvesFromCard.setHp(Integer.parseInt(br.readLine().trim()));
            evolvesFromCard.setEnergyType(EnergyType.valueOf(br.readLine().toUpperCase()));
            String[] abilitiesData = br.readLine().split(",");
            List<AttackSkill> skills = new ArrayList<>();
            for (String ability : abilitiesData) {
                String[] abilityParts = ability.split("/");
                skills.add(new AttackSkill(abilityParts[1].trim(), "", abilityParts[0].trim(), Integer.parseInt(abilityParts[2].trim()))); // Удаляем пробелы
            }
            evolvesFromCard.setSkills(skills);
            evolvesFromCard.setWeaknessType(EnergyType.valueOf(br.readLine().toUpperCase()));
            evolvesFromCard.setResistanceType(br.readLine().trim().equals("-") ? null : EnergyType.valueOf(br.readLine().toUpperCase().trim())); // Удаляем пробелы
            evolvesFromCard.setRetreatCost(br.readLine().trim());
            evolvesFromCard.setGameSet(br.readLine().trim());
            evolvesFromCard.setRegulationMark(br.readLine().trim().charAt(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return evolvesFromCard;
    }

    private Student parseOwner(String ownerData) {
        String[] parts = ownerData.split("/");
        return new Student(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
    }

    public Card deserializeCard(String filePath) {
        Card card = null;
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            card = (Card) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return card;
    }

}

