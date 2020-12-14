package com.testetopi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.testetopi.models.Repo;
import com.testetopi.service.RepoService;

@SpringBootTest
class TesteTopiApplicationTests {

	@Autowired private RepoService repoService;
	ResponseEntity<Repo> repo;
	
	@Test
	public void consumerApi() {
		
		RestTemplate restTemplate = new RestTemplate();
		String language = "Java";
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.github.com")
				.path("search/repositories")
				.queryParam("q", "language:" + language)
				.queryParam("sort", "stars")
				.build();		

		repo = restTemplate.getForEntity(uri.toUriString(), Repo.class);
		uri = repoService.fetchByLanguage(language, 1);
	}
}
