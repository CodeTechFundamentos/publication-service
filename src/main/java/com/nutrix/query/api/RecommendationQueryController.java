package com.nutrix.query.api;

import com.nutrix.command.domain.Recommendation;
import com.nutrix.query.application.services.RecommendationQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendation")
@Api(tags = "Recommendation", value = "Servicio Web RESTful de Recommendation")
public class RecommendationQueryController {

    @Autowired
    private RecommendationQueryService recommendationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Recommendations", notes = "Método para listar todos los recommendations")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recommendations encontrados"),
            @ApiResponse(code = 404, message = "Recommendations no ubicados")
    })
    public ResponseEntity<List<Recommendation>> findAll() {
        try {
            List<Recommendation> recommendations = recommendationService.getAll();
            if (recommendations.isEmpty())
                return new ResponseEntity<List<Recommendation>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Recommendation>>(recommendations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Recommendation>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recommendation por Id", notes = "Método para encontrar un Recommendation por Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recommendation encontrado"),
            @ApiResponse(code = 404, message = "Recommendation no ubicado")
    })
    public ResponseEntity<Recommendation> findById(@PathVariable("id") Integer id)
    {
        try{
            Optional<Recommendation> recommendation = recommendationService.getById(id);
            if(!recommendation.isPresent())
                return new ResponseEntity<Recommendation>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Recommendation>(recommendation.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Recommendation>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recommendations por name", notes = "Método para encontrar un Recommendations por name")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recommendations encontrado"),
            @ApiResponse(code = 404, message = "Recommendations no ubicado")
    })
    public ResponseEntity<List<Recommendation>> findByName(@PathVariable("name") String name)
    {
        try{
            List<Recommendation> recommendations = recommendationService.findByName(name);
            if(recommendations.size()>0)
                return new ResponseEntity<List<Recommendation>>(recommendations, HttpStatus.OK);
            return new ResponseEntity<List<Recommendation>>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Recommendation>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @GetMapping(value = "/searchByNutritionistId/{nutritionist_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recommendations por name", notes = "Método para encontrar un Recommendations por id de nutricionista")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recommendations encontrados"),
            @ApiResponse(code = 404, message = "Recommendations no ubicados")
    })
    public ResponseEntity<List<Recommendation>> findByNutritionist(@PathVariable("nutritionist_id") Integer nutritionist_id)
    {
        try{
            List<Recommendation> recommendations = recommendationService.findByNutritionist(nutritionist_id);
            if(recommendations.size()>0)
                return new ResponseEntity<List<Recommendation>>(recommendations, HttpStatus.OK);
            return new ResponseEntity<List<Recommendation>>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Recommendation>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @GetMapping(value = "/searchRecommendationBetweenDates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recommendations entre fechas", notes = "Método para listar Recommendations entre fechas")
    @ApiResponses({
            @ApiResponse(code=201, message = "Recommendations encontrados"),
            @ApiResponse(code=404, message = "Recommendations no encontrados")
    })
    public ResponseEntity<List<Recommendation>> findRecommendationByRecommendationDateBetweenDates(@RequestParam("date1") String date1_string,
                                                                                                   @RequestParam("date2") String date2_string){
        try{
            Date checking_date = ParseDate(date1_string);
            Date checkout_date = ParseDate(date2_string);
            List<Recommendation> recommendations = recommendationService.findBetweenDates(checking_date, checkout_date);
            if(recommendations!=null && recommendations.size()>0)
                return new ResponseEntity<List<Recommendation>>(recommendations, HttpStatus.OK);
            else
                return new ResponseEntity<List<Recommendation>>(recommendations, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Recommendation>>(HttpStatus.INTERNAL_SERVER_ERROR);
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
