package ru.mirea.pkmn.kryukovakn.models;


import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {

    private PokemonStage pokemonStage;
    private String name;
    private int hp;
    private EnergyType pokemonType;
    private Card evolvesFrom;
    private List<AttackSkill> skills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark;
    private Student pokemonOwner;
    public static final long serialVersionUID = 1L;
    public String number;

    public Card(PokemonStage pokemonStage, String name, int hp, EnergyType pokemonType, Card evolvesFrom, List<AttackSkill> skills, EnergyType weaknessType, EnergyType resistanceType, String retreatCost, String gameSet, String number, char regulationMark, Student pokemonOwner) {
        this.pokemonStage = pokemonStage;
        this.name = name;
        this.hp = hp;
        this.pokemonType = pokemonType;
        this.evolvesFrom = evolvesFrom;
        this.resistanceType = resistanceType;
        this.weaknessType = weaknessType;
        this.gameSet = gameSet;
        this.skills = skills;
        this.retreatCost = retreatCost;
        this.regulationMark = regulationMark;
        this.pokemonOwner = pokemonOwner;
        this.number = number;
    }

    public Card() {

    }

    public PokemonStage getPokemonStage() {
        return pokemonStage;
    }

    public void setPokemonStage(PokemonStage pokemonStage) {
        this.pokemonStage = pokemonStage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public EnergyType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(EnergyType pokemonType) {
        this.pokemonType = pokemonType;
    }

    public Card getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(Card evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public List<AttackSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<AttackSkill> skills) {
        this.skills = skills;
    }

    public EnergyType getWeaknessType() {
        return weaknessType;
    }

    public void setWeaknessType(EnergyType weaknessType) {
        this.weaknessType = weaknessType;
    }

    public EnergyType getResistanceType() {
        return resistanceType;
    }

    public void setResistanceType(EnergyType resistanceType) {
        this.resistanceType = resistanceType;
    }

    public String getRetreatCost() {
        return retreatCost;
    }

    public void setRetreatCost(String retreatCost) {
        this.retreatCost = retreatCost;
    }

    public String getGameSet() {
        return gameSet;
    }

    public void setGameSet(String gameSet) {
        this.gameSet = gameSet;
    }

    public char getRegulationMark() {
        return regulationMark;
    }

    public void setRegulationMark(char regulationMark) {
        this.regulationMark = regulationMark;
    }

    public Student getPokemonOwner() {
        return pokemonOwner;
    }

    public void setPokemonOwner(Student pokemonOwner) {
        this.pokemonOwner = pokemonOwner;
    }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        result.append("\u001b[38;5;183mКарта покемона\u001b[38;5;15m").append("\n")
                .append("Стадия: ").append(pokemonStage).append("\n")
                .append("Имя покемона: ").append(name).append("\n")
                .append("ХП: ").append(hp).append("\n")
                .append("Тип покемона: ").append(pokemonType).append("\n")
                .append("Способности атак: ").append(skills).append("\n")
                .append("Тип слабости: ").append(weaknessType != null ? weaknessType : "-").append("\n")
                .append("Тип сопротивления: ").append(resistanceType != null ? resistanceType : "-").append("\n")
                .append("Цена побега: ").append(retreatCost).append("\n")
                .append("Название сета: ").append(gameSet).append("\n")
                .append("Номер карты в сете: ").append(number).append("\n")
                .append("Отметка легальности: ").append(regulationMark).append("\n")
                .append(pokemonOwner != null ? "Владелец карты: " + pokemonOwner + "\n" : "");

        if (evolvesFrom != null) {
            result.append("\n")
                    .append("\033[4;37mПредшественник:\033[0m").append("\n")
                    .append(evolvesFrom.toString());
        } else {
            result.append("Не имеет предка.\n");
        }

        return result.toString();

    }



}
