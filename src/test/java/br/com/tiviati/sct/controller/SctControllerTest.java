package br.com.tiviati.sct.controller;

import br.com.tiviati.sct.exception.InvalidParameterException;
import br.com.tiviati.sct.exception.NotFoundException;
import br.com.tiviati.sct.handler.RestExceptionHandler;
import br.com.tiviati.sct.model.Beneficiary;
import br.com.tiviati.sct.service.SctService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SctControllerTest {
    @InjectMocks
    private SctController sctController;

    @Mock
    private SctService sctService;

    private RestExceptionHandler restExceptionHandler;

    @Before
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        restExceptionHandler = new FakeRestExceptionHandler();
    }

    @Test
    public void shouldFindBeneficiaryById() {
        final Beneficiary beneficiary = Beneficiary
                .builder()
                .id(1L)
                .build();
        when(sctService.findById(anyLong())).thenReturn(beneficiary);
        final ResponseEntity<Beneficiary> responseWithFoundBeneficiary = sctController.findById(1L);
        assertTrue(responseWithFoundBeneficiary.getStatusCode().is2xxSuccessful());
        assertEquals(1L, (long) responseWithFoundBeneficiary.getBody().getId());
    }

    @Test
    public void shouldNotFindBeneficiaryById() {
        when(sctService.findById(anyLong())).thenThrow(NotFoundException.class);

        try {
            final ResponseEntity<Beneficiary> responseWithFoundBeneficiary = sctController.findById(1L);
            fail("The method responsible for find a beneficiary didn't fail as expected." +
                    " Found: " + responseWithFoundBeneficiary);
        } catch (NotFoundException e) {
            final ResponseEntity<Object> errorHandlerResponseEntity =
                    restExceptionHandler.handleNotFoundException(e);
            assertTrue(errorHandlerResponseEntity.getStatusCode().is4xxClientError());
        }
    }

    @Test
    public void shouldReturnSuccessfulHttpStatusCodeWhenCreateBeneficiary() {
        final Beneficiary beneficiary = Beneficiary
                .builder()
                .id(1L)
                .build();
        when(sctService.create(any(Beneficiary.class))).thenReturn(beneficiary);
        final ResponseEntity<Beneficiary> responseWithCreatedBeneficiary = sctController.create(beneficiary);
        assertTrue(responseWithCreatedBeneficiary.getStatusCode().is2xxSuccessful());
        assertTrue(responseWithCreatedBeneficiary.getHeaders().getLocation().getPath().equals("/1"));
    }

    @Test
    public void shouldReturnErrorHttpStatusCodeWhenCreateBeneficiary() {
        final Beneficiary beneficiary = Beneficiary
                .builder()
                .id(1L)
                .cpf("um-cpf-invalido")
                .build();
        when(sctService.create(any(Beneficiary.class))).thenThrow(InvalidParameterException.class);

        try {
            final ResponseEntity<Beneficiary> responseWithCreatedBeneficiary = sctController.create(beneficiary);
            fail("The method responsible for creating a beneficiary didn't fail as expected." +
                    " Created: " + responseWithCreatedBeneficiary);
        } catch (InvalidParameterException e) {
            final ResponseEntity<Object> errorHandlerResponseEntity =
                    restExceptionHandler.handleInvalidParametersException(e);
            assertTrue(errorHandlerResponseEntity.getStatusCode().is4xxClientError());
        }
    }

    @Test
    public void shouldUpdateSuccessfully() {
        final Beneficiary beneficiary = Beneficiary
                .builder()
                .id(1L)
                .build();
        when(sctService.update(anyLong(), any(Beneficiary.class))).thenReturn(beneficiary);
        ResponseEntity responseAfterUpdatedBeneficiary = sctController.update(1L, beneficiary);
        assertTrue(responseAfterUpdatedBeneficiary.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldNotUpdateWhenBeneficiaryNotExists() {
        final Beneficiary beneficiary = Beneficiary
                .builder()
                .id(1L)
                .build();
        when(sctService.update(anyLong(), any(Beneficiary.class))).thenThrow(NotFoundException.class);

        try {
            final ResponseEntity<Beneficiary> responseWithUpdateBeneficiary = sctController.update(1L, beneficiary);
            fail("The method responsible for updating a beneficiary didn't fail as expected." +
                    " Updated: " + responseWithUpdateBeneficiary);
        } catch (NotFoundException e) {
            final ResponseEntity<Object> errorHandlerResponseEntity =
                    restExceptionHandler.handleNotFoundException(e);
            assertTrue(errorHandlerResponseEntity.getStatusCode().is4xxClientError());
        }
    }

    @Test
    public void shouldDeleteSuccessfully() {
        doNothing().when(sctService).delete(anyLong());
        ResponseEntity<Void> responseEntityAfterDelete = sctController.delete(1L);
        assertNull(responseEntityAfterDelete.getBody());
    }

    private static class FakeRestExceptionHandler extends RestExceptionHandler {}
}
