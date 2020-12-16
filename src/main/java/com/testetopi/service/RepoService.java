package com.testetopi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.testetopi.models.Items;
import com.testetopi.models.Repo;

@Service
public class RepoService {
	
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<Repo> repo;
	List<Items> items = new ArrayList<>();
	List<Items> listaFinal = new ArrayList<>();
	List<Items> listItems = getListaFinal();

	public UriComponents fetchByLanguage(String language, int page) {
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https").host("api.github.com")
				.path("search/repositories")
				.queryParam("q", "language:" + language)
				.queryParam("sort", "stars")
				.queryParam("page", page)
				.build();
		
		String url = uri.toUriString();
		repo = restTemplate.getForEntity(url, Repo.class);
		items = repo.getBody().getItems();
		
		if(url.contains(":&")) {
			items = null;
			listaFinal.clear();
		}
		
		
		if(items != null) {
			listaFinal.clear();
			for(int i = 0; i < items.size(); i++) {
				listaFinal.add(items.get(i));
			}	
		}
		
		return uri;
	}

	public List<Items> getItems() {
		return items;
	}
	
	public void setItems(List<Items> items) {
		this.items = items;
	}
	
	public List<Items> getListaFinal() {
		return listaFinal;
	}

	public void setListaFinal(List<Items> listaFinal) {
		this.listaFinal = listaFinal;
	}
	
	public Page<Items> pagination(Pageable pageable, String language){
		
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<Items> list;

		if(listItems.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, listItems.size());
			list = listItems.subList(startItem, toIndex);
		}

		Page<Items> itemsPage = new PageImpl<Items>(list, PageRequest.of(currentPage, pageSize), listItems.size());
		return itemsPage;
	}
	
	
	public List<Items> getListItems() {
		return listItems;
	}

	public void setListItems(List<Items> listItems) {
		this.listItems = listItems;
	}
}
