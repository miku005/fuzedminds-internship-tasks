package com.fuzedminds.service;

import com.fuzedminds.entity.Property;
import com.fuzedminds.payload.PropertyDto;
import com.fuzedminds.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    PropertyDto MapToDto(Property property){
        PropertyDto propertyDto = modelMapper.map(property, PropertyDto.class);
        return propertyDto;
    }

    Property MapToEntity(PropertyDto propertyDto){
        Property property = modelMapper.map(propertyDto, Property.class);
        return property;
    }

    public PropertyDto addProperty(PropertyDto propertyDto) {
        Property property = MapToEntity(propertyDto);
        Property propertySaved = propertyRepository.save(property);
        return MapToDto(propertySaved);
    }
}
