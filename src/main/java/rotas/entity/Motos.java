package rotas.entity;

import rotas.entity.num.StatusVeiculos;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Motos extends Veiculos {
    @Getter @Setter
    private Integer cilindradas;

    public Motos(String placa, String modelo, Integer anoFabricacao, Integer kmAtual, BigDecimal valorMercado, Integer cilindradas) {
        super(placa, modelo, anoFabricacao, kmAtual, valorMercado);
        this.cilindradas = cilindradas;
    }

    @Override
    public String validarRevisao(Integer ultimoKm) {
        int cicloAtual = getKmAtual() / 3000;
        int cicloAntigo = ultimoKm / 3000;

        if(cicloAtual > cicloAntigo){
            setStatus(StatusVeiculos.MANUTENCAO);
            return "Manutenção Necessária";
        }

        return null;
    }

    @Override
    public BigDecimal calcularSeguro() {
        return getValorMercado().multiply(new BigDecimal("0.05"));
    }

    @Override
    public void realizarManutencao() {
        setStatus(StatusVeiculos.DISPONIVEL);
    }
}