package tbd.group2.spring.backend.sakila.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name="film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="film_id", unique=true, nullable=false)
    private int filmId;

    @Column(name="description", nullable=false, length=256)
    private String description;

    @Column(name="length", nullable=false)
    private Integer length;

    @Column(name="language_id")
    private Integer lenguage_id;

    @Column(name="original_language_id")
    private Integer originalLenguageId;

    @Column(name="rating", length=15)
    private String rating;

    @Column(name="release_year")
    private Date releaseYear;

    @Column(name="rental_duration", nullable=false)
    private Integer rentalDuration;

    @Column(name="rental_rate", nullable=false)
    private Float rentalRate;

    @Column(name="replacement_cost", nullable=false)
    private Float replacementCost;

    @Column(name="special_features")
    private String specialFeatures;

    @Column(name="title", nullable=false, length=45)
    private String title;

    @Column(name="last_update", nullable=false)
    private Timestamp lastUpdate;

    @ManyToMany(mappedBy = "actorFilm")
    Set<Actor> filmActor;

    public Set<Actor> getFilmActor() {
        return filmActor;
    }

    public void setFilmActor(Set<Actor> filmActor) {
        this.filmActor = filmActor;
    }

    public Film() {
    }


    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLenguage_id() {
        return lenguage_id;
    }

    public void setLenguage_id(Integer lenguage_id) {
        this.lenguage_id = lenguage_id;
    }

    public Integer getOriginalLenguageId() {
        return originalLenguageId;
    }

    public void setOriginalLenguageId(Integer originalLenguageId) {
        this.originalLenguageId = originalLenguageId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Integer rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public Float getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(Float rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Float getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(Float replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
