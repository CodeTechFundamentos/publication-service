package com.nutrix.command.api;

import com.nutrix.command.application.services.RecipeCommandService;
import com.nutrix.command.domain.FavoriteRecipes;
import com.nutrix.command.domain.Recipe;
import com.nutrix.command.infra.IDietRecipesRepository;
import com.nutrix.command.infra.IFavoriteRecipesRepository;
import com.nutrix.query.application.services.RecipeQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
@Api(tags="Recipe", value = "Servicio Web RESTFul de Recipe")
public class RecipeCommandController {

    @Autowired
    private RecipeCommandService recipeService;
    @Autowired
    private RecipeQueryService recipeQueryService;
    @Autowired
    private IFavoriteRecipesRepository favoriteRecipesRepository;
    @Autowired
    private IDietRecipesRepository dietRecipesRepository;
    @Autowired
    private RestTemplate template;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un Recipe de un Nutritionist", notes ="Método que registra un Recipe" )
    @ApiResponses({
            @ApiResponse(code=201, message = "Recipe creado"),
            @ApiResponse(code=404, message = "Recipe no creado")
    })
    public ResponseEntity<Recipe> insertRecipe(@Valid @RequestBody Recipe recipe){
        try{
            Recipe recipeNew = recipeService.save(recipe);
            return ResponseEntity.status(HttpStatus.CREATED).body(recipeNew);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Actualización de datos de Recipe", notes = "Método que actualizar los datos de Recipe")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Recipe actualizados"),
            @ApiResponse(code=404, message = "REcipe no actualizado")
    })
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") Integer id, @Valid @RequestBody Recipe recipe){
        try{
            Optional<Recipe> recipeOld = recipeQueryService.getById(id);
            if(!recipeOld.isPresent())
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            else{
                recipe.setId(id);
                recipeService.save(recipe);
                return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Eliminación de Recipe por Id", notes = "Método que elimina los datos de un Recipe")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Recipe eliminados"),
            @ApiResponse(code=404, message = "Recipe no eliminados")
    })
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable("id") Integer id){
        try{
            Optional<Recipe> recipeDelete = recipeQueryService.getById(id);
            if(recipeDelete.isPresent()){
                recipeService.delete(id);
                return new ResponseEntity<Recipe>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/newFavorite", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adición de Recipe favorita a la lista de favoritos de un patient", notes = "Método que añade una Recipe favorita a la lista de favoritos de un patient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recipe añadida a la lista de favoritos del patient"),
            @ApiResponse(code = 404, message = "Recipe o patient no encontrado")
    })
    public ResponseEntity<FavoriteRecipes> addFavoriteRecipe(@Valid @RequestBody FavoriteRecipes favoriteRecipes){
        try {
            Optional<Recipe> foundRecipe = recipeQueryService.getById(favoriteRecipes.getRecipe().getId());
            if(!foundRecipe.isPresent())
                return new ResponseEntity<FavoriteRecipes>(HttpStatus.NOT_FOUND);

            FavoriteRecipes existRecipe = favoriteRecipesRepository.findByPatientAndRecipe(favoriteRecipes.getPatientId(), favoriteRecipes.getRecipe().getId());
            if(existRecipe!=null)
                return new ResponseEntity<FavoriteRecipes>(HttpStatus.NOT_FOUND);

            favoriteRecipesRepository.save(favoriteRecipes);
            return ResponseEntity.status(HttpStatus.CREATED).body(favoriteRecipes);
        }catch (Exception e){
            return new ResponseEntity<FavoriteRecipes>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{recipe_id}/{patient_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Recipe de la lista de favoritos de un patient", notes = "Método para eliminar un Recipe de la lista de favoritos de un patient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recipe eliminado"),
            @ApiResponse(code = 404, message = "Recipe no encontrado")
    })
    public ResponseEntity<FavoriteRecipes> deletePatientFavoriteRecipe(@PathVariable("recipe_id") Integer recipe_id,
                                                             @PathVariable("patient_id") Integer patient_id)
    {
        try{
            Optional<Recipe> foundRecipe = recipeQueryService.getById(recipe_id);
            if(!foundRecipe.isPresent())
                return new ResponseEntity<FavoriteRecipes>(HttpStatus.NOT_FOUND);

            FavoriteRecipes recipeToDelete = favoriteRecipesRepository.findByPatientAndRecipe(patient_id, foundRecipe.get().getId());
            if(recipeToDelete == null)
                return new ResponseEntity<FavoriteRecipes>(HttpStatus.NOT_FOUND);

            favoriteRecipesRepository.deleteById(recipeToDelete.getId());
            return new ResponseEntity<FavoriteRecipes>(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<FavoriteRecipes>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}