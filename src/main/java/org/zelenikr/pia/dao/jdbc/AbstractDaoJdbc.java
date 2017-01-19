package org.zelenikr.pia.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.dao.GenericDao;
import org.zelenikr.pia.domain.BaseObject;
import org.zelenikr.pia.manager.DBSettingsManager;

import javax.annotation.PostConstruct;
import java.sql.*;

/**
 * Abstract class for all JDBC implementation of the {@link GenericDao} interface.
 *
 * @author Roman Zelenik
 */
public abstract class AbstractDaoJdbc<T extends BaseObject> implements GenericDao<T> {

    protected static final String ID = "id";

    @Autowired
    private DBSettingsManager dbSettings;

    @PostConstruct
    private void init() throws ClassNotFoundException {
        Class.forName(dbSettings.getDriver());
    }

    protected Connection openConnection() throws SQLException {
        return DriverManager.getConnection(dbSettings.getUrl(), dbSettings.getUsername(), dbSettings.getPassword());
    }

}
