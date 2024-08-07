package com.example.samuraitravel.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	public Page<Review> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);
	public List<Review> findTop6ByHouseOrderByCreatedAtDesc(House house);
	public long countByHouse(House house);
	public Review findByHouseAndUser(House house, User user);
	
	// メソッドの追加: ユーザーが特定の家に対してレビューを投稿しているかどうかを確認する
    boolean existsByUserAndHouse(User user, House house);
}