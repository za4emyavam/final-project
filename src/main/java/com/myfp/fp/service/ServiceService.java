package com.myfp.fp.service;

import com.myfp.fp.entities.Service;

import java.util.List;

public interface ServiceService {
    List<Service> findAll() throws ServiceException;
    Service findByType(String type) throws ServiceException;
}
