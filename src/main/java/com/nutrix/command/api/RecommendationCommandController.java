package com.nutrix.command.api;

import com.nutrix.command.application.services.RecommendationCommandService;
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

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/recommendation")
@Api(tags = "Recommendation", value = "Servicio Web RESTful de Recommendation")
public class RecommendationCommandController {

    @Autowired
    private RecommendationCommandService recommendationService;
    @Autowired
    private RecommendationQueryService recommendationQueryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Recommendations", notes = "Método para registrar un Recommendation")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recommendation creado"),
            @ApiResponse(code = 404, message = "Recommendation no fue creado")
    })
    public ResponseEntity<Recommendation> insertRecommendation(@Valid @RequestBody Recommendation recommendation){
        try{
            Recommendation newRecommendation = recommendationService.save(recommendation);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRecommendation);
        }catch (Exception e){
            return new ResponseEntity<Recommendation>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Recommendation", notes = "Método que updatea los datos de un Recommendation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recommendation actualizada"),
            @ApiResponse(code = 404, message = "Recommendation no encontrado")
    })
    public ResponseEntity<Recommendation> updateRecommendation(
            @PathVariable("id") Integer id, @Valid @RequestBody Recommendation recommendation){
        try {
            Optional<Recommendation> recommendationUp = recommendationQueryService.getById(id);
            if(!recommendationUp.isPresent())
                return new ResponseEntity<Recommendation>(HttpStatus.NOT_FOUND);
            recommendation.setId(id);
            recommendationService.save(recommendation);
            return new ResponseEntity<Recommendation>(recommendation, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Recommendation>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Recommendation", notes = "Método para eliminar un Recommendation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recommendation eliminado"),
            @ApiResponse(code = 404, message = "Recommendation no encontrado")
    })
    public ResponseEntity<Recommendation> deleteRecommendation(@PathVariable("id") Integer id) {
        try{
            Optional<Recommendation> recommendationDelete = recommendationQueryService.getById(id);
            if(!recommendationDelete.isPresent())
                return new ResponseEntity<Recommendation>(HttpStatus.NOT_FOUND);
            recommendationService.delete(id);
            return new ResponseEntity<Recommendation>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Recommendation>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
