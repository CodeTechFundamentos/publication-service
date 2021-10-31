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
@Table(name = "recommendation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description", nullable = false, length = 250)
    private String description;
    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name="last_modification", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;

    private Integer nutritionistId;

    public Recommendation save(Recommendation recommendation) throws Exception{return null;}
    public void delete(Integer id) throws Exception{}
    protected List<Recommendation> getAll() throws  Exception{return null;}
    public Optional<Recommendation> getById(Integer id) throws Exception{return null;}
    public List<Recommendation> findByName(String name) throws Exception{return null;}
    public List<Recommendation> findBetweenDates(Date date1, Date date2) throws Exception{return null;}
    public List<Recommendation> findByNutritionist(Integer nutritionist_id) throws Exception{return null;}
}
