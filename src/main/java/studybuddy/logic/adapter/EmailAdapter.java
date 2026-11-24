/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic.adapter;

/**
 *
 * @author yoban
 */
public class EmailAdapter implements INotificacionAdapter {

    @Override
    public void enviar(
            String destinatario,
            String asunto,
            String mensaje
    ) throws Exception {

        // Aquí se conectará el proveedor de correo.
        // Por ejemplo: Jakarta Mail, SendGrid o JavaMail.

        System.out.println("Enviando correo electrónico...");
        System.out.println("Destinatario: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Mensaje: " + mensaje);
    }
}