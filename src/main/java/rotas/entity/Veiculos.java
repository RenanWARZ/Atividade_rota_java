package rotas.entity;

import lombok.Getter;
import lombok.Setter;
import rotas.entity.num.StatusVeiculos;

import java.math.BigDecimal;

public abstract class Veiculos {
    @Getter
    private String placa;
    @Getter @Setter
    private String modelo;
    @Getter
    private Integer anoFabricacao;
    @Getter @Setter
    private Integer kmAtual;
    @Getter @Setter
    private StatusVeiculos status;
    @Setter
    private BigDecimal seguro;
    @Getter
    @Setter
    private BigDecimal valorMercado;

    public BigDecimal getSeguro() {
        return calcularSeguro();
    }

    public Veiculos(String placa, String modelo, Integer anoFabricacao, Integer kmAtual, BigDecimal valorMercado) {
        this.placa = placa;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.kmAtual = kmAtual;
        this.valorMercado = valorMercado;
        this.status = StatusVeiculos.DISPONIVEL;
    }

    public String registrarRota(Integer kmRota){
        if(this.getStatus() == StatusVeiculos.MANUTENCAO) {
            return "Erro: Veículo em manutenção. Rota não permitida.";
        }

        if(kmRota <= 0) return "O valor da rota deve ser maior que 0";

        Integer ultimoKm = getKmAtual();
        setKmAtual(getKmAtual() + kmRota);

        String retornoRevisao = validarRevisao(ultimoKm);

        if(retornoRevisao != null) {
            return "Rota registrada. ATENÇÃO: " + retornoRevisao;
        }

        setStatus(StatusVeiculos.EM_ROTA);
        return "Rota registrada com sucesso.";
    }

    public abstract String validarRevisao(Integer ultimoKm);

    public abstract BigDecimal calcularSeguro();

    public abstract void realizarManutencao();
}