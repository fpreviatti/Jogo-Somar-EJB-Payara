/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ejb;

import br.entity.Competidor;
import java.util.ArrayList;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author sacarolhas
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "java/Fila"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
}
)
public class EjbConsumidor implements MessageListener {

    @Override
    public void onMessage(Message message) {
       try{
           
           ObjectMessage tm = (ObjectMessage) message;
            
            System.out.println(tm.getClass().getSimpleName());
            
            ArrayList<Competidor> competidores = new ArrayList();
            competidores = (ArrayList<Competidor>) tm.getObject();
            System.out.println("RANKING:");
            for(int i=0; i<competidores.size(); i++){
                System.out.println(competidores.get(i).getNome() +" - Pontuação: " +competidores.get(i).getPontuacao());
            }

        }
        catch(Exception e){
            
        }
    }
    
}
