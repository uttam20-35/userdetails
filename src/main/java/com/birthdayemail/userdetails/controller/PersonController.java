package com.birthdayemail.userdetails.controller;

import com.birthdayemail.userdetails.entity.PersonEntity;
import com.birthdayemail.userdetails.model.Person;
import com.birthdayemail.userdetails.response.PersonResponse;
import com.birthdayemail.userdetails.service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.MonthDay;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
public class PersonController {

    @Autowired
    private final PersonService personService;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;


    public PersonController(PersonService personService, KafkaTemplate<String, String> kafkaTemplate) {
        this.personService = personService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${topic.name.producer}")
    private String topicName;

    @PostMapping(path = "/persons")
    public ResponseEntity<PersonResponse> createPerson(@RequestBody Person person) {
        PersonResponse personResponse = personService.createPerson(person);
        return new ResponseEntity<>(personResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/persons/birthdate")
    public ResponseEntity<Person> findByDate(@RequestParam @DateTimeFormat(pattern= "yyyy-MM-dd") Date birthdate) {
        Person person = personService.findByDate(birthdate);
        return new ResponseEntity<>(person, HttpStatus.OK);

    }

    @GetMapping(path = "/persons/birthdate-list")
    public ResponseEntity<List<PersonEntity>> getlist(@RequestBody @DateTimeFormat(pattern= "yyyy-MM-dd") Date birthdate){
        MonthDay monthDay=MonthDay.now();
        List<PersonEntity> entityList = (List<PersonEntity>) personService.findByDate(birthdate);
        log.info("Getting the list of person with all the details");
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

   // @Scheduled(cron = "0/30 * * * * *")
    @GetMapping(path = "/persons/month-day")
    public ResponseEntity<List<?>> findByDates()
    {
        MonthDay monthDay=MonthDay.now();
        List<?> entityList = personService.findByDayMonth(monthDay);
        kafkaTemplate.send(topicName, entityList.toString());
        log.info("Sending list of person to the kafka broker "+topicName);
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

}
