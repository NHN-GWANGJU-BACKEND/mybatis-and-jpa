package com.nhnacademy.edu.repository.heart;

import com.nhnacademy.edu.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HeartRepository extends JpaRepository<Heart,Heart.Pk> {
    @Transactional
    @Modifying
    @Query("delete from Heart where Pk = :pk")
    int deleteHeart(@Param("pk") Heart.Pk pk);
}
