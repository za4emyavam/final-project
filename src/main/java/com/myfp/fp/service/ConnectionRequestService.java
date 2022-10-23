package com.myfp.fp.service;

import com.myfp.fp.entities.ConnectionRequest;

import java.util.List;

public interface ConnectionRequestService {
    Long insertRequest(ConnectionRequest entity) throws ServiceException;
    List<ConnectionRequest> readAll() throws ServiceException;
    ConnectionRequest read(Long id) throws ServiceException;
    void update(ConnectionRequest entity) throws ServiceException;
}
