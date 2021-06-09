package br.fiap.bootcamp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fiap.bootcamp.entity.Teacher;
import br.fiap.bootcamp.entity.User;
import br.fiap.bootcamp.repository.BootcampTeacherRepository;
import br.fiap.bootcamp.repository.BootcampUserRepository;

@Service
public class BootcampService {
	
	@Autowired
	BootcampUserRepository userRepository;
	
	@Autowired
	BootcampTeacherRepository teacherRepository;
	
	public User checkLogin(String username, String password) {
		return userRepository.findUserByUsernameAndPassword(username, password);
	}
	
	public void registerTeacher(Teacher teacher) {
		teacherRepository.saveAndFlush(teacher);
	}
	
	public Teacher findTeacherById(Integer teacherId) {
		Optional<Teacher> optional = teacherRepository.findById(teacherId);
		return optional.get();
	}
	
	public List<Teacher> listAllTeachers() {
		return teacherRepository.findAll();
	}
	
	public void deleteTeacher(Integer teacherId) {
		teacherRepository.deleteById(teacherId);
	}

}
