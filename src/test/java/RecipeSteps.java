import com.nutrix.command.domain.Recipe;
import io.cucumber.java.en.And;


import lombok.extern.log4j.Log4j2;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.log4j.Log4j2;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RecipeSteps {

    @LocalServerPort
    private int port;
    private RestTemplate restTemplate = new RestTemplate();
    private String postUrl="https://nutrix-publication-service.mybluemix.net/";
    private String error= "Ingrese un codigo valido";

    @Given("I register a new recipe as a nutritionist")
    public void i_register_a_new_recipe_as_a_nutritionist() {
        String url=postUrl + "recipe/";
        List<Recipe> all=restTemplate.getForObject(url, List.class);
        log.info(all);
        assertTrue(!all.isEmpty());
    }
    @Given("I sending diet form to be created with name {string} , description {string}, ingredients {string} and preparation {string}")
    public void i_sending_diet_form_to_be_created_with_name_description_ingredients_and_preparation(String name, String description, String ingredients, String preparation) {
        Date date= new Date();
        Recipe recipe = new Recipe(1,name,description,preparation,ingredients,1L,date,date,1);
        String url=postUrl + "/recipe/";
        Recipe newRecipe =restTemplate.postForObject(url,recipe,Recipe.class);

        log.info(newRecipe);
        assertNotNull(newRecipe);
    }
    @Then("I should be able to see my newly created recipe")
    public void i_should_be_able_to_see_my_newly_created_recipe() {
        String url=postUrl+"/recipe" +"/1";
        Recipe recipe=restTemplate.getForObject(url,Recipe.class);
        log.info(recipe);
        assertNotNull(recipe);
    }
}
