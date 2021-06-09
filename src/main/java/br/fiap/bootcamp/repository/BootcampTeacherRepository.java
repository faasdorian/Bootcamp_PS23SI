package br.fiap.bootcamp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fiap.bootcamp.entity.Teacher;

@Repository
public interface BootcampTeacherRepository extends JpaRepository<Teacher, Integer> {
}
