package br.com.ekan.avaliacao.model.enums;


import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;

public enum TipoDocumento {
    CNPJ,
    CPF,
    CNH,
    RG;

    public static TipoDocumento toEnum(String value) throws ObjectNotFoundException {
        for (TipoDocumento tipoDocumento : TipoDocumento.values()) {
            if (tipoDocumento.name().equals(value)) {
                return tipoDocumento;
            }
        }

        throw new ObjectNotFoundException("Tipo de documento inválido: " + value);
    }
}
