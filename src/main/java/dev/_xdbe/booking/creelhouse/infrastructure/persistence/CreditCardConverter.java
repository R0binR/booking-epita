package dev._xdbe.booking.creelhouse.infrastructure.persistence;

import dev._xdbe.booking.creelhouse.infrastructure.persistence.CryptographyHelper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;

@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {

  @Autowired private CryptographyHelper cryptographyHelper;

  @Override
  public String convertToDatabaseColumn(String attribute) {
    // Step 7a: Encrypt the PAN before storing it in the database
    return attribute;
    // Step 7a: End of PAN encryption
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    // Step 7b: Decrypt the PAN when reading it from the database
    String pan = dbData;
    // Step 7b: End of PAN decryption
    String maskedPanString = panMasking(pan);
    return maskedPanString;
  }

  private String panMasking(String pan) {
    // Step 6:
    if (pan == null) {
      return null;
    }

    int length = pan.length();
    if (length <= 8) {
      return pan; // rien a masquer entre first4 et last4
    }

    String first4 = pan.substring(0, 4);
    String last4 = pan.substring(length - 4);
    String middleMasked = "*".repeat(length - 8);

    return first4 + middleMasked + last4;
    // Step 6: End
  }
}
