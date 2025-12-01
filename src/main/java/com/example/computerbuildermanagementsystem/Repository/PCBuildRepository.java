package com.example.computerbuildermanagementsystem.Repository;

import com.example.computerbuildermanagementsystem.Model.PCBuild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PCBuildRepository extends JpaRepository<PCBuild, Integer> {
    PCBuild getPCBuildById(Integer id);
}
