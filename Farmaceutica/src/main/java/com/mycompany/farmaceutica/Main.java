/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmaceutica;

import javax.swing.SwingUtilities;
import View.GUIEmpresa;

/**
 *
 * @author isaacmgz
 */
public class Main {
    public static void main(String[] args) {
        // Arranca la UI en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            GUIEmpresa ventana = new GUIEmpresa();
            ventana.setLocationRelativeTo(null);  // centra la ventana
            ventana.setVisible(true);
        });
    }
}
