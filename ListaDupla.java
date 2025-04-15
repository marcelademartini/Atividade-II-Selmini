class ListaDupla<T> {
    private NoLista<T> inicio;
    private NoLista<T> fim;

    public void inserir(T valor) {
        NoLista<T> novo = new NoLista<>(valor);
        if (inicio == null) {
            inicio = fim = novo;
        } else {
            fim.setProximo(novo);
            novo.setAnterior(fim);
            fim = novo;
        }
    }

    public NoLista<T> getInicio() {
        return inicio;
    }
}

