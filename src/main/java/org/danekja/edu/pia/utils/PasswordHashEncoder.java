package org.danekja.edu.pia.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Dummy encoder doing literally nothing.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@Component
public class PasswordHashEncoder implements Encoder, PasswordEncoder {

    @Override
    public String encode(String text) {
        try {
            return PasswordHash.createHash(text);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //TODO log!
            return null;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            //TODO log!
            return null;
        }
    }

    @Override
    public boolean validate(String text, String hash) {
        try {
            return PasswordHash.validatePassword(text, hash);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //TODO log!
            return false;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            //TODO log!
            return false;
        }
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encode(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return validate(rawPassword.toString(), encodedPassword);
    }
}
