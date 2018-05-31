package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ExtendedRoomTariff implements Serializable {

    private int id = -1;

    private double cost;

    private boolean visibility;

    private Map<LanguageEnum, String> names;

    private Map<LanguageEnum, String> descriptions;

    public ExtendedRoomTariff() {
        names = new EnumMap<>(LanguageEnum.class);
        descriptions = new EnumMap<>(LanguageEnum.class);
    }

    public ExtendedRoomTariff(int id, double cost, boolean visibility, Map<LanguageEnum, String> names, Map<LanguageEnum, String> descriptions) {
        this.id = id;
        this.cost = cost;
        this.visibility = visibility;
        this.names = names;
        this.descriptions = descriptions;
    }

    public ExtendedRoomTariff(double cost, boolean visibility, Map<LanguageEnum, String> names, Map<LanguageEnum, String> descriptions) {
        this.cost = cost;
        this.visibility = visibility;
        this.names = names;
        this.descriptions = descriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Map<LanguageEnum, String> getNames() {
        return names;
    }

    public void setNames(Map<LanguageEnum, String> names) {
        this.names = names;
    }

    public Map<LanguageEnum, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<LanguageEnum, String> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedRoomTariff that = (ExtendedRoomTariff) o;
        return id == that.id &&
                Double.compare(that.cost, cost) == 0 &&
                visibility == that.visibility &&
                Objects.equals(names, that.names) &&
                Objects.equals(descriptions, that.descriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, visibility, names, descriptions);
    }

    @Override
    public String toString() {
        return "ExtendedRoomTariff{" +
                "id=" + id +
                ", cost=" + cost +
                ", visibility=" + visibility +
                ", names=" + names +
                ", descriptions=" + descriptions +
                '}';
    }

}