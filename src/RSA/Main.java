
package RSA;

import java.util.Random;
import java.util.Scanner;
import java.math.BigInteger;

public class Main {
    
   public static BigInteger EEA(BigInteger a,BigInteger b)
   {
       BigInteger x,x1,y,y1,temp;
       x = BigInteger.ONE;
       x1 = BigInteger.ZERO;
       y = BigInteger.ZERO;
       y1 = BigInteger.ONE;
              
       while(b.compareTo(BigInteger.ZERO)==1)
       {
           BigInteger q,r;
           q = a.divide(b);
           r = a.mod(b);
           
           a=b;
           b=r;
           
           temp = x;
           x = x1.subtract(q.multiply(x));
           x1 = temp;
           
           temp = y;
           y = y1.subtract(q.multiply(y));
           y1 = temp;
       }
       
       return x1;
   }
    
   
   public static BigInteger FME(BigInteger a,BigInteger b,BigInteger n)
   {
       a = a.mod(n);
       if(b.compareTo(BigInteger.ZERO)==0)
           return BigInteger.ONE;
       else if(b.compareTo(BigInteger.ONE)==0)
           return a;
       else if(b.mod(BigInteger.TWO)==BigInteger.ZERO)
           return FME(a.multiply(a.mod(n)),b.divide(BigInteger.TWO),n);
       else
           return a.multiply(FME(a,b.subtract(BigInteger.ONE),n)).mod(n);
   }
   
   private static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
	Random rnd = new Random();
	BigInteger res;
	do {
            res = new BigInteger(top.bitLength(), rnd);
            } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
		return res;
	}
   
   public static boolean MRTest(BigInteger n, int k) {
    if (n.compareTo(BigInteger.ONE) == 0)
			return false;
    if (n.compareTo(BigInteger.valueOf(3)) < 0)
			return true;
        int s = 0;
	BigInteger d = n.subtract(BigInteger.ONE);
	while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) 
            {
		s++;
		d = d.divide(BigInteger.TWO);
            }
	for (int i = 0; i < k; i++) 
            {
                BigInteger a = uniformRandom(BigInteger.TWO, n.subtract(BigInteger.ONE));
                BigInteger x = FME(a,d,n);
                if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                    continue;
                int r = 0;
                for (; r < s; r++) 
                {
                    x = FME(x,BigInteger.TWO, n);
                    if (x.equals(BigInteger.ONE))
                        return false;
                    if (x.equals(n.subtract(BigInteger.ONE)))
                        break;
                }
                if (r == s) // None of the steps made x equal n-1.
		return false;
            }
            return true;
	}    
   
   public static BigInteger Phi(BigInteger a,BigInteger b)
   {
       return a.subtract(BigInteger.ONE).multiply(b.subtract(BigInteger.ONE));   
   }
   
   public static BigInteger gcd(BigInteger a,BigInteger b)
   {
    while(b.compareTo(BigInteger.ZERO)==1)
    {
        BigInteger r = a.mod(b);
        a = b;
        b = r;
    }   
    return a;
   }
    
   public static BigInteger RandPrime()
   {
       int rnd;
       rnd = (int)(Math.random() * (1000000000 - 1000000 +1000000)+1000000);
       BigInteger Rnd = BigInteger.valueOf(rnd);
       if(MRTest(Rnd,5))
           return Rnd;
       else
           return RandPrime();
   }
      
   
   public static void main(String[] args) {
       
       BigInteger p,q,n,phi,e,d,m,c,dm,dc,j,eHOLD;
       
       Scanner sc =  new Scanner(System.in);
       
       p = RandPrime();
       q = RandPrime();
       
       n = p.multiply(q);
       phi = Phi(p,q);
       
       BigInteger odd[];
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
        if(eHOLD.compareTo(BigInteger.ONE)== 1)
       {
           if(eHOLD.compareTo(phi)== -1)
           {
               if(gcd(eHOLD,phi).compareTo(BigInteger.ONE)==0)
               {
                   e = eHOLD;
                   break;
               }
               else
                   i++;
           }  
       }                  
      }
       
       d  =  EEA(phi,e);
       
       if(d.compareTo(BigInteger.ZERO)== -1)
           d = d.add(phi);
       
       System.out.println("PK("+n+","+e+")");
       System.out.println("SK("+d+")");
       
       //Encryption
       System.out.print("Message:");
       m = sc.nextBigInteger();
       if(m.compareTo(n) == -1){
           c = FME(m,e,n);
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
       dm = FME(dc,d,n);
       System.out.println(dm+" <-- Message");
       }
       else
           System.exit(0);
    }
    
}
