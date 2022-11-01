package com.birthdayemail.userdetails.repository;

import com.birthdayemail.userdetails.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.MonthDay;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query(value = "select * from person_details pd where pd.birthdate=?1", nativeQuery = true)
    Optional<PersonEntity> findByDate(Date birthdate);

    @Query(value = "select email_id from person_details where to_char(CURRENT_DATE, 'MM-dd')=to_char(day_month, 'MM-dd')", nativeQuery = true)
    List<?> findAllByDayMonth(MonthDay monthDay);
}
