/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package br.ejb;

import javax.ejb.Stateless;

@Stateless
public class EjbLocal implements EjbLocalLocal {

    @Override
    public int somarValores(int a, int b) {
        return a+b;
    }
}
