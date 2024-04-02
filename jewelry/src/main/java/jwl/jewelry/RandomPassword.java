/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 18.10.2021 r.
 */
package jwl.jewelry;

import java.security.SecureRandom;

public class RandomPassword {
    final static String alphabet = "0123456789ABCDEFGHIJKLMNOPRSTUWXYZabcdefghijklmnoprstuwxyz!@#$%^&*(){}[]";
    final static int N = alphabet.length();
    static SecureRandom rand = new SecureRandom();
    
    public static String generate(){
        int iLength = (int) (Math.random() * 12 + 9);
        
        StringBuilder sb = new StringBuilder(iLength);
        for (int i = 0; i < iLength; i++) {
             sb.append(alphabet.charAt(rand.nextInt(N)));
        }
    return sb.toString();
    }
}
