package io.github.leonardormlins.fox.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = -2716502827930129870L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = null;

    @Column(length = 16, unique = true)
    @NotNull
    private final String name;
    
    @Column(length = 200, nullable = true)
    private String profilePhoto = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";

    @NotNull
    private final String password;

    @Column(columnDefinition = "boolean default false")
    @NotNull
    private final Boolean admin = false;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author", orphanRemoval = true)
    private final List<Post> posts = Collections.emptyList();
    
    @JsonBackReference("followed")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_follower", 
    	joinColumns = { @JoinColumn(name = "usr_id_following") }, 
    	inverseJoinColumns = { @JoinColumn(name = "usr_id_followed") })
    private List<User> followed;

    // Hibernate requires a no-argument constructor.
    public User() {
        name = null;
        password = null;
    }

    public User(@JsonProperty("name") final String name, @JsonProperty("password") final String password) {
        this.name = name;
        this.password = password;
    }
    
    @JsonView(View.class)
    public Long getId() {
    	return id;
    }

    @JsonView(View.class)
    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @JsonView(Token.View.class)
    public Boolean getAdmin() {
        return admin;
    }

    @JsonView(View.class)
    public List<User> getFollowed() {
        return followed;
    }

    public void setFollowed(final List<User> followed) {
        this.followed = followed;
    }
    
    @JsonView(View.class)
    public String getProfilePhoto() {
    	return profilePhoto;
    }
    
    public void setProfilePhoto(String profilePhoto) {
    	this.profilePhoto = profilePhoto;
    }

    @JsonView(ViewPost.class)
    public List<Post> getPosts() {
    	return posts;
    }
    
    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        final List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>(2);

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (admin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class Token {

        private final String name;

        private final String token;

        private final Boolean admin;

        public Token(final User user, final String token) {
            this.name = user.getName();
            this.token = token;
            this.admin = user.getAdmin();
        }

        @JsonView(View.class)
        public String getName() {
            return name;
        }

        @JsonView(View.class)
        public String getToken() {
            return token;
        }

        @JsonView(View.class)
        public Boolean getAdmin() {
            return admin;
        }

        public interface View extends User.View {

            // Empty.

        }

    }

    public interface View {

        // Empty.

    }
    
    public interface ViewPost {

        // Empty.

    }

}