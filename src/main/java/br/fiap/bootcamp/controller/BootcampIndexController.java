package br.fiap.bootcamp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.fiap.bootcamp.entity.Teacher;
import br.fiap.bootcamp.entity.User;
import br.fiap.bootcamp.service.BootcampService;

@Controller
@SessionAttributes("user")
public class BootcampIndexController {

	@Autowired
	BootcampService service;

	@ModelAttribute("user")
	public User user() {
		return new User();
	}

	@GetMapping("index")
	public ModelAndView getHomePage() {
		return new ModelAndView("index");
	}
	
	@GetMapping("contact")
	public ModelAndView getContactPage() {
		return new ModelAndView("contact");
	}
	
	@GetMapping("login")
	public ModelAndView getLoginPage() {
		return new ModelAndView("login");
	}

	@PostMapping("log")
	@ResponseBody
	public ModelAndView logUser(@RequestParam MultiValueMap<String, String> body,
			RedirectAttributes redirectAttributes) {
		ModelAndView model;

		User user = service.checkLogin(body.getFirst("username"), body.getFirst("password"));

		if (user == null) {
			boolean userInvalid = true;
			model = new ModelAndView("redirect:/login");
			redirectAttributes.addFlashAttribute("userInvalid", userInvalid);
		} else {
			model = new ModelAndView("redirect:/list");
			redirectAttributes.addFlashAttribute("user", user);
		}
		return model;
	}

	@GetMapping("user")
	public ModelAndView getUserPage(@ModelAttribute("user") User user) {
		ModelAndView model = new ModelAndView("user");
		model.addObject("user", user);
		return model;
	}

	@PostMapping(value = {"register_page", "register_page/{teacherId}"})
	@ResponseBody
	public ModelAndView getRegisterPage(@PathVariable(required = false) Integer teacherId) {
		ModelAndView model = new ModelAndView("register");
		Teacher teacher = new Teacher();

		if (teacherId != null) {
			teacher = service.findTeacherById(teacherId);
		}
		model.addObject("teacher", teacher);

		return model;
	}

	@PostMapping(value = { "register", "register/{teacherId}" })
	@ResponseBody
	public ModelAndView registerTeacher(@RequestParam MultiValueMap<String, String> body,
			@PathVariable(required = false, value = "teacherId") Integer teacherId) {		
		ModelAndView model = new ModelAndView("redirect:/list");
		String name = body.getFirst("name");
		String subject = body.getFirst("subject");
		Double cost = Double.parseDouble(body.getFirst("cost"));
		String email = body.getFirst("email");
		String phone = body.getFirst("phone");

		Teacher teacher = new Teacher(name, subject, cost, email, phone);

		if (teacherId != null) {
			teacher.setTeacherId(teacherId);
		}
		service.registerTeacher(teacher);

		return model;
	}

	@GetMapping("list")
	public ModelAndView getListPage() {
		ModelAndView model = new ModelAndView("list");
		List<Teacher> teachers = service.listAllTeachers();
		model.addObject("teachers", teachers);
		return model;
	}

	@RequestMapping("delete/{teacherId}")
	@ResponseBody
	public ModelAndView postDeletePage(@PathVariable("teacherId") Integer teacherId) {
		ModelAndView model = new ModelAndView("redirect:/list");
		service.deleteTeacher(teacherId);
		return model;
	}
}
