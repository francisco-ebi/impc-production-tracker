/*******************************************************************************
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *******************************************************************************/
package uk.ac.ebi.impc_prod_tracker.web.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.impc_prod_tracker.conf.error_management.OperationFailedException;
import uk.ac.ebi.impc_prod_tracker.conf.security.AuthorizationHeaderReader;
import uk.ac.ebi.impc_prod_tracker.conf.security.SystemSubject;
import uk.ac.ebi.impc_prod_tracker.data.organization.person.Person;
import uk.ac.ebi.impc_prod_tracker.domain.login.AuthenticationRequest;
import uk.ac.ebi.impc_prod_tracker.domain.login.UserRegisterRequest;
import uk.ac.ebi.impc_prod_tracker.service.authentication.AuthService;
import uk.ac.ebi.impc_prod_tracker.service.PersonService;
import uk.ac.ebi.impc_prod_tracker.web.dto.auth.AuthenticationResponseDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.auth.PersonRoleConsortiumDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.auth.PersonRoleWorkUnitDTO;
import uk.ac.ebi.impc_prod_tracker.web.dto.auth.UserSecurityInformationDTO;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for end points related with the auth process.
 *
 * @author Mauricio Martinez
 */
@RestController
@RequestMapping("/auth")
public class AuthController
{
    private static final String AUTHENTICATION_ERROR = "Invalid User/Password provided.";
    private AuthService authService;
    private PersonService personService;

    private AuthorizationHeaderReader authorizationHeaderReader = new AuthorizationHeaderReader();

    public AuthController(AuthService authService, PersonService personService)
    {
        this.authService = authService;
        this.personService = personService;
    }

    /**
     * Signin a user to obtain a token and some basic information about the user.
     * @param authenticationRequest Object with the user's credentials.
     * @return Token if authenticated, as well as basic information like role and workUnitName.
     */
    @PostMapping(value = {"/signin"})
    public ResponseEntity signIn(@RequestBody AuthenticationRequest authenticationRequest)
    {
        try
        {
            String username = authenticationRequest.getUserName();
            String token = authService.getAuthenticationToken(authenticationRequest);
            AuthenticationResponseDTO authenticationResponseDTO = buildAuthenticationResponseDTO(
                username, token);

            return ok(authenticationResponseDTO);
        }
        catch (AuthenticationException e)
        {
            throw new OperationFailedException(AUTHENTICATION_ERROR);
        }
    }

    private AuthenticationResponseDTO buildAuthenticationResponseDTO(String userName, String token)
    {
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setUserName(userName);
        authenticationResponseDTO.setAccessToken(token);
        return authenticationResponseDTO;
    }

    /**
     *      * @api {post} /signin Signin a user to obtain a token.
     * @apiName Signin
     * @apiGroup User
     */
    @PostMapping(value = {"/signup"})
    @PreAuthorize("hasPermission(null, 'CREATE_USER')")
    public ResponseEntity signup(@RequestBody UserRegisterRequest userRegisterRequest)
    {
        try
        {
            Person person = personService.createPerson(userRegisterRequest);
            System.out.println("User created! Welcome! "+ person);
            return ok(person);
        }
        catch (AuthenticationException e)
        {
            throw new OperationFailedException(AUTHENTICATION_ERROR);
        }
    }

    @GetMapping(value = {"/securityInformation"})
    public UserSecurityInformationDTO signIn(HttpServletRequest req)
    {
        String token = authorizationHeaderReader.getAuthorizationToken(req);
        SystemSubject person = personService.getPersonByToken(token);
        UserSecurityInformationDTO userSecurityInformationDTO = buildUserSecurityInformationDTO(person);
        return userSecurityInformationDTO;
    }

    private UserSecurityInformationDTO buildUserSecurityInformationDTO(SystemSubject person)
    {
        UserSecurityInformationDTO userSecurityInformationDTO = new UserSecurityInformationDTO();
        userSecurityInformationDTO.setUserName(person.getLogin());
        if (person.getRoleWorkUnits() != null)
        {
            List<PersonRoleWorkUnitDTO> roleWorkUnitDTOS = new ArrayList<>();
            person.getRoleWorkUnits().forEach(
                x ->
                {
                    PersonRoleWorkUnitDTO personRoleWorkUnitDTO = new PersonRoleWorkUnitDTO();
                    personRoleWorkUnitDTO.setRoleName(x.getRole().getName());
                    personRoleWorkUnitDTO.setWorkUnitName(x.getWorkUnit().getName());
                    roleWorkUnitDTOS.add(personRoleWorkUnitDTO);
                });
            userSecurityInformationDTO.setRolesWorkUnits(roleWorkUnitDTOS);
        }
        if (person.getRoleConsortia() != null)
        {
            List<PersonRoleConsortiumDTO> roleConsortiumDTOS = new ArrayList<>();
            person.getRoleConsortia().forEach(
                x ->
                {
                    PersonRoleConsortiumDTO personRoleConsortiumDTO = new PersonRoleConsortiumDTO();
                    personRoleConsortiumDTO.setRoleName(x.getRole().getName());
                    personRoleConsortiumDTO.setConsortiumName(x.getConsortium().getName());
                    roleConsortiumDTOS.add(personRoleConsortiumDTO);
                });
            userSecurityInformationDTO.setRolesConsortia(roleConsortiumDTOS);
        }
        userSecurityInformationDTO.setAdmin(person.isAdmin());
        return userSecurityInformationDTO;
    }
}