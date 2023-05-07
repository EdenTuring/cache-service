package com.assignment.cache.service;

import com.assignment.cache.model.Bond;
import com.assignment.cache.utils.LazyCache;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static com.assignment.cache.service.BondService.BOND_WAS_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class BondServiceTest {

    public static final String TEST_ID = "testId";

    private LazyCache<String, Bond> bondLazyCache = new LazyCache<>();

    @InjectMocks
    private BondService bondService = new BondService(bondLazyCache);

    @Nested
    class happyFlowTests {

        @Test
        void saveRuleOnlyId() {
            final var bond = new Bond(TEST_ID, null, null, null, null, null, null);

            bondService.saveNewBond(bond);

            final var actual = bondService.getBondById(TEST_ID);

            assertEquals(bond, actual);
        }

    }


    @Nested
    class invalid {

        @Test
        void cacheIsEmpty_getIdShouldThrow() {

            final var responseStatusException = assertThrows(ResponseStatusException.class, () -> bondService.getBondById(TEST_ID));

            assertEquals(NOT_FOUND, responseStatusException.getStatusCode());
            assertEquals(BOND_WAS_NOT_FOUND, responseStatusException.getReason());


        }

    }
}
