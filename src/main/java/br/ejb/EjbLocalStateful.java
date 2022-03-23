/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ejb;

import br.entity.Competidor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateful;

@Stateful
public class EjbLocalStateful implements EjbLocalLocalStateful{

    static List<Competidor> arr = new ArrayList();

    @Override
    public void adicionaCompetidorComPontuacao(Competidor competidor) {
        boolean competidorNovo=true;
        for(int i=0; i<arr.size(); i++){
            if(arr.get(i).getNome().equals(competidor.getNome())){
                Integer pontuacaoAtual = arr.get(i).getPontuacao();
                
                arr.get(i).setPontuacao(pontuacaoAtual +competidor.getPontuacao());
                competidorNovo=false;
            }
        }
        
        if(competidorNovo==true){
           arr.add(competidor); 
        }
        
    }

    @Override
    public List<Competidor> gerarRanking() {
                
        Collections.sort(arr, new SortByPontuacao());
        
        return arr;
    }

    class SortByPontuacao implements Comparator<Competidor> {

        @Override
        public int compare(Competidor a, Competidor b) {
            return b.getPontuacao() - a.getPontuacao();
        }
    } 
}