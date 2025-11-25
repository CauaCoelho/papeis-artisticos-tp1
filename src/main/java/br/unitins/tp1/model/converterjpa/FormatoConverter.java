package br.unitins.tp1.model.converterjpa;

import br.unitins.tp1.model.Formato;
import jakarta.persistence.AttributeConverter;

public class FormatoConverter implements AttributeConverter<Formato, Long>{

    @Override
    public Long convertToDatabaseColumn(Formato formato) {
        return (formato == null) ? null : formato.ID;
    }

    @Override
    public Formato convertToEntityAttribute(Long id) {
        return Formato.valueOf(id);
    }
    
}
