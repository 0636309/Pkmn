package ru.mirea.kryukovakn.pkmn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Scanner;

public class PkmnApplication {
    public static void main(String[] args) {

        //1 задание
        String filePath = "C:\\Users\\ltyut\\IdeaProjects\\Pkmn\\src\\main\\resources\\my_card.txt";
        CardImport cardImport = new CardImport(filePath);
        Card card = cardImport.loadCard();

        if (card != null) {
            System.out.println(card);
        }
        else {
            System.out.println("Данные не найдены.");
        }

        if (card.getEvolvesFrom() != null) {
            System.out.println("Эволюционирует из:");
            System.out.println(card.getEvolvesFrom());
        }

        //2 задание
        CardExport cardExport = new CardExport();
        cardExport.saveCard(card);

        String loadFilePath = "Pokemosha.crd";
        Card loadedCard = cardImport.deserializeCard(loadFilePath);
        if (loadedCard != null) {
            System.out.println("Загруженная карта:");
            System.out.println(loadedCard);
        }
    }

}
