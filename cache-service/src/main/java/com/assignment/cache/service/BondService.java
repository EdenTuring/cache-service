package com.assignment.cache.service;

import com.assignment.cache.model.Bond;
import com.assignment.cache.utils.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BondService {

    private static final Logger logger = LoggerFactory.getLogger(BondService.class);


    //package protected for unit testing
    static final String BOND_WAS_NOT_FOUND = "Bond was not found";

    private final Cache<String, Bond> bondCache = new Cache<>();


    public void saveNewBond(Bond bond) {

        final var bondId = bond.bondId();

        logger.info("Adding new bond to cache with id: {}", bondId);

        bondCache.put(bondId, bond);
    }

    public Bond getBondById(String id) {

        logger.info("Looking for bond with id: {}", id);

        final var bond = bondCache.get(id);

        if (bond == null) {

            throw new ResponseStatusException(NOT_FOUND, BOND_WAS_NOT_FOUND);
        }

        logger.info("Found bond with id: {}", id);

        return bond;

    }
}
