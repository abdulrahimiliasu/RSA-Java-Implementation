
package RSA;

import java.util.*;
import java.math.BigInteger;

public class Main {

   public static void main(String[] args) {
       
       BigInteger p,q,n,phi,e,d,m,c,dm,dc,j,eHOLD;
       
       Scanner sc =  new Scanner(System.in);
       
       p = Rsa.RandPrime();
       q = Rsa.RandPrime();
       
       n = p.multiply(q);
       phi = Rsa.Phi(p,q);
       
       BigInteger[] odd;
       odd = new BigInteger[100];
       
       j = BigInteger.ONE;
       for(int i=0;i<100;i++)
       {
           j = j.add(BigInteger.TWO);
           odd[i] = j;
       }

       e = BigInteger.ONE;
      for(int i=0;i<100;)
      {
        eHOLD = odd[i];
        if(eHOLD.compareTo(BigInteger.ONE) > 0)
       {
           if(eHOLD.compareTo(phi) < 0)
           {
               if(Rsa.gcd(eHOLD,phi).compareTo(BigInteger.ONE)==0)
               {
                   e = eHOLD;
                   break;
               }
               else
                   i++;
           }  
       }                  
      }
       
       d  =  Rsa.EEA(phi,e);
       
       if(d.compareTo(BigInteger.ZERO) < 0)
           d = d.add(phi);
       
       System.out.println("PublicKey(n, e) = PK("+n+","+e+")");
       System.out.println("SecretKey(d) = SK("+d+")");
       
       //Encryption
       System.out.print("Message:");
       String text = sc.next();
       m = new BigInteger(text.getBytes());
       if(m.compareTo(n) < 0){
           c = Rsa.FME(m,e,n);
           System.out.println(c+" <-- Cipher Text");
       }else{
           System.out.println("Message Bigger Than n!");
            System.exit(0);}
       
       System.out.print("Do You Want to Decrypt? (yes=1/no=0):");
       int dec = sc.nextInt() ;

       if(dec == 1){
       //Decryption
       System.out.print("Cipher:");
       dc = sc.nextBigInteger();
       dm = Rsa.FME(dc,d,n);
       String dcm = new String(dm.toByteArray());
       System.out.println(dcm+" <-- Message");
       }
       else
           System.exit(0);
    }
    
}
