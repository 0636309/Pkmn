package ru.mirea.kryukovakn.pkmn;


import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {

    private PokemonStage pokemonStage;
    private String name;
    private int hp;
    private EnergyType energyType;
    private Card evolvesFrom;
    private List<AttackSkill> skills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark;
    private Student pokemonOwner;

    public Card(PokemonStage pokemonStage, String name, int hp, EnergyType energyType, Card evolvesFrom, List<AttackSkill> skills, EnergyType weaknessType, EnergyType resistanceType, String retreatCost, String gameSet, char regulationMark, Student pokemonOwner) {
        this.pokemonStage = pokemonStage;
        this.name = name;
        this.hp = hp;
        this.energyType = energyType;
        this.evolvesFrom = evolvesFrom;
        this.resistanceType = resistanceType;
        this.weaknessType = weaknessType;
        this.gameSet = gameSet;
        this.skills = skills;
        this.retreatCost = retreatCost;
        this.regulationMark = regulationMark;
        this.pokemonOwner = pokemonOwner;
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

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
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

    @Override
    public String toString() {
        return "Карта покемона" + "\n" +
                "Стадия: " + pokemonStage + "\n" +
                "Имя покемона: " + name +  "\n" +
                "ХП: " + hp + "\n" +
                "Тип покемона: " + energyType + "\n" +
                "Из какого покемона эволюционирует: " + (evolvesFrom != null ? evolvesFrom.getName() : "None") + "\n" +
                "Способности атак: " + skills + "\n" +
                "Тип слабости: " + (weaknessType != null ? weaknessType : "None") + "\n" +
                "Тип сопротивления: " + (resistanceType != null ? resistanceType : "None") + "\n" +
                "Цена побега: " + retreatCost + "\n" +
                "Название сета: " + gameSet + "\n" +
                "Отметка легальности: " + regulationMark + "\n" +
                "Владелец карты: " + pokemonOwner + "\n";
    }

}
