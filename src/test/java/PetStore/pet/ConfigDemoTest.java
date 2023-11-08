package PetStore.pet;

import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

//@Disabled
public class ConfigDemoTest {

    @Test
    public void test1(){

        System.out.println("ConfigReader.getProperty(\"serenity.project.name\") = " + ConfigReader.getProperty("serenity.project.name"));



    }

}
