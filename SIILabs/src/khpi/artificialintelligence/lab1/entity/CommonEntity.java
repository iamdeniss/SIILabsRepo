package khpi.artificialintelligence.lab1.entity;

import java.util.LinkedHashSet;
import java.util.Set;

public class CommonEntity {
    private Set<String> commonEntityProperties;

    public CommonEntity() {
        commonEntityProperties = new LinkedHashSet<>();
    }

    public void addCommonProperties(String commonProperty) {
        commonEntityProperties.add(commonProperty);
    }

    public Set<String> getCommonProperties() {
        return this.commonEntityProperties;
    }
}
