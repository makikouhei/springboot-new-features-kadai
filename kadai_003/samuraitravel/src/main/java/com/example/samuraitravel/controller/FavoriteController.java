package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;


@Controller
public class FavoriteController {
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;
	private final HouseRepository houseRepository;
	
	public FavoriteController(FavoriteRepository favoriteRepository, FavoriteService favoriteService,HouseRepository houseRepository) {
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
		this.houseRepository = houseRepository;
	}
	
	@GetMapping("/favorites")
    public String index(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("favoritePage", favoritePage);             
        
        return "/favorites/index";
	}
	
	//お気に入り登録
	@GetMapping("/houses/{houseId}/register")
	public String register(@PathVariable("houseId") Integer houseId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		House house = houseRepository.getReferenceById(houseId);
		User user = userDetailsImpl.getUser();
		favoriteService.register(house, user);
		return "redirect:/houses/{houseId}";
	}
	
	//お気に入り解除
	@GetMapping("/houses/{houseId}/delete")
	public String delete(@PathVariable("houseId") Integer houseId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		House house = houseRepository.getReferenceById(houseId);
		favoriteService.delete(house, user);
		return "redirect:/houses/{houseId}" ;
	}
}
