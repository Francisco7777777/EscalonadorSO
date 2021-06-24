package escalonador;

import enumConfig.EnumEstado;
import enumConfig.EnumTipo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processo extends Thread {

    public int pc = 0;                           
    public String nome;
    public EnumEstado estado = EnumEstado.PRONTO;
    public EnumTipo tipo;
    public int qtdePc;
    public static Escalonador escalonador;
    public int quantum;                         

    public Processo(String nome, EnumTipo tipo, int qtdePc, int quantum) { 
        this.nome = nome;
        this.tipo = tipo;
        this.qtdePc = qtdePc;
        this.quantum = quantum;
    }

    @Override
    public void run() {
        
        while (this.qtdePc > this.pc) {
            try {
               
                if (this.estado.equals(EnumEstado.EXECUTANDO)) {
                        
                        for (int i = 0; i < this.quantum && this.qtdePc > this.pc; i++) {
                            this.pc++;
                        }
                    this.sleep(50);
                    escalonador.escalonarProcesso();
                
                } else {   
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
        return this.nome + "(" + this.pc + "/" + this.qtdePc + ")";
    }

}
