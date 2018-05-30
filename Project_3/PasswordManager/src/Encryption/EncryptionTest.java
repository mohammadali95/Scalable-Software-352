package Encryption;

import Data.Tuple;
import org.junit.Assert;
import org.junit.Test;

public class EncryptionTest {

    @Test
    public void TestEncryption() throws Exception {
    	Tuple<String> user = new Tuple("Hello", "World");
        Tuple<String> encrypted = Encryption.encryptTuple(user, "A948AB9C57030E9FF7035EFFD4071DAA");
        Tuple<String> decrypted = Encryption.decryptTuple(encrypted, "A948AB9C57030E9FF7035EFFD4071DAA");
        Assert.assertTrue(decrypted.getLabel().equals("Hello"));

    }
    @Test
    public void TestThatWrongPasswordDoesntWork() throws Exception {
        Tuple<String> user = new Tuple<>("Hello", "World");
        Tuple<String> encrypted = Encryption.encryptTuple(user, "TESTTEST");
        try {
            //Bad passwords don't give us jumbled data, they give us padding exceptions.
            Tuple<String> decrypted = Encryption.decryptTuple(encrypted, "TESTTEST"); //
        }catch (WrongPasswordException e){
            Assert.assertTrue(true);
        }
    }
}
