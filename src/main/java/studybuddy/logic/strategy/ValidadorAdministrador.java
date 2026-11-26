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
    private static final String DOMINIO_ADMIN = "@admin.unimayor.edu.co";

    @Override
    public boolean validarDominioInstitucional(String correo) {
        return correo != null && correo.toLowerCase().endsWith(DOMINIO_ADMIN);
    }

    @Override
    public boolean validarCamposNoVacios(String... campos) {
        if (campos == null || campos.length < 4) return false;
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) return false;
        }
        return true;
    }
}

