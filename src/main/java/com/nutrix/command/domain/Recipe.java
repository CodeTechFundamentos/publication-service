package com.nutrix.command.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="Recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name", nullable = false, length = 50)
    private String name;
    @Column(name="description", nullable = false, length = 250)
    private String description;
    @Column(name="preparation", nullable = false, length = 500)
    private String preparation;
    @Column(name="ingredients", nullable = false, length = 500)
    private String ingredients;
    @Column(name="favorite", nullable = true, length = 10)
    private Long favorite;
    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    @Column(name="last_modification", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_modification;

    private Integer nutritionistId;


    public Recipe save(Recipe recipe) throws Exception{return null;}
    public void delete(Integer id) throws Exception{}
    protected List<Recipe> getAll() throws  Exception{return null;}
    public Optional<Recipe> getById(Integer id) throws Exception{return null;}
    public List<Recipe> findAllByNutritionist(Integer nutritionist_id) throws Exception{return null;}
    public Recipe findByName(String name) throws Exception{return null;}
    public List<Recipe> findBetweenDates(Date date1, Date date2) throws Exception{return null;}

}
