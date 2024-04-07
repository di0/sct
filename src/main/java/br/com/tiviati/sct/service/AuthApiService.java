package br.com.tiviati.sct.service;

import br.com.tiviati.sct.repository.AuthApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static br.com.tiviati.sct.commons.Constants.API_KEY_HEADER_NAME;
import static br.com.tiviati.sct.commons.Constants.API_KEY_IS_NULL;

@Service
public class AuthApiService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthApiService.class);

    private final AuthApiRepository authApiRepository;

    public AuthApiService(final AuthApiRepository authApiRepository) {
        this.authApiRepository = authApiRepository;
    }

    public boolean isValidApiKey(final String requestApiKey) {
        if (requestApiKey == null) {
            LOG.debug(API_KEY_IS_NULL);
            return false;
        }
        final String token = authApiRepository.findByApiKey(API_KEY_HEADER_NAME).getToken();
        return token.equals(requestApiKey);
    }
}
