package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.AddressDao;
import org.zelenikr.pia.domain.Address;

import javax.transaction.Transactional;

/**
 * @author Roman Zelenik
 */
@PreAuthorize("isAuthenticated()")
@Service
@Transactional
public class DefaultAddressManager implements AddressManager{

    private AddressDao addressDao;

    @Autowired
    public DefaultAddressManager(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public void create(Address newAddress) {
        if (!newAddress.isNew()) {
            throw new RuntimeException("Address already exists, use save method for updates!");
        }

        addressDao.save(newAddress);
    }

    @Override
    public void save(Address address) {
        throw new IllegalStateException("Not implemented yet");
    }
}
