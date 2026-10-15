/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
public interface IValidadorUsuario {

    /** true si el correo pertenece al dominio institucional. */
    boolean validarDominioInstitucional(String correo);

    /** true si ningún campo obligatorio es nulo o está vacío. */
    boolean validarCamposNoVacios(String... campos);
}