package ru.mirea.pkmn.kryukovakn.web.jdbc;

import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.Student;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DatabaseService {

    Card getCardFromDatabase(String cardName) throws IOException;
    Student getStudentFromDatabase(String studentFullName);
    void saveCardToDatabase(Card card) throws IOException;
    void createPokemonOwner(Student owner);
}
