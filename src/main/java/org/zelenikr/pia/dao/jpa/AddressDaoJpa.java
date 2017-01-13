package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.AddressDao;
import org.zelenikr.pia.domain.Address;

/**
 * @author Roman Zelenik
 */
@Repository
public class AddressDaoJpa extends GenericDaoJpa<Address> implements AddressDao{

    public AddressDaoJpa(){
        super(Address.class);
    }
}
