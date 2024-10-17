package ru.mirea.pkmn.kryukovakn;
import ru.mirea.pkmn.*;


public class PkmnApplication {
    public static final long serialVersionUID = 1L;
    public static void main(String[] args) {

        // вывод my_card.txt и сериализация

        String filePath = "src\\main\\resources\\my_card.txt";
        CardImport cardImport_ = new CardImport(filePath);
        Card card = cardImport_.loadCard();

        if (card != null) {
            System.out.println("\u001b[38;5;183mВывод из текстового файла:\u001b[38;5;15m");
            System.out.println(card);
        }
        else {
            System.out.println("Данные не найдены.");
        }

        if (card.getEvolvesFrom() != null) {
            System.out.println("Эволюционирует из:");
            System.out.println(card.getEvolvesFrom());

        }

        CardExport cardExport = new CardExport();
        cardExport.saveCard(card);

        if (card.getEvolvesFrom() != null) {
            CardExport cardEvolvesExport = new CardExport();
            cardEvolvesExport.saveCard(card.getEvolvesFrom());

        }

        String loadFilePathCrd = "Glastrier.crd";
        CardImport myCardImport = new CardImport(loadFilePathCrd);
        Card myLoadedCard = myCardImport.deserializeCard(loadFilePathCrd);
        if (myLoadedCard != null) {
            System.out.println("\u001b[38;5;183mДесериализация моей карты (проверка корректности):\u001b[38;5;15m");
            System.out.println(myLoadedCard);
        }


        // десериализация файла одногруппника

        String loadFilePath = "Lera.crd";
        CardImport cardImport = new CardImport(loadFilePath);
        Card loadedCard = cardImport.deserializeCard(loadFilePath);
        if (loadedCard != null) {
            System.out.println("\u001b[38;5;183mДесериализация карты одногруппника:\u001b[38;5;15m");
            System.out.println(loadedCard);
        }
    }
}
