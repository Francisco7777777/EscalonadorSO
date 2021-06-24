package escalonador;

import enumConfig.EnumEstado;
import enumConfig.EnumTipo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processo extends Thread {

    public int pc = 0;                           //Armasena as informaçoes processadas de quada processo.
    public String nome;
    public EnumEstado estado = EnumEstado.PRONTO;
    public EnumTipo tipo;
    public int qtdePc;
    public static Escalonador escalonador;
    public int quantum;                         //Intervalo de tempo entre cada interupição.

    public Processo(String nome, EnumTipo tipo, int qtdePc, int quantum) {  //Classe construtora.
        this.nome = nome;
        this.tipo = tipo;
        this.qtdePc = qtdePc;
        this.quantum = quantum;
    }

    @Override
    public void run() {
        //Enquanto a quatidade de atividade do processo for mair do que as atividade processadas.
        while (this.qtdePc > this.pc) {
            try {
                //Se o estado do processo escalonado for "EXECULTANDO".
                if (this.estado.equals(EnumEstado.EXECUTANDO)) {
                        
                        /*Equanto o valor da variavel(i) for menor que a que o valor da variavel(quantum)
                        é a quantidade de tarefas for maior as  que as informações processadada */
                        for (int i = 0; i < this.quantum && this.qtdePc > this.pc; i++) {
                            this.pc++;
                        }
                    this.sleep(50);
                    escalonador.escalonarProcesso(); //O escalonador escalona um processo.
                
                } else {   //um processo e imterompido é o escalonador escalona um processo no topo da fila. 
                    synchronized (this) {
                        wait();
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        
        //Retorna o nome do processo, quantidade de "pc", é "qtdePc" 
        return this.nome + "(" + this.pc + "/" + this.qtdePc + ")";
    }

}
