/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.jsf;

import br.ejb.EjbLocalLocal;
import br.ejb.EjbLocalLocalStateful;
import br.entity.Competidor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

@Named(value = "jsfLocal")
@RequestScoped
public class JsfLocal {

    @EJB
    private EjbLocalLocalStateful ejbLocalStateful;

    @EJB
    private EjbLocalLocal ejbLocal;

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "java/Fila")
    private Queue fila;
    
    public JsfLocal() {
        a = 1 + (int) (Math.random() * 100);
        b = 1 + (int) (Math.random() * 100);
    }
    
    private String nome="";
    private static List<Competidor> lista = new ArrayList();
    private int a=0;
    private int b=0;
    private boolean jogoFinalizado;
    
    private HtmlDataTable dataTable;

    public List<Competidor> getLista() {
        return lista;
    }

    public void setLista(List<Competidor> lista) {
        this.lista = lista;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
    
    private String mensagem;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isJogoFinalizado() {
        return jogoFinalizado;
    }

    public void setJogoFinalizado(boolean jogoFinalizado) {
        this.jogoFinalizado = jogoFinalizado;
    }

    public void enviarRanking(){

        try{

            Connection conn = connectionFactory.createConnection();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage om = session.createObjectMessage();;

            om.setObject((Serializable) lista);
            JMSContext context = connectionFactory.createContext();

            context.createProducer().send(fila, om);

        }
        catch(Exception e){
               System.out.println("ERRO");
               System.out.println(e.getMessage());
        }
        
    }
    
    private int resultado;

    public void validarResultado(){

        if(resultado == ejbLocal.somarValores(a, b)){
            Competidor competidor = new Competidor();
            competidor.setNome(nome);
            competidor.setPontuacao(1);
            ejbLocalStateful.adicionaCompetidorComPontuacao(competidor);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Parabéns, acertou!", "Message body");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
        else{
            Competidor competidor = new Competidor();
            competidor.setNome(nome);
            competidor.setPontuacao(0);
            ejbLocalStateful.adicionaCompetidorComPontuacao(competidor);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Infelizmente você errou!", "Message body");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
        }
        
        gerarRanking();

    }
    
    public void gerarRanking(){
        lista = ejbLocalStateful.gerarRanking();

        for(int i=0; i<lista.size();i++){
            if(lista.get(i).getPontuacao()==5){
                jogoFinalizado=true;
                enviarRanking();    
                
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Jogo finalizado! Vencedor: " +lista.get(i).getNome(), "Message body");
                FacesContext.getCurrentInstance().addMessage(null, message);
                break;
            }
        }
        
        gerarNovosNumeros();
    }
    
    public void iniciarNovoJogo(){
        lista.clear();
        gerarNovosNumeros();
        setNome("");
    }
    
    public void gerarNovosNumeros(){
        a = 1 + (int) (Math.random() * 100);
        b = 1 + (int) (Math.random() * 100);
        resultado=0;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    class SortByPontuacao implements Comparator<Competidor> {

        public int compare(Competidor a, Competidor b) {
            return b.getPontuacao() - a.getPontuacao();
        }
    }

}
