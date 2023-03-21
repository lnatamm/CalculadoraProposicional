import java.util.*;

public class Main {

    private static int input(Scanner sc){

        try{

            return Integer.parseInt(sc.nextLine());

        }

        catch (NumberFormatException e){

            return input(sc);

        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Bem Vindo a Calculadora Proposicional! Aqui você poderá analisar fórmulas e descobrir se elas são Tautologias, Contigências ou Contradições");

        boolean erro = true;

        while (erro) {

            int controle = 1;

            int tabela = 0;

            while (controle == 1) {

                try {

                    System.out.println("Digite a fórmula: (ou: ∨ | e: ∧ | se: → | se, somente se: ↔)");

                    String formula = sc.nextLine();

                    Validacao validacao = new Validacao(formula);

                    Calculadora calculadora = new Calculadora(validacao);

                    System.out.println();

                    System.out.println("O que deseja fazer? (1 - Definir os valores das proposições | !1 - Gerar a tabela verdade");

                    tabela = input(sc);

                    if(tabela == 1){

                        calculadora.calcular();

                        System.out.println();

                        System.out.println("A fórmula " + formula + " é " + calculadora.getResposta());

                        System.out.println();

                        System.out.println("Esta fórmula é " + calculadora.tabelaVerdade());

                    }

                    else {

                        System.out.println();

                        System.out.println("Esta fórmula é uma " + calculadora.tabelaVerdade());

                    }

                    System.out.println();

                    System.out.println("Deseja continuar? Sim(1) Não(!1)");

                    controle = input(sc);

                    erro = false;

                } catch (InputMismatchException e) {

                    System.out.println(e.getMessage());

                    erro = true;

                }

            }

        }

    }

}