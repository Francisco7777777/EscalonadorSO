package escalonador;

import enumConfig.EnumTipo;

public class Processador {

    public static void main(String[] args) throws InterruptedException {
        
        Escalonador escalonador = new Escalonador();                 
        Processo.escalonador = escalonador;
        Escalonador.qtdeProcesso = 4;                      
        
        Processo p1 = new Processo("NetBeans", EnumTipo.CPU, 4, 2);
        Processo p2 = new Processo("QAcademico", EnumTipo.IO, 6, 2);
        Processo p3 = new Processo("Familydoc", EnumTipo.CPU, 3, 2);
        Processo p4 = new Processo("SOAE", EnumTipo.IO, 4, 2);
     
        Escalonador.prontos.add(p1);
        Escalonador.prontos.add(p2);
        Escalonador.prontos.add(p3);
        Escalonador.prontos.add(p4);
        
        escalonador.escalonarProcesso();

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        
        p1.join();
        p2.join();
        p3.join();
        p4.join();
        
        Escalonador.imprimeStatusListas();

    }
}
