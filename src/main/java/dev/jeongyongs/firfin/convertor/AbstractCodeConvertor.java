package dev.jeongyongs.firfin.convertor;

import dev.jeongyongs.firfin.domain.Code;
import dev.jeongyongs.firfin.exception.InvalidCodeException;
import java.util.Arrays;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public abstract class AbstractCodeConvertor<E extends Enum<E> & Code> implements AttributeConverter<E, Integer> {

    private final Class<E> enumType;

    protected AbstractCodeConvertor(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public Integer convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(Integer dbData) {
        return Arrays.stream(enumType.getEnumConstants())
                     .filter(status -> status.getCode() == dbData)
                     .findFirst()
                     .orElseThrow(() -> new InvalidCodeException(dbData));
    }
}
