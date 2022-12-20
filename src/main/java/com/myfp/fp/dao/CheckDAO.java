package com.myfp.fp.dao;

import com.myfp.fp.entities.Check;

public interface CheckDAO extends DAO<Check> {
    Check readLast() throws DAOException;
}
