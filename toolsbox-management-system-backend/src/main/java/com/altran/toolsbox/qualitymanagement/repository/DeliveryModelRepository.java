package com.altran.toolsbox.qualitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.toolsbox.qualitymanagement.model.DeliveryModel;

/**
 * Represents repository of delivery
 * 
 * @author Ahmed.Elayeb
 * @version 1.0
 */
@Repository
public interface DeliveryModelRepository extends JpaRepository<DeliveryModel, Long> {

}
