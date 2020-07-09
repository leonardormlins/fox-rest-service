package io.github.leonardormlins.fox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.github.leonardormlins.fox.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(final String name);
    
    @Query( value = "select * from user u WHERE u.id IN (select usr_id_followed from user_follower uf where uf.usr_id_following = (Select id from user where user.name=:name))", 
    		  nativeQuery = true)
    Iterable<User> findFollowedByName(final String name);

    List<User> findByNameIn(final List<String> names);
    
    @Transactional
    @Modifying
    @Query(
      value = 
        "insert into user_follower (usr_id_following,usr_id_followed) values (:following, :followed)",
      nativeQuery = true)
    void follow(@Param("following") Long following, @Param("followed") Long followed);

    @Transactional
    @Modifying
    @Query(
    	value="update user SET profile_photo = :link WHERE user.name = :name",
    	nativeQuery=true)
    void updatePhoto(@Param("link") String link, @Param("name") String name);
}