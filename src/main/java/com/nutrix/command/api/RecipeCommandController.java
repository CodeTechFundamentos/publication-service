package com.nutrix.command.api;

import com.netflix.discovery.converters.Auto;
import com.nutrix.command.application.services.RecipeCommandService;
import com.nutrix.command.domain.Recipe;
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
}