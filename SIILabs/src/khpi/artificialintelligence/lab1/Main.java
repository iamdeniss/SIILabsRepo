package khpi.artificialintelligence.lab1;

import khpi.artificialintelligence.lab1.entity.CommonEntity;
import khpi.artificialintelligence.lab1.entity.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    private static final String FIRST_ENTITY_STRING_NUMBER = "first";
    private static final String SECOND_ENTITY_STRING_NUMBER = "second";

    public static void main(String[] args) {
        CommonEntity commonEntity = new CommonEntity();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            Entity entity1 = entityCreation(bufferedReader, FIRST_ENTITY_STRING_NUMBER);
            Entity entity2 = entityCreation(bufferedReader, SECOND_ENTITY_STRING_NUMBER);

            addPropertiesForEntity(bufferedReader, entity1);
            addPropertiesForEntity(bufferedReader, entity2);

            findCommonPropertiesForEntities(entity1, entity2, commonEntity);

            Scanner sc = new Scanner(System.in);
            String[] unknownEntityPropertiesArray;
            System.out.print(System.lineSeparator() + "Enter properties for unknown entity: ");
            String unknownEntityProperties;
            while(!(unknownEntityProperties = sc.next()).equals("quit")) {
                unknownEntityPropertiesArray = unknownEntityProperties.split(",");
                int countOfFirstEntityProperties = getCountOfPropertiesOfUnknownEntityWhichContainsInThisEntity(unknownEntityPropertiesArray, entity1, commonEntity);
                int countOfSecondEntityProperties = getCountOfPropertiesOfUnknownEntityWhichContainsInThisEntity(unknownEntityPropertiesArray, entity2, commonEntity);

                if(countOfFirstEntityProperties > countOfSecondEntityProperties) {
                    findTrueEntity(entity1, entity2, unknownEntityPropertiesArray, commonEntity);
                } else if(countOfFirstEntityProperties < countOfSecondEntityProperties) {
                    findTrueEntity(entity2, entity1, unknownEntityPropertiesArray, commonEntity);
                } else {
                    findTrueEntity(entity1, entity2, unknownEntityPropertiesArray, commonEntity);
                }
                findCommonPropertiesForEntities(entity1, entity2, commonEntity);
                System.out.println(entity1.getName() + " properties: " + entity1.getEntityProperties().toString()
                        .concat(commonEntity.getCommonProperties().toString()).replace("[", "")
                        .replaceFirst("]", ", ").replace("]", ""));
                System.out.println(entity2.getName() + " properties: " + entity2.getEntityProperties().toString()
                        .concat(commonEntity.getCommonProperties().toString()).replace("[", "")
                        .replaceFirst("]", ", ").replace("]", ""));

                System.out.print(System.lineSeparator() + "Enter properties for unknown entity: ");
            }
        } catch (IOException e) {
            System.out.println("Reading failed...");
        }
    }

    private static void findTrueEntity(Entity entity1, Entity entity2, String[] unknownEntityProperties, CommonEntity commonEntity) {
        char answer;
        Scanner sc = new Scanner(System.in);
        System.out.print("Is it " + entity1.getName() + "? (y/n) ");
        answer = sc.next().charAt(0);
        if(answer == 'y') {
            for(int i = 0; i < unknownEntityProperties.length; i++) {
                if(!commonEntity.getCommonProperties().contains(unknownEntityProperties[i])) {
                    entity1.addProperty(unknownEntityProperties[i]);
                }
            }
        }
        if(answer == 'n') {
            for(int i = 0; i < unknownEntityProperties.length; i++) {
                if(!commonEntity.getCommonProperties().contains(unknownEntityProperties[i])) {
                    entity2.addProperty(unknownEntityProperties[i]);
                }
            }
        }
    }

    private static Entity entityCreation(BufferedReader bufferedReader, String entityNumber) throws IOException {
        System.out.print("Enter name for the " + entityNumber + " entity: ");
        return new Entity(bufferedReader.readLine());
    }

    private static void addPropertiesForEntity(BufferedReader bufferedReader, Entity entity) throws IOException {
        System.out.print("Enter properties for " + entity.getName() + ": ");
        String[] properties = bufferedReader.readLine().split(",");
        entity.addProperties(properties);
    }

    private static void findCommonPropertiesForEntities(Entity entity1, Entity entity2, CommonEntity commonEntity) {
        Iterator<String> it = entity1.getEntityProperties().iterator();
        while(it.hasNext()) {
            String property = it.next();
            if(entity2.getEntityProperties().contains(property)) {
                commonEntity.addCommonProperties(property);
                it.remove();
                entity2.removeProperty(property);
            }
        }
    }

    private static int getCountOfPropertiesOfUnknownEntityWhichContainsInThisEntity(String[] unknownEntityProperties,
                                                                                Entity entity, CommonEntity commonEntity) {
        int countOfEntityProperties = 0;
        for(int i = 0; i < unknownEntityProperties.length; i++) {
            if(entity.getEntityProperties().contains(unknownEntityProperties[i])) {
                countOfEntityProperties++;
            } else {
                if(commonEntity.getCommonProperties().contains(unknownEntityProperties[i])) {
                    countOfEntityProperties++;
                }
            }
        }
        return countOfEntityProperties;
    }
}
