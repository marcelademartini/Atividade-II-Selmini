import javax.swing.JOptionPane;

public class Main {
    static ListaDupla<Cidade> cidades = new ListaDupla<>();

    public static void main(String[] args) {
        String[] opcoes = {
            "1. Cadastrar cidades",
            "2. Cadastrar ligações",
            "3. Listar cidades e ligações",
            "4. Verificar ligação e tempo",
            "5. Buscar por tempo limite",
            "6. Sair"
        };

        while (true) {
            String opcao = (String) JOptionPane.showInputDialog(null, "Escolha uma opção:",
                "Menu", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

            if (opcao == null || opcao.equals(opcoes[5])) break;

            switch (opcao) {
                case "1. Cadastrar cidades":
                    String nomeCidade = JOptionPane.showInputDialog("Nome da cidade:");
                    cidades.inserir(new Cidade(nomeCidade));
                    break;

                case "2. Cadastrar ligações":
                    String origem = JOptionPane.showInputDialog("Cidade de origem:");
                    Cidade cidadeOrigem = buscarCidade(origem);
                    if (cidadeOrigem == null) {
                        JOptionPane.showMessageDialog(null, "Cidade de origem não encontrada.");
                        break;
                    }
                    String destino = JOptionPane.showInputDialog("Cidade de destino:");
                    double distancia = Double.parseDouble(JOptionPane.showInputDialog("Distância (km):"));
                    double trafego = Double.parseDouble(JOptionPane.showInputDialog("Fator de tráfego (0 a 2):"));
                    int pedagios = Integer.parseInt(JOptionPane.showInputDialog("Número de pedágios:"));
                    Ligacao ligacao = new Ligacao(destino, distancia, trafego, pedagios);
                    cidadeOrigem.getLigacoes().inserir(ligacao);
                    break;

                case "3. Listar cidades e ligações":
                    StringBuilder sb = new StringBuilder();
                    for (NoLista<Cidade> no = cidades.getInicio(); no != null; no = no.getProximo()) {
                        Cidade cid = no.getInfo();
                        sb.append("Cidade: ").append(cid.getNome()).append("\n");
                        for (NoLista<Ligacao> lig = cid.getLigacoes().getInicio(); lig != null; lig = lig.getProximo()) {
                            sb.append(" → ").append(lig.getInfo()).append("\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                    break;

                case "4. Verificar ligação e tempo":
                    String origemBusca = JOptionPane.showInputDialog("Cidade de origem:");
                    String destinoBusca = JOptionPane.showInputDialog("Cidade de destino:");
                    Cidade cidOrigem = buscarCidade(origemBusca);
                    boolean encontrada = false;
                    if (cidOrigem != null) {
                        for (NoLista<Ligacao> lig = cidOrigem.getLigacoes().getInicio(); lig != null; lig = lig.getProximo()) {
                            Ligacao l = lig.getInfo();
                            if (l.getDestino().equalsIgnoreCase(destinoBusca)) {
                                JOptionPane.showMessageDialog(null, "Ligação encontrada. Tempo estimado: " + l.calcularTempo() + " min");
                                encontrada = true;
                                break;
                            }
                        }
                    }
                    if (!encontrada) JOptionPane.showMessageDialog(null, "Ligação não encontrada.");
                    break;

                case "5. Buscar por tempo limite":
                    double tempoLimite = Double.parseDouble(JOptionPane.showInputDialog("Tempo limite (minutos):"));
                    StringBuilder sbTempo = new StringBuilder();
                    for (NoLista<Cidade> no = cidades.getInicio(); no != null; no = no.getProximo()) {
                        Cidade c = no.getInfo();
                        for (NoLista<Ligacao> lig = c.getLigacoes().getInicio(); lig != null; lig = lig.getProximo()) {
                            Ligacao l = lig.getInfo();
                            if (l.calcularTempo() <= tempoLimite) {
                                sbTempo.append(c.getNome()).append(" → ").append(l.getDestino()).append(" (").append(l.calcularTempo()).append(" min)\n");
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, sbTempo.toString());
                    break;
            }
        }
    }

    private static Cidade buscarCidade(String nome) {
        for (NoLista<Cidade> no = cidades.getInicio(); no != null; no = no.getProximo()) {
            if (no.getInfo().getNome().equalsIgnoreCase(nome)) return no.getInfo();
        }
        return null;
    }
}

