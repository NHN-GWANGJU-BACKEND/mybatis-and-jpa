package com.nhnacademy.edu.repository.resident;

import com.nhnacademy.edu.domain.DTO.ResidentDTO;
import com.nhnacademy.edu.entity.QBirthDeathReportResident;
import com.nhnacademy.edu.entity.QResident;
import com.nhnacademy.edu.entity.Resident;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ResidentRepositoryImpl extends QuerydslRepositorySupport implements ResidentRepositoryCustom {

    public ResidentRepositoryImpl() {
        super(Resident.class);
    }


    @Override
    public Page<ResidentDTO> findAllResident(Pageable pageable) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident birthDeath = QBirthDeathReportResident.birthDeathReportResident;

        List<ResidentDTO> list = from(resident)
                .leftJoin(birthDeath)
                .on(birthDeath.pk.residentSerialNumber.eq(resident.residentSerialNumber))
                .select(Projections.constructor(ResidentDTO.class,
                        resident.residentSerialNumber,
                        resident.name,
                        birthDeath.pk.birthDeathTypeCode.isNotNull(),
                        resident.deathDate.isNotNull()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return new PageImpl<>(list, pageable, list.size() + 1L);
    }
}
