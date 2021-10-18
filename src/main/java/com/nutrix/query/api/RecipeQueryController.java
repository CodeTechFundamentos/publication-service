package com.nutrix.query.api;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
@Api(tags="Recipe", value = "Servicio Web RESTFul de Recipe")
public class RecipeQueryController {

    @Autowired
    private RecipeQueryService recipeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Recipe", notes="Método para listar todos los recipes")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrados"),
            @ApiResponse(code=404, message = "recipe no encontrados")
    })
    public ResponseEntity<List<Recipe>> findAllRecipe(){
        try{
            List<Recipe> recipe = recipeService.getAll();
            if(recipe.size()>0)
                return new ResponseEntity<List<Recipe>>(recipe, HttpStatus.OK);
            else
                return new ResponseEntity<List<Recipe>>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipe por Id", notes="Método para listar un recipe por Id")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrado"),
            @ApiResponse(code=404, message = "recipe no encontrado")
    })
    public ResponseEntity<Recipe>findRecipeById(@PathVariable("id") Integer id){
        try{
            Optional<Recipe> recipe= recipeService.getById(id);
            if(!recipe.isPresent())
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Recipe>(recipe.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //pending request
//    @GetMapping(value="/getRecipesByList", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Buscar Recipe por una lista de ids", notes="Método para listar un recipe por una lista de ids")
//    @ApiResponses({
//            @ApiResponse(code=201, message = "recipes encontrados"),
//            @ApiResponse(code=404, message = "recipes no encontrados")
//    })
//    public ResponseEntity<List<Recipe>>getRecipesByList(@Valid @RequestBody List<Integer> recipesList){
//        try{
//            for(Integer i: recipesList)
//            {
//
//            }
//            Optional<Recipe> recipe= recipeService.getById(id);
//            if(!recipe.isPresent())
//                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
//            else
//                return new ResponseEntity<Recipe>(recipe.get(),HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping(value = "/searchRecipeByNutritionistId/{nutritionist_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar recipe por nutritionist id", notes = "Método para encontrar recipe por nutritionist id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recipe encontrados"),
            @ApiResponse(code = 404, message = "Recipe no encontrados")
    })
    public ResponseEntity<List<Recipe>> findByNutritionist(@PathVariable("nutritionist_id") Integer nutritionist_id)
    {
        try{
            List<Recipe> recipes = recipeService.findAllByNutritionist(nutritionist_id);
            if(recipes.size()>0)
                return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
            return new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipe por name", notes = "Método para encontrar un Recipe por su respectivo name")
    @ApiResponses({
            @ApiResponse(code=201, message = "Recipe encontrado"),
            @ApiResponse(code=404, message = "Recipe no encontrado")
    })
    public ResponseEntity<Recipe> findByName(@PathVariable("name") String name){
        try{
            Recipe recipe = recipeService.findByName(name);
            if(recipe==null)
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Recipe>(recipe,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchBetweenDates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipe entre fechas", notes = "Método para listar recipe entre fechas")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrados"),
            @ApiResponse(code=404, message = "recipe no encontrados")
    }) //Al requestparam le puedes decir que sea opcional y no necesita estar en el URL
    public ResponseEntity<List<Recipe>> findRecipeByCreated_atBetweenDates(@RequestParam("date1") String date1_string,
                                                                           @RequestParam("date2") String date2_string){
        try{
            Date checking_date = ParseDate(date1_string);
            Date checkout_date = ParseDate(date2_string);
            List<Recipe> recipes = recipeService.findBetweenDates(checking_date, checkout_date);
            if(recipes!=null && recipes.size()>0)
                return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
            else
                return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Date ParseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }
}