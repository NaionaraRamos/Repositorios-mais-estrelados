package com.testetopi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.testetopi.models.Items;
import com.testetopi.service.RepoService;

@Controller
public class ControllerRepo {
	
	@Autowired private RepoService repoService;
	List<Items> items = new ArrayList<>();
	
	@GetMapping("/")
	public ModelAndView fetchRepos(Model model,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("per_page") Optional<Integer> size,
			@ModelAttribute("language") String language){
		
		ModelAndView mv = new ModelAndView();
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		repoService.fetchByLanguage(language, currentPage);
		items = repoService.getListItems();
		
		Page<Items> itemsPage = repoService.pagination(PageRequest.of((currentPage)-1, pageSize), language);
		
		int totalPages = itemsPage.getTotalPages();
		
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}	
		
		model.addAttribute("language", language);
		model.addAttribute("itemsPage", itemsPage);
		
		
		if(language != null) {
			mv.addObject("items", items);
		}
		
		try {
			mv.setViewName("index_thymeleaf");
		} catch(Exception e) {
			mv.setViewName("error");
		}
		
		return mv;
	}
}
