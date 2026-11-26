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

public class ValidadorEstudiante implements IValidadorUsuario {
    @Override
    public boolean validarCamposNoVacios(String... campos) {
        // Estudiante necesita: nombre, correo, contraseña (3 campos)
        if (campos == null || campos.length < 3) return false;
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) return false;
        }
        return true;
    }
}
