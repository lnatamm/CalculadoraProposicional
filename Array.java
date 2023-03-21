public class Array {
    private char[] v;
    private int cont;
    public Array(){
        this.v = new char[10];
        this.cont = 0;
    }
    private void createEspace(){
        char[] auxV = this.v;
        this.v = new char[v.length + v.length / 2];
        for(int i = 0; i < auxV.length; i++){
            this.v[i] = auxV[i];
        }
    }
    public void add(char n){
        try {
            this.v[cont] = n;
            cont++;
        }
        catch (IndexOutOfBoundsException e){
            createEspace();
            add(n);
        }
    }
    public void add(int i, char n){
        try{
            for(int j = cont; j > i; j--){
                this.v[j] = this.v[j - 1];
            }
            this.v[i] = n;
            this.cont++;
        }
        catch (IndexOutOfBoundsException e){
            createEspace();
            add(i, n);
        }
    }
    public int size(){
        return this.cont;
    }
    public void clear(){
        this.cont = 0;
    }
    public boolean contains(char n){
        for(int i = 0; i < this.cont; i ++){
            if(this.v[i] == n){
                return true;
            }
        }
        return false;
    }
    public void remove(char n){
        try{
            for(int i = 0; i < cont; i++){
                if(v[i] == n){
                    v[i] = v[i + 1];
                }
                cont--;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
        }
    }
    public void remove(int i){
        try {
            for (int j = i; j < this.cont; j++) {
                this.v[j] = this.v[j + 1];
            }
            this.cont--;
        }
        catch (ArrayIndexOutOfBoundsException e){
        }
    }
    public char get(int i){
        if(!(i >= cont)) {
            return this.v[i];
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    public int indexOf(char n){
        for(int i = 0; i < this.cont; i ++){
            if(this.v[i] == n){
                return i;
            }
        }
        return -1;
    }
    @Override
    public String toString(){
        String string = "";
        for(int i = 0; i < this.cont; i++){
            string += v[i] + " ";
        }
        return string;
    }
}