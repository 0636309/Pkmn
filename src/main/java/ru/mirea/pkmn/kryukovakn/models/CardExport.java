package ru.mirea.pkmn.kryukovakn.models;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CardExport {
    public static final long serialVersionUID = 1L;
    public void saveCard(Card card) {

        String fileName = card.getName() + ".crd";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(card);
            System.out.println("\u001b[38;5;183mКарта успешно сохранена в файл: " + fileName + "\u001b[38;5;183m\n");
            out.flush();
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Ошибка при сериализации.");
            e.printStackTrace();
        }
























































    }
}
