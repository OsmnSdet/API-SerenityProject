package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;

public class PetStoreUtil {

    public static Faker faker = new Faker();
    public static ObjectMapper objectMapper = new ObjectMapper();


    // Generate random pet information with java faker
    public static ObjectNode GenerateRandomPet() throws Exception {
        ObjectNode pet = objectMapper.createObjectNode();
        pet.put("category", createCategory());
        pet.put("name", "Scout");
        pet.putArray("photoUrls").add(faker.internet().image());
        pet.putArray("tags").add(createTag());
        pet.put("status", "available");

        return pet;
    }

    private static ObjectNode createCategory() {
        ObjectNode category = objectMapper.createObjectNode();
        category.put("id", faker.number().numberBetween(1, 100));
        category.put("name", "Pets");
        return category;
    }

    private static ObjectNode createTag() {
        ObjectNode tag = objectMapper.createObjectNode();
        tag.put("id", faker.number().numberBetween(1, 100));
        tag.put("name", "pet-" + faker.animal().name().toLowerCase());
        return tag;
    }
}

