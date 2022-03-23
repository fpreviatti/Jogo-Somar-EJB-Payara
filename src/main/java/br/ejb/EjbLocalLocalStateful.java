/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ejb;

import br.entity.Competidor;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EjbLocalLocalStateful {
    
    public void adicionaCompetidorComPontuacao(Competidor competidor);
    
    public List<Competidor> gerarRanking();
      
}
