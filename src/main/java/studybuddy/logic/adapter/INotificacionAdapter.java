/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.logic.adapter;

/**
 *
 * @author yoban
 */
public interface INotificacionAdapter {

    void enviar(
            String destinatario,
            String asunto,
            String mensaje
    ) throws Exception;

}

