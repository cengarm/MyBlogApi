package com.example.MyBlogApi.utils.mappers;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper forRequest();
    ModelMapper forResponse();

}