package com.volna.restaurantservice.repository;
import com.volna.restaurantservice.entity.RestaurantDocument; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface RestaurantDocumentRepository extends JpaRepository<RestaurantDocument,UUID>{
 List<RestaurantDocument> findAllByRestaurantId(UUID restaurantId);
}