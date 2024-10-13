package ru.mirea.kryukovakn.pkmn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CardExport {
    public void saveCard(Card card) {
        String fileName = card.getName() + ".crd";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(card);
            System.out.println("Карта успешно сохранена в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении карты.");
        }
    }
}
