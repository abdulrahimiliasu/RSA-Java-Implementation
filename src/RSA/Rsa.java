package RSA;

import java.math.BigInteger;
import java.util.Random;

public class Rsa {

    /**
     * Performs Extended Euclidean Algorithm with Two BigInteger Numbers
     * ax + by = gcd(a,b)
     * @param a x coefficient
     * @param b y coefficient
     * */
    public static BigInteger EEA(BigInteger a, BigInteger b)
    {
        BigInteger x,x1,y,y1,temp;
        x = BigInteger.ONE;
        x1 = BigInteger.ZERO;
        y = BigInteger.ZERO;
        y1 = BigInteger.ONE;

        while(b.compareTo(BigInteger.ZERO) > 0)
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

    /**
     * Performs Fast Modular Exponentiation*/
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

    /**
     * Generates Uniform Random BigInteger number from a range
     * @param start lower bottom of range
     * @param end end of range
     */
    private static BigInteger uniformRandom(BigInteger start, BigInteger end) {
        Random rnd = new Random();
        BigInteger res;
        do {
            res = new BigInteger(end.bitLength(), rnd);
        } while (res.compareTo(start) < 0 || res.compareTo(end) > 0);
        return res;
    }

    /**
     * Performs MilnerRabin Prime Test
     * @param n number to check
     * @param k k
     * */
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

    /**
     * @param a a
     * @param b b
     * Calculates phi = (a-1) * (b - 1)
     * */
    public static BigInteger Phi(BigInteger a,BigInteger b)
    {
        return a.subtract(BigInteger.ONE).multiply(b.subtract(BigInteger.ONE));
    }

    /**
     * Calculates Greatest Common Divisor between two numbers
     * @param a first number
     * @param b second number
     * */
    public static BigInteger gcd(BigInteger a,BigInteger b)
    {
        while(b.compareTo(BigInteger.ZERO) > 0)
        {
            BigInteger r = a.mod(b);
            a = b;
            b = r;
        }
        return a;
    }

    /**
     * Generates random Prime BigInteger
     * @return  Prime BigInteger Number
     * */
    public static BigInteger RandPrime()
    {
        Random rnd = new Random();
        BigInteger randomBigInteger = new BigInteger(200, rnd);
        if(MRTest(randomBigInteger,5))
            return randomBigInteger;
        else
            return RandPrime();
    }
}
