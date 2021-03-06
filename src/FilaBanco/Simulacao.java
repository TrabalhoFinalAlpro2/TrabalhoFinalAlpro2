/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilaBanco;

import java.util.Random;



/*
 * Classe com a logica da simulacao passo-a-passo
 */
public class Simulacao
{
    private static final int duracao = 200;
    private static final double probabilidadeChegada = 0.6;
    private QueueTAD<Cliente> filaCaixa, filaPrioritaria;
    private StackTAD<Cliente> filaGerente;
    private Caixa caixa1, caixa2, caixaPrioritaria; 
    private Gerente gerente1;
    private GeradorClientes geradorClientes;
    private Acumulador statTemposEsperaFila, statTemposEsperaFilaGerente, statTemposEsperaFilaPrioritaria;
    private Acumulador statComprimentosFila, statComprimentosFilaGerente, statComprimentosFilaPrioritaria;
    private boolean trace; //valor indica se a simulacao ira imprimir passo-a-passo os resultados
    private static final Random geradorIdade = new Random();
    private static final Random definidorLocal = new Random();
    
    public Simulacao(boolean t)
    {
        filaCaixa = new QueueLinked<Cliente>();
        filaGerente = new StackArray<Cliente>();
        filaPrioritaria = new QueueLinked<Cliente>();
        caixa1 = new Caixa();
        
        caixa2 = new Caixa();
        caixaPrioritaria = new Caixa();
        gerente1 = new Gerente();
        geradorClientes = new GeradorClientes(probabilidadeChegada);
        statTemposEsperaFila = new Acumulador();
        statComprimentosFila = new Acumulador();
        statTemposEsperaFilaGerente = new Acumulador();
        statTemposEsperaFilaPrioritaria = new Acumulador();
        statComprimentosFilaPrioritaria = new Acumulador();
        statComprimentosFilaGerente = new Acumulador();
        trace = t;
    }
    
    public void simular()
    {
        //realizar a simulacao por um certo numero de passos de duracao
        for(int tempo=0; tempo<duracao; tempo++)
        {
            //verificar se um cliente chegou
            if(geradorClientes.gerar())
            {
                //se cliente chegou, criar um cliente e inserir na fila do caixa
                Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(),tempo);
                    if(definidorLocal.nextBoolean()==true){
                        if(geradorIdade.nextDouble()*90 > 60.0){
                        filaPrioritaria.enqueue(c);
                        }
                        else{
                        filaCaixa.enqueue(c);
                        }
                        if(trace)
                        System.out.println(tempo + ": cliente " + c.getNumero() + " ("+c.getTempoAtendimento()+" min) entra na fila - " + filaCaixa.size() + " pessoa(s)");
                    }
                    else{
                        filaGerente.push(c);
                    }    
            }
            //verificar se o caixa esta vazio
            if(caixa1.estaVazio())
            {
                //se o caixa esta vazio, atender o primeiro cliente da fila se ele existir
                if(!filaCaixa.isEmpty() && (filaPrioritaria.size()<=5))
                {
                    //tirar o cliente do inicio da fila e atender no caixa
                    
                    caixa1.atenderNovoCliente(filaCaixa.dequeue());
                    statTemposEsperaFila.adicionar(tempo - caixa1.getClienteAtual().getInstanteChegada());
                    
                                                           
                  if(trace)
                        System.out.println(tempo + ": cliente " + caixa1.getClienteAtual().getNumero() + " chega ao caixa.");  
                }
                else{
                    if(!filaPrioritaria.isEmpty()){
                    caixa1.atenderNovoCliente(filaPrioritaria.dequeue());
                    statTemposEsperaFilaPrioritaria.adicionar(tempo - caixa1.getClienteAtual().getInstanteChegada()); 
                    
                      if(trace)
                        System.out.println(tempo + ": cliente " + caixa1.getClienteAtual().getNumero() + " chega ao caixa.");
                    }
                    
                }
                
            }
            else
            {
                //se o caixa ja esta ocupado, diminuir de um em um o tempo de atendimento ate chegar a zero
                if(caixa1.getClienteAtual().getTempoAtendimento() == 0)
                {
                    if(trace)
                        System.out.println(tempo + ": cliente " + caixa1.getClienteAtual().getNumero() + " deixa o caixa.");
                    caixa1.dispensarClienteAtual();
                }
                else
                {
                    caixa1.getClienteAtual().decrementarTempoAtendimento();
                }
            }
            statComprimentosFila.adicionar(filaCaixa.size());
            
                       
            if(gerente1.estaVazio())
            {
                //se o caixa esta vazio, atender o primeiro cliente da fila se ele existir
                if(!filaGerente.isEmpty())
                {
                    //tirar o cliente do inicio da fila e atender no caixa
                   
                    gerente1.atenderNovoCliente(filaGerente.pop());
                    statTemposEsperaFilaGerente.adicionar(tempo - gerente1.getClienteAtual().getInstanteChegada());
                              
                    if(trace)
                        System.out.println(tempo + ": cliente " + gerente1.getClienteAtual().getNumero() + " chega ao caixa.");
               
                }
                
            }
            else
            {
                //se o caixa ja esta ocupado, diminuir de um em um o tempo de atendimento ate chegar a zero
                if(gerente1.getClienteAtual().getTempoAtendimento() == 0)
                {
                    if(trace)
                        System.out.println(tempo + ": cliente " + gerente1.getClienteAtual().getNumero() + " deixa o caixa.");
                    gerente1.dispensarClienteAtual();
                }
                else
                {
                    gerente1.getClienteAtual().decrementarTempoAtendimento();
                }
            }
            statComprimentosFilaGerente.adicionar(filaGerente.size());
            
                                   
            if(caixaPrioritaria.estaVazio())
            {
                //se o caixa esta vazio, atender o primeiro cliente da fila se ele existir
                if(!filaPrioritaria.isEmpty())
                {
                    //tirar o cliente do inicio da fila e atender no caixa
                    
                    caixaPrioritaria.atenderNovoCliente(filaPrioritaria.dequeue());
                    statTemposEsperaFilaPrioritaria.adicionar(tempo - caixaPrioritaria.getClienteAtual().getInstanteChegada()); 
                                  
                    if(trace)
                        System.out.println(tempo + ": cliente " + caixaPrioritaria.getClienteAtual().getNumero() + " chega ao caixa.");
                }
                else{
                    if(!filaCaixa.isEmpty()){
                        caixaPrioritaria.atenderNovoCliente(filaCaixa.dequeue());
                    statTemposEsperaFila.adicionar(tempo - caixaPrioritaria.getClienteAtual().getInstanteChegada());
                    }
                }
            }
            else
            {
                //se o caixa ja esta ocupado, diminuir de um em um o tempo de atendimento ate chegar a zero
                if(caixaPrioritaria.getClienteAtual().getTempoAtendimento() == 0)
                {
                    if(trace)
                        System.out.println(tempo + ": cliente " + caixaPrioritaria.getClienteAtual().getNumero() + " deixa o caixa.");
                    caixaPrioritaria.dispensarClienteAtual();
                }
                else
                {
                    caixaPrioritaria.getClienteAtual().decrementarTempoAtendimento();
                }
            }
            statComprimentosFilaPrioritaria.adicionar(filaPrioritaria.size());
        }
    }
    
    public void limpar()
    {
        filaCaixa = new QueueLinked<Cliente>();
        filaPrioritaria = new QueueLinked<Cliente>();
        filaGerente = new StackArray<Cliente>();
        caixa1 = new Caixa();
        gerente1 = new Gerente();
        caixaPrioritaria = new Caixa();
        geradorClientes = new GeradorClientes(probabilidadeChegada);
        statTemposEsperaFila = new Acumulador();
        statComprimentosFila = new Acumulador();
        statComprimentosFilaGerente = new Acumulador();
        statTemposEsperaFilaPrioritaria = new Acumulador();
        statComprimentosFilaPrioritaria = new Acumulador();
        statComprimentosFilaGerente = new Acumulador();
    }
    
    public void imprimirResultados()
    {
        System.out.println();
        System.out.println("Resultados da Simulacao");
        System.out.println("Duracao:" + duracao);
        System.out.println("Probabilidade de chegada de clientes:" + probabilidadeChegada);
        System.out.println("Tempo de atendimento minimo:" + Cliente.tempoMinAtendimento);
        System.out.println("Tempo de atendimento maximo:" + Cliente.tempoMaxAtendimento);
        System.out.println("Cliente atendidos no caixa 1:" + caixa1.getNumeroAtendidos());
        System.out.println("Cliente atendidos no gerente:" + gerente1.getNumeroAtendidos());
        System.out.println("Cliente atendidos no Caixa Prioritaria:" + caixaPrioritaria.getNumeroAtendidos());
        System.out.println("Clientes ainda na fila:" + filaCaixa.size());
        System.out.println("Clientes ainda na fila Prioritaria:" + filaPrioritaria.size());
        System.out.println("Clientes ainda na fila Gerente:" + filaGerente.size());
        System.out.println("Cliente ainda no caixa 1:" + (caixa1.getClienteAtual() != null));
        System.out.println("Cliente ainda no Gerente:" + (gerente1.getClienteAtual() != null));
        System.out.println("Cliente ainda no caixa Prioritario:" + (caixaPrioritaria.getClienteAtual() != null));
        System.out.println("Total de clientes gerados:" + geradorClientes.getQuantidadeGerada());
        System.out.println("Tempo medio de espera na fila do Caixa:" + statTemposEsperaFila.getMedia());
        System.out.println("Comprimento medio da fila do Caixa:" + statComprimentosFila.getMedia());
        System.out.println("Tempo medio de espera Prioritaria:" + statTemposEsperaFilaPrioritaria.getMedia());
        System.out.println("Comprimento medio da fila Prioritaria:" + statComprimentosFilaPrioritaria.getMedia());
        System.out.println("Tempo medio de espera na fila do Gerente:" + statTemposEsperaFilaGerente.getMedia());
        System.out.println("Comprimento medio da fila de Gerente:" + statComprimentosFilaGerente.getMedia());
    }
}

