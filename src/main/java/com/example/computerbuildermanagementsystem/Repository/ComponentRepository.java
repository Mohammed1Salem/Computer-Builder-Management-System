package com.example.computerbuildermanagementsystem.Repository;

import com.example.computerbuildermanagementsystem.Model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {
    Component getComponentById(Integer id);

    @Query("select c from Component c where c.type like %?1%")
    List<Component> getByType(String type);

    @Query("select c from Component c where c.price < ?1 order by c.type asc")
    List<Component> getByPriceLessThan(double price);

}
