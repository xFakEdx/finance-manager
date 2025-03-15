package de.homebrewed.financemanager.external.persistance.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Currency;

@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {

  @Override
  public String convertToDatabaseColumn(Currency currency) {
    return currency.getCurrencyCode();
  }

  @Override
  public Currency convertToEntityAttribute(String code) {
    return Currency.getInstance(code);
  }
}
