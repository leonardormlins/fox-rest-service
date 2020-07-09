package io.github.leonardormlins.fox.model;

import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = null;

    @NotNull
    private final String pictureLink;

    @JoinColumn(name = "author_id")
    @ManyToOne
    @NotNull
    private User author = null;


    // Hibernate requires a no-argument constructor.
    public Post() {
        pictureLink = null;
    }

    public Post(@JsonProperty("pictureLink") final String pictureLink) {
        this.pictureLink = pictureLink;

    }

    @JsonView(View.class)
    public Long getId() {
        return id;
    }

    @JsonView(View.class)
    public String getPictureLink() {
        return pictureLink;
    }

    @JsonView(View.class)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public interface View extends User.View {

        // Empty.

    }

}