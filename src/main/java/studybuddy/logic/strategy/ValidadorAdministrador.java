/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic.strategy;

/**
 *
 * @author yoban
 */
import studybuddy.logic.IValidadorUsuario;

public class ValidadorAdministrador implements IValidadorUsuario {

    private static final String DOMINIO =
            "@unimayor.edu.co";

    @Override
    public boolean validarDominioInstitucional(String correo) {

        return correo != null
                && correo.trim().toLowerCase().endsWith(DOMINIO);
    }

    @Override
    public boolean validarCamposNoVacios(String... campos) {

        if (campos == null) {
            return false;
        }

        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}

