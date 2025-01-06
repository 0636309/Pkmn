package ru.mirea.pkmn.kryukovakn.web.jdbc;

import ru.mirea.pkmn.kryukovakn.models.Card;
import ru.mirea.pkmn.kryukovakn.models.Student;

import java.io.IOException;

public interface DatabaseService {

    Card getCardFromDatabase(String cardName) throws IOException;
    Student getStudentFromDatabase(String studentFullName);
    void saveCardToDatabase(Card card) throws IOException;
    void createPokemonOwner(Student owner);
}
