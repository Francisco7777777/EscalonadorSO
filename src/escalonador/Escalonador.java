package escalonador;

import enumConfig.EnumEstado;
import enumConfig.EnumTipo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Escalonador {

    public static List<Processo> prontos = new ArrayList<>();               //lista de processos prontos.
    public static List<Processo> bloqueados = new ArrayList<>();          //lista de processos bloqueados.
    public static List<Processo> finalizados = new ArrayList<>();         //lista de processos bloqueados.
    public static Processo executando;
    public static int qtdeProcesso;                                       //Quantidade de processo.
    public static int qtdeEscalonamento = 0;                              /*Quantidade de escalonamento
                                                                            iniciacom valor 0.          */
   
    //O metodo imprime o status da lista de processo.
    public static void imprimeStatusListas() {                       
        System.out.println("--------------- ESCALONAMENTO "+qtdeEscalonamento+" ---------------");
        System.out.println("PRONTOS: " + prontos.size());                     //a quantidade de processos
        for (Processo pronto : prontos) {
            System.out.print(" | " + pronto.toString());                     //imprime os processos proto.
        }
        System.out.println("\nBLOQUEADOS: " + bloqueados.size());
        for (Processo bloqueado : bloqueados) {
            System.out.print(" | " + bloqueado.toString());              //imprime os processos bloqueados
        }
        System.out.println("\nFINALIZADOS: " + finalizados.size());
        for (Processo finalizado : finalizados) {
            System.out.print(" | " + finalizado.toString());            //imprime os processos finalisados
        }
        if (executando != null) {
            System.out.println("\nEXECUTANDO: " + executando.toString()); //imprime o processo em execução
        }else{
           System.out.println("\nEXECUTANDO: null"); 
        }
        System.out.println("\n");
    }
    //metodo bloqueado topo pronto
    public static void bloqueadoToPronto() {        
            Processo p = bloqueados.get(0);
            p.estado = EnumEstado.PRONTO;            
            prontos.add(p);
            bloqueados.remove(0);
    }
    
    //mmetodo execultando topo bloqueado
    public static void executandoToBloqueado() {
        executando.estado = EnumEstado.BLOQUEADO;
        bloqueados.add(executando);
    }
    
    //metodo pronto topo execultando
    public static void prontoToExetucando() {
        executando = prontos.get(0);
        executando.estado = EnumEstado.EXECUTANDO;
        prontos.remove(prontos.get(0));
    }
    
    //metodo execultando topo pronto
    public static void executandoToPronto() {
        executando.estado = EnumEstado.PRONTO;
        prontos.add(executando);
    }
    
    //metodo execultando topo finalizado
    public static void executandoToFinalizado() {
        executando.estado = EnumEstado.FINALIZADO;
        finalizados.add(executando);
    }
    //Metodo escalonar processo.
    public synchronized void escalonarProcesso() {
        if (this.executando != null) {
            if (executando.pc >= executando.qtdePc) {
                executandoToFinalizado();
            } else if (executando.tipo.equals(EnumTipo.CPU)) {
                this.executandoToPronto();
            } else {
                this.executandoToBloqueado();
            }
        }

        // Zerando o executando
        executando = null;

        // Restaurando bloqueados
        while (bloqueados.size() > 0) {
            bloqueadoToPronto();
        }

        if (prontos.size() > 0) {
            prontoToExetucando();
            qtdeEscalonamento++;
            imprimeStatusListas();
            try {
                synchronized (executando) {
                    executando.notifyAll();
                }
            } catch (Exception e) {
            }
        }
    }
}
