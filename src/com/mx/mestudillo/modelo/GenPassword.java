package com.mx.mestudillo.modelo;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

/**
 *
 * @author Marco Estudillo
 */
public class GenPassword {

    /**
     * Variables que establecen el número de caracteres y dígitos a generar en
     * el password
     */
    private CharacterRule alfabeto;
    private CharacterRule digitos;

    private String password;

    /**
     * Función encargada de la generación aleatoria de passwords
     *
     * @return password
     */
    public String getPassword() {
        alfabeto = new CharacterRule(EnglishCharacterData.Alphabetical, 6);
        digitos = new CharacterRule(EnglishCharacterData.Digit, 2);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        this.password = passwordGenerator.generatePassword(8, alfabeto, digitos);

        return this.password;
    }
}
