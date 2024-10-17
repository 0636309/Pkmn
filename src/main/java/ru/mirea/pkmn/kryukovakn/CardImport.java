package ru.mirea.pkmn.kryukovakn;

import ru.mirea.pkmn.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardImport {
    private String filePath;
    public static final long serialVersionUID = 1L;

    public CardImport(String filePath) {
        this.filePath = filePath;
    }

    public Card loadCard() {
        Card card = new Card();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            card.setPokemonStage(PokemonStage.valueOf(br.readLine().toUpperCase()));
            card.setName(br.readLine());
            card.setHp(Integer.parseInt(br.readLine()));

            card.setPokemonType(EnergyType.valueOf(br.readLine().trim().toUpperCase()));
            String evolvesFromFile = br.readLine().trim();
            if (!evolvesFromFile.equals("-")) {
                String evolveFilePath = "src\\main\\resources\\" + evolvesFromFile;
                card.setEvolvesFrom(loadEvolvesFrom(evolveFilePath));
            }

            String[] abilitiesData = br.readLine().split(",");
            List<AttackSkill> skills = new ArrayList<>();
            for (String ability : abilitiesData) {
                String[] abilityParts = ability.split("/");
                skills.add(new AttackSkill(abilityParts[1].trim(), "", abilityParts[0].trim(), Integer.parseInt(abilityParts[2].trim())));
            }
            card.setSkills(skills);
            String weaknessLine = br.readLine().trim();
            card.setWeaknessType(weaknessLine.equals("NO") || weaknessLine.equals("-") || weaknessLine.isEmpty() ? null : EnergyType.valueOf(weaknessLine.toUpperCase()));
            String resistanceLine = br.readLine().trim();
            card.setResistanceType(resistanceLine.equals("NO") || resistanceLine.equals("-") || resistanceLine.isEmpty() ? null : EnergyType.valueOf(resistanceLine.toUpperCase()));
            card.setRetreatCost(br.readLine());
            card.setGameSet(br.readLine());
            card.setRegulationMark(br.readLine().charAt(0));
            card.setPokemonOwner(parseOwner(br.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return card;
    }


    private Card loadEvolvesFrom(String evolvesFromPath) {
        Card evolvesFromCard = new Card();
        try (BufferedReader br = new BufferedReader(new FileReader(evolvesFromPath))) {
            evolvesFromCard.setPokemonStage(PokemonStage.valueOf(br.readLine().toUpperCase()));
            evolvesFromCard.setName(br.readLine());
            evolvesFromCard.setHp(Integer.parseInt(br.readLine()));
            evolvesFromCard.setPokemonType(EnergyType.valueOf(br.readLine().toUpperCase().trim()));
            String evolvesFrom_ = br.readLine();
            if (!evolvesFrom_.equals("-")) {
                evolvesFromCard.setEvolvesFrom(loadEvolvesFrom(evolvesFrom_));
            }
            String[] abilitiesData = br.readLine().split(",");
            List<AttackSkill> skills = new ArrayList<>();
            for (String ability : abilitiesData) {
                String[] abilityParts = ability.split("/");
                skills.add(new AttackSkill(abilityParts[1].trim(), "", abilityParts[0].trim(), Integer.parseInt(abilityParts[2].trim())));
            }
            evolvesFromCard.setSkills(skills);
            String weaknessLine = br.readLine().trim();
            evolvesFromCard.setWeaknessType(weaknessLine.isEmpty() || weaknessLine.equals("-") ? null : EnergyType.valueOf(br.readLine().toUpperCase()));
            String resistanceLine = br.readLine().trim();
            evolvesFromCard.setResistanceType(resistanceLine.equals("NO") || resistanceLine.equals("-") || resistanceLine.isEmpty() ? null : EnergyType.valueOf(resistanceLine.toUpperCase()));
            evolvesFromCard.setRetreatCost(br.readLine());
            evolvesFromCard.setGameSet(br.readLine());
            evolvesFromCard.setRegulationMark(br.readLine().trim().charAt(0));
            //evolvesFromCard.setPokemonOwner(parseOwner(br.readLine()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return evolvesFromCard;
    }



    private Student parseOwner(String ownerParts) {
        String[] parts = ownerParts.split("/");
        return new Student(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
    }

    public Card deserializeCard(String filePath) {
        Card card = null;
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            card = (Card) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при десериализации.");
            e.printStackTrace();
        }
        return card;

    }

}

