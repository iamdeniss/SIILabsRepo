package khpi.artificialintelligence.lab1.entity;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Entity {
    private String name;
    private Set<String> entityProperties;

    public Entity(String name) {
        this.name = name;
        entityProperties = new LinkedHashSet<>();
    }

    public void addProperties(String[] newProperties) {
        entityProperties.addAll(Arrays.asList(newProperties));
    }

    public void addProperty(String property) {
        entityProperties.add(property);
    }

    public Set<String> getEntityProperties() {
        return this.entityProperties;
    }

    public void removeProperty(String property) {
        entityProperties.removeIf(entityProperty -> entityProperty.equals(property));
    }

    public String getName() {
        return name;
    }
}
