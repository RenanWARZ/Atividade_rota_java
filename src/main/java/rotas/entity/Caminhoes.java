package rotas.entity;

import lombok.Getter;
import lombok.Setter;
import rotas.entity.num.StatusVeiculos;

import java.math.BigDecimal;

public class Caminhoes extends Veiculos {
    @Getter
    @Setter
    private Integer capacidadeCarga;
    @Getter @Setter
    private Integer eixos;
    @Getter @Setter
    private Double cargaAcumulada = 0.0;

    public Caminhoes(String placa, String modelo, Integer anoFabricacao, Integer kmAtual, BigDecimal valorMercado, Integer capacidadeCarga, Integer eixos) {
        super(placa, modelo, anoFabricacao, kmAtual, valorMercado);
        this.capacidadeCarga = capacidadeCarga;
        this.eixos = eixos;
    }

    public String registrarRota(Integer kmRota, Double cargaViagem) {
        this.cargaAcumulada += cargaViagem;
        return super.registrarRota(kmRota);
    }

    @Override
    public String validarRevisao(Integer ultimoKm) {
        int cicloAtualKm = getKmAtual() / 10000;
        int cicloAntigoKm = ultimoKm / 10000;

        if (cicloAtualKm > cicloAntigoKm || this.cargaAcumulada >= 500) {
            setStatus(StatusVeiculos.MANUTENCAO);
            return "Manutenção de Caminhão Necessária (Km ou Carga excedida)";
        }
        return null;
    }

    @Override
    public BigDecimal calcularSeguro() {
        BigDecimal parteMercado = getValorMercado().multiply(new BigDecimal("0.02"));
        BigDecimal parteCapacidade = new BigDecimal(this.capacidadeCarga).multiply(new BigDecimal("50.00"));
        return parteMercado.add(parteCapacidade);
    }

    @Override
    public void realizarManutencao() {
        setStatus(StatusVeiculos.DISPONIVEL);
        this.cargaAcumulada = 0.0;
    }
}