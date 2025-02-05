package com.fuzedminds.controller;

import com.fuzedminds.payload.PropertyDto;
import com.fuzedminds.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<?> addProperty(@RequestBody PropertyDto propertyDto){
        PropertyDto addProperty = propertyService.addProperty(propertyDto);
        return new ResponseEntity<>(addProperty, HttpStatus.CREATED);
    }
}
