package br.unitins.tp1.model.converterjpa;

import br.unitins.tp1.model.Textura;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TexturaConverter implements AttributeConverter<Textura, Long> {


    @Override
    public Textura convertToEntityAttribute(Long id){
        return Textura.valueOf(id);
    }

    @Override
    public Long convertToDatabaseColumn(Textura textura) {
        return (textura == null) ? null : textura.ID;
    }
}
