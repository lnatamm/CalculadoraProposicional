import java.util.*;

public class Calculadora {
    private Validacao validacao; //Importa a classe Validação
    private ArrayList<Character> formula; //Cria uma lista de Caracteres
    private ArrayList<Proposicao> proposicoes; //Cria uma lista de Proposições(classe)
    private String resposta; //Cria uma variável de String
    public Calculadora(Validacao validacao) {
        this.validacao = validacao;
        if(validacao.isFbf() && validacao.verificaConectivos()) {
            defineFormula();
            recolheProposicoes();
        }
        else {
            throw new InputMismatchException("A fórmula não é uma FBF!");
        }
    }
    public ArrayList<Character> getFormula() {
        return formula;
    }
    public String getResposta() {
        return resposta;
    }
    public ArrayList<Proposicao> getProposicoes() {
        return proposicoes;
    }
    private boolean isAnd(Character c){
        Character and = '∧';  //Cria o Caractere ''e''
        return c.equals(and); //Retorna se o c é igual ao ''and'' ou não
    }
    private boolean isOr(Character c){
        Character or = '∨'; //Cria o Caractere ''ou''
        return c.equals(or); //Retorna se o c é igual ao ''ou'' ou não
    }
    private boolean isIf(Character c){
        Character If = '→'; //Cria o Caractere ''se então''
        return c.equals(If); //Retorna se o c é igual ao ''se'' ou não
    }
    private boolean isBiIf(Character c){
        Character biIf = '↔'; //Cria o Caractere ''Se somente se''
        return c.equals(biIf); //Retorna se o c é igual ao ''Se somente se'' ou não
    }
    private boolean isParentese(Character c) {
        ArrayList<Character> parentese = new ArrayList<>();
        parentese.add('(');
        parentese.add(')');
        for(Character i : parentese) {
            if(i.equals(c)) {
                return true;
            }
        }
        return false;
    }
    private boolean isAberto(Character c) {
        Character abertoParentese = '(';
        return c.equals(abertoParentese);
    }
    private boolean isFechado(Character c) {
        Character fechadoParentese = ')';
        return c.equals(fechadoParentese);
    }
    private boolean isConective(Character c) {
        ArrayList<Character> operacoes = new ArrayList<>();
        operacoes.add('∧');
        operacoes.add('∨');
        operacoes.add('∼');
        operacoes.add('→');
        operacoes.add('↔');
        for(Character i : operacoes) {
            if(i.equals(c)) {
                return true;
            }
        }
        return false;
    }
    private boolean isNegador(Character c) {
        Character negador = '~';
        return c.equals(negador);
    }
    private boolean isOperador(Character c) { //Verifica se o caractere passado é um operador ou não
        ArrayList<Character> operacoes = new ArrayList<>(); //Cria a relação de operadores
        operacoes.add('∧');
        operacoes.add('∨');
        operacoes.add('→');
        operacoes.add('↔');
        for(Character i : operacoes) { //Para cada caractere em opereções, faça isso
            if(i.equals(c)) { //Verifica se o caractere passado é igual a algum dos operadores da relação
                return true;
            }
        }
        return false; //Caso não tenha retornado true em nenhum dos casos, retorna false
    }
    private boolean translateDigit(Character c){ //Traduzir os parenteses simplificados
        Character zero = '0'; //Cria um Caractere
        return !c.equals(zero); //Retorna se ele não for igual a 0

    }
    private void defineFormula(){
        formula = new ArrayList<>();
        for(int i = 0; i < validacao.getCaracteres().size(); i++){ //Percorre o array do tamanho de caracteres da validacao
            formula.add(validacao.getCaracteres().get(i)); //Adiciona cada elemento na formula
        }
    }
    private void recolheProposicoes(){
        ArrayList<Character> letras = new ArrayList<>(); //cria uma lista de Caractere
        proposicoes = new ArrayList<>();
        for(Character i : formula){ //Percorre cada caractere da formula e atrubui seu valor a i
            if(Character.isLetter(i)){ //Verifica se o caractere é uma letra
                if(!letras.contains(i)){ //Verifica se a letra é repetida
                    letras.add(i); //Se não for, adiciona a letra
                }
            }
        }
        for(Character i : letras){ //Percorre cada letra da lista
            proposicoes.add(new Proposicao(i)); //Adiciona a lista de proposições novas proposições com o nome da letra
        }
    }
    private void defineProposicoes(){
        try {
            Scanner sc = new Scanner(System.in);
            for(Proposicao i : proposicoes){ //Percorre cada proposições da Proposição
                System.out.print(i.getNome() + ": "); //Imprime o nome da proposição
                String s = sc.nextLine();
                if(s.equals("V") || s.equals("v") || s.equals("F") || s.equals("f")){ //Verifica se o valor salvo é V ou F
                    i.setValor(s.equals("V") || s.equals("v")); //Se for igual a verdadeiro, seta o valor da proposição como true, caso contrário, seta como false
                }
                else {
                    throw new InputMismatchException("Insira apenas valores 'V' ou 'true' ou 'F' ou 'false'!"); //Se não, ele joga uma exceção
                }
            }
        }
        catch (InputMismatchException e){
            System.out.println(e.getMessage()); //Imprime a mensagem da excessão
            defineProposicoes(); //Tenta definir de novo(chamada da própria função)
        }
    }
    private Proposicao getProposicao(Character c){ //Ele retorna a proposição com o mesmo nome do caractere passado
        for(Proposicao i : proposicoes) { //Perccore cada proposição em Proposição
            if(i.getNome().equals(c)){ //Verifica se o nome é igual ao caractere passado
                return i; //Se for, retorna a proposição
            }
        }
        return null; //Se não for, retorna nulo
    }
    public void executaParentese(){
        int comecoP = 0; //Marca o começo do parentese
        int finalP = 0; //Marca o final do parantese
        ArrayList<Character> parentese = new ArrayList<>(); //Guarda todos os caracteres dentro do parentese
        ArrayList<Proposicao> propParentese = new ArrayList<>(); //Guarda todas as proposições dentro do parentese
        ArrayList<Character> opParentese = new ArrayList<>(); //Guarda todos os operadores dentro do parentese
        try{
            for(int i = 0; i < formula.size(); i++){ //Percorre toda a formula, procurando uma abertura de parentese
                if(isAberto(formula.get(i))){ //Se tiver um parentese aberto
                    comecoP = i; //Define o começo do parentese
                }
            }
            finalP = comecoP; //Inicia o final do parentese como o começo do parentese
            while (!isFechado(formula.get(finalP))){ //Enquanto o parentese não for fechado
                finalP++; //Incrementa o final do parentese
            }
            for(int i = comecoP; i < finalP - 1; i++){//Percorre o que está dentro do parentese
                parentese.add(formula.get(i + 1)); //Adiciona os caracteres a lista do parentese
            }
            for(int i = 0; i < parentese.size(); i++){ //Percorre o tamanho do parentese
                if(isNegador(parentese.get(i))){ //Verifica se o caractere atual é um negador
                    Proposicao n; //Cria uma proposição
                    if(Character.isDigit(parentese.get(i + 1))){ //Verifica se o próximo caractere é 0 ou 1(resultado de um outro parenteses)
                        n = new Proposicao(!translateDigit(parentese.get(i + 1))); //Instancia a proposição com o inverso do valor
                    }
                    else {
                        n = new Proposicao(parentese.get(i + 1)); //Se for uma letra, instancia uma nova proposição com o nome dela
                        n.setValor(!(getProposicao(parentese.get(i + 1)).getValor())); //Seta o valor como o inverso dela
                    }
                    propParentese.add(n); //Adiciona a proposição a lista de proposições
                    parentese.remove(i); //Remove o ''~''
                }
                else if(Character.isLetter(parentese.get(i)) || Character.isDigit(parentese.get(i))){ //Verifica se o caractere atual é uma letra ou um dígito
                    if(Character.isLetter(parentese.get(i))){ //Verifica se é uma letra
                        propParentese.add(getProposicao(parentese.get(i))); //Se for, adiciona a lista de proposições a proposição com o mesmo nome do atributo Proposições
                    }
                    else {
                        propParentese.add(new Proposicao(parentese.get(i), translateDigit(parentese.get(i)))); //Se for um dígito(0 ou 1), adiciona uma nova proposição com o valor 0 ou 1
                    }
                }
                else{
                    opParentese.add(parentese.get(i)); //Se não for letra nem dígito, adiciona aos operadores
                }
            }
            for(int i = comecoP; i <= finalP; i++){ //Percorre do começo do parentese ao final
                formula.remove(comecoP); //Remove o parentese inteiro da fórmula original
            }
            boolean resultado; //Cria uma váriavel que armazena o resultado do parentese
            try{
                resultado = operacao(propParentese.get(0).getValor(), opParentese.get(0), propParentese.get(1).getValor());
                //Inicializa o resultado com a operação entre a primeira proposição, o primeiro operador e a segunda proposição
            }
            catch (IndexOutOfBoundsException e){
                resultado = propParentese.get(0).getValor(); //Se só tiver uma proposição, retorna o valor dela própria
            }
            for(int i = 1; i < parentese.size() - 1; i++){ //Percorre os restantes dos caracteres dos parenteses
                try{
                    resultado = operacao(resultado, opParentese.get(i), propParentese.get(i + 1).getValor());
                    //Seta o resultado com a operação entre o resultado anterior e a próxima proposição
                }
                catch (IndexOutOfBoundsException e){
                }
            }
            if(resultado){ //Verifica se o resultado for true
                formula.add(comecoP, '1'); //Adiciona no lugar de onde era o parentese(1)
            }
            else{
                formula.add(comecoP, '0'); //Se não for, adiciona no lugar de onde era o parentese(0)
            }
        }
        catch (IndexOutOfBoundsException e){
        }
    }

    public boolean isTrue() {
        defineFormula(); //Redefine a fórmula para a fórmula original
        int ii = 0; //Inicializa a variável ii com 0
        while (ii < formula.size()){ //Percorre toda a fórmula
            if(isAberto(formula.get(ii))){ //Verifica se ele entrou em um parentese
                executaParentese(); //Executa o parentese(de forma a eliminar todos os parenteses da fórmula)
                ii = 0; //Inicializa a variável com ii com 0 de novo
            }
            else {
                ii++; //Se não, incrementa ii
            }
        }
        ArrayList<Proposicao> proposicoes = new ArrayList<>();
        ArrayList<Character> operadores = new ArrayList<>();
        for(int i = 0; i < formula.size(); i++){ //Percorre o tamanho da fórmula
            if(isNegador(formula.get(i))){ //Verifica se o caractere atual é um negador
                Proposicao n;
                if(Character.isDigit(formula.get(i + 1))){
                    n = new Proposicao(!translateDigit(formula.get(i + 1)));
                }
                else {
                    n = new Proposicao(formula.get(i + 1));
                    n.setValor(!(getProposicao(formula.get(i + 1)).getValor()));
                }
                proposicoes.add(n);
                formula.remove(i);
            }
            else if(Character.isLetter(formula.get(i)) || Character.isDigit(formula.get(i))){
                if(Character.isLetter(formula.get(i))){
                    proposicoes.add(getProposicao(formula.get(i)));
                }
                else {
                    proposicoes.add(new Proposicao(formula.get(i), translateDigit(formula.get(i))));
                }
            }
            else {
                operadores.add(formula.get(i));
            }
        }
        boolean resultado;
        try{
            resultado = operacao(proposicoes.get(0).getValor(), operadores.get(0), proposicoes.get(1).getValor());
        }
        catch (IndexOutOfBoundsException e){
            resultado = proposicoes.get(0).getValor();
        }
        for(int i = 1; i < proposicoes.size() - 1; i++){
            try{
                resultado = operacao(resultado, operadores.get(i), proposicoes.get(i + 1).getValor());
            }
            catch (IndexOutOfBoundsException e){
            }
        }
        return resultado; //Retorna se é true ou false
    }
    public void calcular() {
        defineFormula(); //Redefine a fórmula
        defineProposicoes();
        int ii = 0;
        while (ii < formula.size()){
            if(isAberto(formula.get(ii))){
                executaParentese();
                ii = 0;
            }
            else {
                ii++;
            }
        }
        ArrayList<Proposicao> proposicoes = new ArrayList<>();
        ArrayList<Character> operadores = new ArrayList<>();
        for(int i = 0; i < formula.size(); i++){
            if(isNegador(formula.get(i))){
                Proposicao n;
                if(Character.isDigit(formula.get(i + 1))){
                    n = new Proposicao(!translateDigit(formula.get(i + 1)));
                }
                else {
                    n = new Proposicao(formula.get(i + 1));
                    n.setValor(!(getProposicao(formula.get(i + 1)).getValor()));
                }
                proposicoes.add(n);
                formula.remove(i);
            }
            else if(Character.isLetter(formula.get(i)) || Character.isDigit(formula.get(i))){
                if(Character.isLetter(formula.get(i))){
                    proposicoes.add(getProposicao(formula.get(i)));
                }
                else {
                    proposicoes.add(new Proposicao(formula.get(i), translateDigit(formula.get(i))));
                }
            }
            else {
                operadores.add(formula.get(i));
            }
        }
        boolean resultado;
        try{
            resultado = operacao(proposicoes.get(0).getValor(), operadores.get(0), proposicoes.get(1).getValor());
        }
        catch (IndexOutOfBoundsException e){
            resultado = proposicoes.get(0).getValor();
        }
        for(int i = 1; i < proposicoes.size() - 1; i++){
            try{
                resultado = operacao(resultado, operadores.get(i), proposicoes.get(i + 1).getValor());
            }
            catch (IndexOutOfBoundsException e){
            }
        }
        if(resultado){
            resposta = "Verdadeiro"; //Define a resposta como verdadeiro
        }
        else {
            resposta = "Falso"; //Ou falso
        }
    }
    public boolean operacao(boolean a, Character o, boolean b) { //Realiza a operação entre 2 booleanos
        if(isAnd(o)) { //Se o operador for ''e''
            return a && b; //Retorna o resultado entre a && b
        }
        if(isOr(o)) { //Se o operador for ''ou''
            return a || b; //Retorna o resultado entre a || b
        }
        if(isIf(o)) { //Se o operador for ''Se''
            return !(a && !b); //Retorna falso se o a for verdadeiro e b for falso, caso contrário retorna true
        }
        if(isBiIf(o)) { //Se o operador for o ''Se somente se''
            return a == b; //Retorna true, se eles forem iguais e falso caso contrário
        }
        return false; //Se não for nenhum operador, retorna false
    }


    public String tabelaVerdade() {
        String formula = ""; //Cria uma string que vai armazenar a fórmula
        for(int i = 0; i < validacao.getCaracteres().size(); i++) { //Percorre cada caractere da fórmula original
            formula += validacao.getCaracteres().get(i); //Concatena os caracteres para gerar uma string da fórmula original
        }
        double tamanho = Math.pow(2, proposicoes.size()); //Define o tamanho da tabela
        double troca = tamanho / 2; //Inicializa o primeiro ponto de troca
        int aux = 0; //Variável auxiliar para controlar as trocas entre V e F
        boolean flagTroca = false; //Variável para trocar
        ArrayList<Boolean> relacao; //Conter todos os valores possíveis para cada proposição
        ArrayList<ArrayList<Boolean>> listaProposicoes = new ArrayList<>(); //Todas as proposições e todos os seus valores possíveis
        for(int i = 0; i < proposicoes.size(); i++) { //Percorre o tamanho da proposição
            System.out.print(proposicoes.get(i).getNome() + " "); //Imprime a primeira linha da tabela verdade, com o nome de cada proposição
        }
        System.out.print(formula); //Imprime no final a fórmula
        for(int i = 0; i < proposicoes.size(); i++) { //Perccrre o tamanho das proposições
            relacao = new ArrayList<>(); //Inicializa uma nova relação de todos os valores possíveis
            for(int j = 0; j < tamanho; j++) { //Percorre de 0 até o tamanho da tabela verdade
                if(aux == troca) { //Verifica se é um ponto de troca
                    flagTroca =! flagTroca; //Se for, começa a troca
                    aux = 0; //Reinicia a variável auxiliar
                }
                if(!flagTroca) { //Verifica se não está trocando
                    relacao.add(true); //Adiciona true aos valores possíveis
                } else {
                    relacao.add(false); //Se não, adiciona false aos valores possíveis
                }
                aux++; //Incrementa a variável auxiliar
            }
            aux = 0; //Reinicializa a variável auxiliar como 0
            flagTroca = false; //Reinicializa a flag como false
            listaProposicoes.add(relacao); //Adiciona a lista de todas as proposições a sua relação
            troca = troca / 2; //Inicializa a troca a próxima coluna
        }
        ArrayList<Boolean> valores = new ArrayList<>(); //Resposta de cada combinação de valores
        System.out.println(); //Pula linha
        for(int i = 0; i < tamanho; i++) { //Percorre o tamanho da tabela verdade
            for(int j = 0; j < listaProposicoes.size(); j++) { //Percorre a lista com todas as proposições
                proposicoes.get(j).setValor(listaProposicoes.get(j).get(i)); //Seta o valor de cada proposição
                if(listaProposicoes.get(j).get(i)) { //Verifica se o valor da relação de cada proposição é verdadeiro
                    System.out.print("V "); //Se for, imprime V
                } else {
                    System.out.print("F "); //Se não, imprime F
                }
            } valores.add(isTrue()); //Adiciona a lista de cada combinação com o valor da resposta da combinação atual
            if(valores.get(i)) { //Verifica se a resposta da combinação atual é verdadeira
                System.out.println("V "); //Se for, imprime V
            } else {
                System.out.println("F "); //Se não for, imprime F
            }
        }
        boolean flagTrue = false; //Flag para saber se há algum resultado ''V'' da tabela verdade
        boolean flagFalse = false; //Flag para saber se há algum resultado ''F'' da tabela verdade
        for(Boolean i: valores) { //Percorre cada valor na lista de valores
            if(i) { //Verifica se o valor é verdadeiro
                flagTrue = true; //Se tiver algum, inicializa a flagTrue como verdadeiro
            } else {
                flagFalse = true; ////Se não tiver algum, inicializa a flagFalse como verdadeiro
            }
        }
        if(flagTrue) { //Verifica se tem algum resultado verdadeiro na tabela verdade
            if(flagFalse) { //Verifica se também tem algum resultado false
                return "Contigência"; //Se sim, retorna Contigência
            } else {
                return "Tautologia"; //Se não, retorna Tautologia
            }
        } return "Contradição"; //Se não, retorna Contradição
    }
}