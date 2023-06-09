package com.nhnacademy.edu.repository.birthDeath;

import com.nhnacademy.edu.entity.BirthDeathReportResident;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BirthDeathRepositoryCustom {
    BirthDeathReportResident getBirth(int targetSerialNumber);

    BirthDeathReportResident getDeath(int targetSerialNumber);
}
