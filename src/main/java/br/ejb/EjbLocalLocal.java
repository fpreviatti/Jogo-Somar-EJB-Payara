/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package br.ejb;

import javax.ejb.Local;

@Local
public interface EjbLocalLocal {
   public int somarValores(int a, int b);
}
