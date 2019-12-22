package com.example.APZ.repos;

import com.example.APZ.domain.Door;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoorRepo extends CrudRepository<Door, Integer> {

   List<Door> findByDoorname(String name);

}


