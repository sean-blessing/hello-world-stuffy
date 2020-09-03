package com.stuffy.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.stuffy.business.Stuffy;

public interface StuffyRepository extends JpaRepository<Stuffy, Integer> {
	List<Stuffy> findAllByColor(String color);
}
