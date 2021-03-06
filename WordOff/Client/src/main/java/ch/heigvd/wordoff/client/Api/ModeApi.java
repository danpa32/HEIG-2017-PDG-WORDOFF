/*
 * File: ModeApi.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.Mode.CreateModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

/**
 * Api that holds the calls to handle the differents modes.
 */
public class ModeApi {

    private final static RestTemplate restTemplate = Api.getRestTemplate();

    /**
     * Retrieve the list of the user's modes
     * @return The modes
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static List<ModeSummaryDto> retrieveModes() throws TokenNotFoundException {
        return retrieveModes(TokenManager.loadToken());
    }

    /**
     * Create a new mode
     * @param modeDto The new mode
     * @return The summary of the mode created
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static ModeSummaryDto createMode(CreateModeDto modeDto) throws TokenNotFoundException {
        return createMode(TokenManager.loadToken(), modeDto);
    }

    /**
     * Get a specific mode
     * @param endpoint The endpoint the get the mode
     * @return The mode
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static ModeDto getMode(String endpoint) throws TokenNotFoundException {
        return getMode(TokenManager.loadToken(), endpoint);
    }

    private static List<ModeSummaryDto> retrieveModes(String token) {
        final String uri = SERVER_URI + "/modes";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<ModeSummaryDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<ModeSummaryDto>>() {});

        return responseEntity.getBody();
    }

    private static ModeDto getMode(String token, String endpoint) {
        final String uri = SERVER_URI + endpoint;

        Map<String, String> params = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<ModeDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ModeDto.class,
                        params);

        return responseEntity.getBody();
    }

    private static ModeSummaryDto createMode(String token, CreateModeDto modeDto) {
        final String uri = SERVER_URI + "/modes";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ModeSummaryDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(modeDto, headers),
                        ModeSummaryDto.class);

        return responseEntity.getBody();
    }
}
