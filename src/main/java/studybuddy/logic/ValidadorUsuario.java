/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
public class ValidadorUsuario implements IValidadorUsuario {

    private static final String DOMINIO_INSTITUCIONAL = "@unimayor.edu.co";

    /**
     * Valida que el correo pertenezca al dominio institucional.
     * @param correo Correo a validar
     * @return true si es válido, false en caso contrario
     */
    @Override
    public boolean validarDominioInstitucional(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return correo.toLowerCase().endsWith(DOMINIO_INSTITUCIONAL);
    }

    /**
     * Valida que ningún campo obligatorio esté vacío.
     * @param campos Arreglo de strings a validar
     * @return true si todos tienen contenido, false si alguno está vacío
     */
    @Override
    public boolean validarCamposNoVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
