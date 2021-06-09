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
	public ModelAndView getIndexPage() {
		return new ModelAndView("index");
	}

	@PostMapping("log")
	@ResponseBody
	public ModelAndView logUser(@RequestParam MultiValueMap<String, String> body,
			RedirectAttributes redirectAttributes) {
		ModelAndView model;

		User user = service.checkLogin(body.getFirst("username"), body.getFirst("password"));

		if (user == null) {
			boolean userInvalid = true;
			model = new ModelAndView("redirect:/index");
			redirectAttributes.addFlashAttribute("userInvalid", userInvalid);
		} else {
			if (user.getType().equalsIgnoreCase("admin")) {
				model = new ModelAndView("redirect:/admin");
			} else {
				model = new ModelAndView("redirect:/common");
			}
			redirectAttributes.addFlashAttribute("user", user);
		}
		return model;
	}

	@GetMapping("admin")
	public ModelAndView getAdminPage(@ModelAttribute("user") User user) {
		ModelAndView model = new ModelAndView("admin");
		model.addObject("user", user);
		return model;
	}

	@GetMapping("common")
	public ModelAndView getCommonPage(@ModelAttribute("user") User user) {
		ModelAndView model = new ModelAndView("common");
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = {"register_page", "register_page/{teacherId}"})
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

	@PostMapping(value = {"register", "register/{teacherId}"})
	@ResponseBody
	public ModelAndView registerTeacher(@RequestParam MultiValueMap<String, String> body, @PathVariable(required = false, value = "teacherId") Integer teacherId, @ModelAttribute("user") User user) {
		ModelAndView model = new ModelAndView("redirect:/" + user.getType());
		
		Teacher teacher = new Teacher(body.getFirst("name"), body.getFirst("subject"),
				Double.parseDouble(body.getFirst("cost")), body.getFirst("email"), body.getFirst("phone"));

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
	public ModelAndView postDeletePage(@PathVariable("teacherId") Integer teacherId, @ModelAttribute("user") User user) {
		ModelAndView model = new ModelAndView("redirect:/" + user.getType());
		
		service.deleteTeacher(teacherId);
		
		return model;
	}
}
