package br.com.ekan.avaliacao.service;

import br.com.ekan.avaliacao.model.enums.TipoDocumento;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TiposDocumentosService {

    public List<TipoDocumento> listar() {
        return Arrays.asList(TipoDocumento.values());
    }

}
