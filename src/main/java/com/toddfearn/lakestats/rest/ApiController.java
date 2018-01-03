package com.toddfearn.lakestats.rest;

import com.toddfearn.lakestats.model.LakeName;
import com.toddfearn.lakestats.service.LakeStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    private LakeStatistics lakeStatistics;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        return new ResponseEntity<String>("Hello world", HttpStatus.OK);
    }

    @RequestMapping(value = "/lakestats", method = RequestMethod.GET)
    public ResponseEntity<String> lakeStats() {
        return new ResponseEntity<String>(lakeStatistics.get(LakeName.LAKE_CHAMPLAIN), HttpStatus.OK);
    }
}