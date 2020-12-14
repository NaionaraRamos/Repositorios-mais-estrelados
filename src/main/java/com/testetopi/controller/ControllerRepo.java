package com.testetopi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
			@ModelAttribute("language") String language
			//, Pageable pageable
			){
		
		ModelAndView mv = new ModelAndView();
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		Page<Items> itemsPage = repoService.pagination(PageRequest.of(currentPage, pageSize), language);
		
		int totalPages = itemsPage.getTotalPages();
		
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			System.out.println("Page numbers: " + pageNumbers);
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		model.addAttribute("language", language);
		model.addAttribute("itemsPage", itemsPage);
		
		repoService.fetchByLanguage(language, currentPage);
		//items = repoService.getListaFinal();
//		items = repoService.getListItems();
		items = repoService.getItems();
		
		System.out.println(items);
		
		if(language != null) {
			mv.addObject("items", items);
		}
		
		//System.out.println("Page: " + page);
		//System.out.println("Size: " + size);
//		System.out.println("Current Page: " + currentPage);
//		System.out.println("Page Size: " + pageSize);
		//System.out.println("Items Page: " + itemsPage);
		try {
			mv.setViewName("index_thymeleaf");
		} catch(Exception e) {
			mv.setViewName("error");
		}
		
		return mv;
	}
//	
//	@GetMapping("/error")
//	public String erro() {
//		return "/error";
//	}
}
