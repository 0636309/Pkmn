package ru.mirea.pkmn;

public enum PokemonStage {
    BASIC,
    STAGE1,
    STAGE2,
    VSTAR,
    VMAX;

    PokemonStage() {

    }

    @Override
    public String toString() {
        return name();
    }
}
