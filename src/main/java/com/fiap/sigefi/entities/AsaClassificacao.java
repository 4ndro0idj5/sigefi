package com.fiap.sigefi.entities;

public enum AsaClassificacao {
    ASA_I(365),
    ASA_II(180),
    ASA_III(90);

    private final int validadeDias;

    AsaClassificacao(int validadeDias) {
        this.validadeDias = validadeDias;
    }

    public int getValidadeDias() {
        return validadeDias;
    }
}
