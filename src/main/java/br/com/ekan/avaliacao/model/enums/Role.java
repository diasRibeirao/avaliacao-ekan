package br.com.ekan.avaliacao.model.enums;

import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public static Role toEnum(String value) throws ObjectNotFoundException {
        for (Role role : Role.values()) {
            if (role.name().equals(value)) {
                return role;
            }
        }

        throw new ObjectNotFoundException("Role inválida: " + value);
    }
}
