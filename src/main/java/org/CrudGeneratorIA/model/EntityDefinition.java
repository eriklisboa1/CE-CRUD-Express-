package org.CrudGeneratorIA.model;


import java.util.List;

public class EntityDefinition {
    private String entityName;
    private List<String[]> attributes;

    public EntityDefinition(String entityName, List<String[]> attributes) {
        this.entityName = entityName;
        this.attributes = attributes;
    }

    public String getEntityName() {
        return entityName;
    }

    public List<String[]> getAttributes() {
        return attributes;
    }
}
