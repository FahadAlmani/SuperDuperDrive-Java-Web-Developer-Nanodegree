package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.DTO.CredentialsDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsModel;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public int createCredential(CredentialsModel credentialsModel){
        String encodedKey = getRandomKey();
        String encryptedPassword = encryptionService.encryptValue(credentialsModel.getPassword(), encodedKey);
        return credentialsMapper.insert(new CredentialsModel(null,
                credentialsModel.getUrl(),
                credentialsModel.getUsername(),
                encodedKey,
                encryptedPassword,
                this.userService.getUserId()
        ));
    }
    public void deleteCredential(int credentialId){
        this.credentialsMapper.delete(credentialId);
    }

    public void updateCredential(CredentialsModel credentialsModel) {
        credentialsModel.setKey(getRandomKey());
        String encryptedPasswords = encryptionService.encryptValue(credentialsModel.getPassword(), credentialsModel.getKey());
        credentialsModel.setPassword(encryptedPasswords);
        this.credentialsMapper.update(credentialsModel);
    }
    public ArrayList<CredentialsDTO> getCredential(){
        ArrayList<CredentialsModel> credentials = this.credentialsMapper.getCredentials(this.userService.getUserId());
        ArrayList<CredentialsDTO> credentialsDTOS = new ArrayList<>();
        for (CredentialsModel credentialsModel: credentials ) {
            String DecryptedPassword = this.encryptionService.decryptValue(credentialsModel.getPassword(), credentialsModel.getKey());
            CredentialsDTO credentialsDTO = new CredentialsDTO(credentialsModel.getCredentialId(),
                    credentialsModel.getUrl(),
                    credentialsModel.getUsername(),
                    credentialsModel.getKey(),
                    credentialsModel.getPassword(),
                    credentialsModel.getUserId(),
                    DecryptedPassword);
            credentialsDTOS.add(credentialsDTO);
        }

        return credentialsDTOS;
    }

    private String getRandomKey(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
       return Base64.getEncoder().encodeToString(salt);
    }


}
