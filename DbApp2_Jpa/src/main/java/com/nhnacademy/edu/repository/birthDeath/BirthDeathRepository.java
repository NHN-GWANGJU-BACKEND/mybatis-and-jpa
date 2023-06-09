package com.nhnacademy.edu.repository.birthDeath;

import com.nhnacademy.edu.entity.BirthDeathReportResident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthDeathRepository extends JpaRepository<BirthDeathReportResident, BirthDeathReportResident.Pk>,
        BirthDeathRepositoryCustom {
}
