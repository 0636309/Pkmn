package ru.mirea.pkmn.kryukovakn.web.jdbc;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.postgresql.util.PGobject;
import ru.mirea.pkmn.kryukovakn.models.*;
import ru.mirea.pkmn.kryukovakn.web.http.PkmnHttpClient;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseServiceImpl implements DatabaseService {

    private final Connection connection;
    private final Properties databaseProperties;

    public DatabaseServiceImpl() throws SQLException, IOException {

        databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));

        connection = DriverManager.getConnection(
                databaseProperties.getProperty("database.url"),
                databaseProperties.getProperty("database.user"),
                databaseProperties.getProperty("database.password")
        );
        System.out.println("Соединение с базой данных "+(connection.isValid(0) ? "установлено." : "не установлено."));
    }

    @Override
    public Card getCardFromDatabase(String cardName) throws IOException {
        Card card = null;
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));

            String query = "SELECT * FROM card WHERE name = ?";

            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password"));
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, cardName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        card = new Card();

                        card.setName(resultSet.getString("name"));
                        card.setHp(resultSet.getInt("hp"));

                        String evolvesUuidFromDB = resultSet.getString("evolves_from");;
                        Card evolvesCard;
                        if (evolvesUuidFromDB != null){
                            try{
                                UUID evolvesUUID = UUID.fromString(evolvesUuidFromDB);
                                evolvesCard = getEvolvesFrom(evolvesUUID);
                                card.setEvolvesFrom(evolvesCard);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }


                        card.setGameSet(resultSet.getString("game_set"));
                        card.setPokemonStage(PokemonStage.valueOf(resultSet.getString("stage")));
                        card.setRetreatCost(resultSet.getString("retreat_cost"));
                        card.setWeaknessType(EnergyType.valueOf(resultSet.getString("weakness_type")));

                        String resistanceTypeValue = resultSet.getString("resistance_type");
                        EnergyType resistanceType = null;
                        if (resistanceTypeValue != null && !resistanceTypeValue.isEmpty()) {
                            try {
                                resistanceType = EnergyType.valueOf(resistanceTypeValue);
                            } catch (IllegalArgumentException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        card.setResistanceType(resistanceType);

                        String attackSkillsJson = resultSet.getString("attack_skills");
                        Gson gson = new Gson();
                        List<AttackSkill> attackSkills = gson.fromJson(attackSkillsJson, new TypeToken<List<AttackSkill>>() {}.getType());
                        card.setSkills(attackSkills);

                        card.setPokemonType(EnergyType.valueOf(resultSet.getString("pokemon_type")));
                        card.setRegulationMark((resultSet.getString("regulation_mark")).charAt(0));
                        card.setNumber(resultSet.getString("card_number"));

                        String ownerUuidFromDB = resultSet.getString("pokemon_owner");
                        Student owner = null;
                        if(ownerUuidFromDB != null || !ownerUuidFromDB.isEmpty()){
                            owner = getPokemonOwner(UUID.fromString(ownerUuidFromDB));
                            card.setPokemonOwner(owner);
                        }

                        return card;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Ошибка получения карты из базы данных: " + e.getMessage());
            }
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }

        return card;
    }

    @Override
    public Student getStudentFromDatabase(String studentFullName) {
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));

            String query = "SELECT \"familyName\", \"firstName\", \"patronicName\", \"group\" FROM student WHERE \"familyName\" = ? AND \"firstName\" = ? AND \"patronicName\" = ?";

            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password"));
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                String[] studentFullNameParts = studentFullName.split(" ");
                preparedStatement.setString(1, studentFullNameParts[0]);
                preparedStatement.setString(2, studentFullNameParts[1]);
                preparedStatement.setString(3, studentFullNameParts[2]);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {

                        String familyName = resultSet.getString("familyName");
                        String firstName = resultSet.getString("firstName");
                        String patronicName = resultSet.getString("patronicName");
                        String group = resultSet.getString("group");

                        return new Student(familyName, firstName, patronicName, group);

                    } else {
                        System.out.println("Студент с именем '" + studentFullName + "' не найден.");
                        return null;
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveCardToDatabase(Card card) throws IOException {
        String queryUUID = "SELECT id FROM student WHERE \"familyName\" = ?";

        Properties databaseProperties = new Properties();
        try{
            databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));

            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password"));

                PreparedStatement preparedStatement = connection.prepareStatement(queryUUID)) {
                Student owner = card.getPokemonOwner();
                String family = owner.getFamilyName();
                preparedStatement.setString(1, family);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String uuid = resultSet.getString("id");
                        //System.out.println("Все ок.");

                        PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();
                        JsonNode cardNode = pkmnHttpClient.getPokemonCard(card.getName(), card.getNumber());

                        Set<String> attackSkillsText = cardNode.findValues("attacks")
                                .stream()
                                .flatMap(attack -> attack.findValues("text").stream())
                                .map(JsonNode::toPrettyString)
                                .collect(Collectors.toSet());

                        List<String> attackDescriptions = new ArrayList<>(attackSkillsText);

                        List<AttackSkill> skills = card.getSkills();
                        int size = Math.min(skills.size(), attackDescriptions.size());
                        for (int i = 0; i < size; i++) {
                            skills.get(i).setDescription(attackDescriptions.get(i));
                        }

                        String jsonAttackSkills = new Gson().toJson(skills);
                        PGobject AttackSkills = new PGobject();
                        AttackSkills.setType("json");
                        AttackSkills.setValue(jsonAttackSkills);

                        String query = "INSERT INTO card (id, name, hp, evolves_from, game_set, pokemon_owner, stage, retreat_cost, weakness_type, resistance_type, attack_skills, pokemon_type, regulation_mark, card_number) VALUES (gen_random_uuid(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(query)) {

                            insertStatement.setString(1, card.getName());
                            insertStatement.setInt(2, card.getHp());

                            UUID evolvesFromUUID = null;
                            String evolvesFromName = card.getEvolvesFrom() != null ? card.getEvolvesFrom().getName() : null;


                            if (evolvesFromName != null && !evolvesFromName.isEmpty()) {
                                try (PreparedStatement selectUUIDStatement = connection.prepareStatement(
                                        "SELECT id FROM card WHERE name = ?")) {
                                    selectUUIDStatement.setString(1, evolvesFromName);
                                    try (ResultSet resSet = selectUUIDStatement.executeQuery()) {
                                        if (resSet.next()) {
                                            evolvesFromUUID = UUID.fromString(resSet.getString("id"));
                                        } else {
                                            System.err.println("Карта эволюции '" + evolvesFromName + "' не найдена в базе данных.");
                                        }
                                    }
                                } catch (SQLException e) {
                                    System.err.println("Ошибка получения UUID для evolves_from: " + e.getMessage());

                                }
                            }
                            insertStatement.setObject(3, evolvesFromUUID);
                            insertStatement.setString(4, card.getGameSet());
                            insertStatement.setObject(5, UUID.fromString(uuid));
                            insertStatement.setString(6, card.getPokemonStage().name());
                            insertStatement.setString(7, card.getRetreatCost());
                            insertStatement.setString(8, card.getWeaknessType().name());
                            String resistanceTypeValue;
                            EnergyType resistanceType = card.getResistanceType();
                            if (resistanceType == null || resistanceType.name().equals("-")) {
                                resistanceTypeValue = null;
                            } else {
                                resistanceTypeValue = resistanceType.name();
                            }
                            insertStatement.setString(9, resistanceTypeValue);
                            insertStatement.setObject(10, AttackSkills);
                            insertStatement.setString(11, card.getPokemonType().name());
                            insertStatement.setString(12, String.valueOf(card.getRegulationMark()));
                            insertStatement.setString(13, card.getNumber());

                            int rowsAffected = insertStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Данные карты успешно добавлены.");
                            } else {
                                System.out.println("Ошибка добавления данных карты.");
                            }
                        }
                    } else {
                        System.out.println("Студент с таким именем не найден.");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла свойств: " + e.getMessage());
        }
    }


    @Override
    public void createPokemonOwner(Student owner) {

        String query = "INSERT INTO student (id ,\"familyName\", \"firstName\", \"patronicName\",\"group\") VALUES (gen_random_uuid(), ?, ?, ?, ?)";

        Properties databaseProperties = new Properties();
        try {

            databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));

            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password"));
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, owner.getFamilyName());
                preparedStatement.setString(2, owner.getFirstName());
                preparedStatement.setString(3, owner.getSurName());
                preparedStatement.setString(4, owner.getGroup());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Студент успешно добавлен.");
                } else {
                    System.out.println("Ошибка при добавлении студента.");
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка загрузки файла: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Card getEvolvesFrom(UUID evolvesUUID) throws IOException {
        String query = "SELECT * FROM card WHERE id = ?";
        Properties databaseProperties = new Properties();

        try{
            databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));
            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password"));
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setObject(1, evolvesUUID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Card card = new Card();

                        card.setName(resultSet.getString("name"));
                        card.setHp(resultSet.getInt("hp"));

                        String nextEvolvesUuid = resultSet.getString("evolves_from");
                        if (nextEvolvesUuid != null && !nextEvolvesUuid.isEmpty()) {
                            try {
                                UUID nextEvolvesUUID = UUID.fromString(nextEvolvesUuid);
                                card.setEvolvesFrom(getEvolvesFrom(nextEvolvesUUID));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        card.setGameSet(resultSet.getString("game_set"));
                        card.setPokemonStage(PokemonStage.valueOf(resultSet.getString("stage")));
                        card.setRetreatCost(resultSet.getString("retreat_cost"));
                        card.setWeaknessType(EnergyType.valueOf(resultSet.getString("weakness_type")));

                        String resistanceTypeValue = resultSet.getString("resistance_type");
                        EnergyType resistanceType = null;
                        if (resistanceTypeValue != null && !resistanceTypeValue.isEmpty()) {
                            try {
                                resistanceType = EnergyType.valueOf(resistanceTypeValue);
                            } catch (IllegalArgumentException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        card.setResistanceType(resistanceType);

                        String attackSkillsJson = resultSet.getString("attack_skills");
                        Gson gson = new Gson();
                        List<AttackSkill> attackSkills = gson.fromJson(attackSkillsJson, new TypeToken<List<AttackSkill>>() {}.getType());
                        card.setSkills(attackSkills);

                        card.setPokemonType(EnergyType.valueOf(resultSet.getString("pokemon_type")));
                        card.setRegulationMark((resultSet.getString("regulation_mark")).charAt(0));
                        card.setNumber(resultSet.getString("card_number"));

                        return card;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Ошибка получения карты из базы данных: " + e.getMessage());
                return null;
            }
            return null;
        } catch (IOException e) {
            System.err.println("Ошибка загрузки файла: " + e.getMessage());
        }
        return null;
    }

    private Student getPokemonOwner(UUID ownerUUID){
        String query = "SELECT * FROM student WHERE id = ?";
        Properties databaseProperties = new Properties();

        try{
            databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));
            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password"));
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setObject(1, ownerUUID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Student student = new Student();

                        student.setFamilyName(resultSet.getString("familyName"));
                        student.setFirstName(resultSet.getString("firstName"));
                        student.setSurName(resultSet.getString("patronicName"));
                        student.setGroup(resultSet.getString("group"));

                        return student;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Ошибка получения карты из базы данных: " + e.getMessage());
                return null;
            }
            return null;
        } catch (IOException e) {
            System.err.println("Ошибка загрузки файла: " + e.getMessage());
        }
        return null;
    }
}
