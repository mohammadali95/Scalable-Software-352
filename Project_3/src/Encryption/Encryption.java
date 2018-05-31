package Encryption;

import Data.Tuple;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Encryption {
    //Modified code from https://gist.github.com/SimoneStefani/99052e8ce0550eb7725ca8681e4225c5
    private static final String ALGO = "AES";
    public static Tuple<String> encryptTuple(Tuple<String> tuple, String masterPassword) throws Exception {

        //Thing
        String username = tuple.getLabel();
        String password = tuple.getPassword();

        username = encrypt(username, masterPassword);
        password = encrypt(password,masterPassword);

        Tuple output = new Tuple(username, password);
        //Hopefully this will GC our passwords so someone can't just read our memory as easily.
        return output;

    }
    public static Tuple<String> decryptTuple(Tuple<String> tuple, String masterPassword) throws WrongPasswordException {


            String username = tuple.getLabel();
            String password = tuple.getPassword();
            Tuple output;
            try {
                username = decrypt(username, masterPassword).get();
                password = decrypt(password, masterPassword).get();

                 output = new Tuple(username, password);
            }catch (NoSuchElementException e){
                throw new WrongPasswordException();
            }
            return output;
    }
    public static String encrypt(String data, String masterPassword) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoMasterPasswordException {
      if(masterPassword==null) throw new NoMasterPasswordException();
        Key key = generateKey(masterPassword);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());

        System.gc();
        return Base64.getEncoder().encodeToString(encVal);
    }
    public static Optional<String> decrypt(String encryptedData, String masterPassword) throws WrongPasswordException {
        try {
            Key key = generateKey(masterPassword);
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decValue = c.doFinal(decodedValue);
            String outputDecoded = new String(decValue);
            System.gc();
            return Optional.of(outputDecoded);
            //We shouldn't get these, so it's fine to catch these here.
        }catch (BadPaddingException e){
            throw new WrongPasswordException();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static Key generateKey(String masterPassword) {
        int numberSpaces = 16-masterPassword.length();
        for(int i=0;i<numberSpaces;i++){
            masterPassword+=" ";
        }
        byte[] keyValue = masterPassword.getBytes();
        System.gc();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyValue, ALGO);
        return new SecretKeySpec(keyValue, ALGO);
    }

}
