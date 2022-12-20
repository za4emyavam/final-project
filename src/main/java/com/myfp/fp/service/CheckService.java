package com.myfp.fp.service;

import com.myfp.fp.entities.Check;

public interface CheckService {
    Check readLast() throws ServiceException;
}
