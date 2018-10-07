import input.ProcessInput;
import objects.Polynom;
import org.junit.Test;

public class JUnitTest {

    @Test
    public void wrongInput() {
        assert (ProcessInput.processInput("x.12") == null);
        assert (ProcessInput.processInput("x^22x^22x^22-") == null);
        assert (ProcessInput.processInput("x^x") == null);
        assert (ProcessInput.processInput("asdasda") == null);
        assert (ProcessInput.processInput("-") == null);
        assert (ProcessInput.processInput("x^-1") == null);
        assert (ProcessInput.processInput("x^") == null);
        assert (ProcessInput.processInput("xx^") == null);
        assert (ProcessInput.processInput("-32xx") == null);
        assert (ProcessInput.processInput("2^4+2x+1") == null);
        assert (ProcessInput.processInput("-32x^-12") == null);
        assert (ProcessInput.processInput("-32x^-") == null);
        assert (ProcessInput.processInput("-32x^-1") == null);
        assert (ProcessInput.processInput("--x") == null);
        assert (ProcessInput.processInput("-32x^123-x^-1") == null);
        assert (ProcessInput.processInput("^") == null);
        assert (ProcessInput.processInput("x+1^x") == null);
        assert (ProcessInput.processInput("x-^2x") == null);
        assert (ProcessInput.processInput("0^0") == null);
        assert (ProcessInput.processInput("+-1") == null);
        assert (ProcessInput.processInput("2^4") == null);
        assert (ProcessInput.processInput("x^^") == null);
        assert (ProcessInput.processInput("3x4+2") == null);
        assert (ProcessInput.processInput("3x^4dsa+2") == null);
        assert (ProcessInput.processInput("3x^4-+2") == null);

    }

    @Test
    public void correctInput(){
        assert (ProcessInput.processInput("30x^2-x^1+1").toString().equals("30x^2-x+1"));
        assert (ProcessInput.processInput("-3x^100000+1").toString().equals("-3x^100000+1"));
        assert (ProcessInput.processInput("32x^3+12x^2-11x+1").toString().equals("32x^3+12x^2-11x+1"));
        assert (ProcessInput.processInput("3").toString().equals("3"));
        assert (ProcessInput.processInput("x").toString().equals("x"));
        assert (ProcessInput.processInput("x^4").toString().equals("x^4"));
        assert (ProcessInput.processInput("-x^4+2").toString().equals("-x^4+2"));
        assert (ProcessInput.processInput("3x^4+2").toString().equals("3x^4+2"));
        assert (ProcessInput.processInput("x").toString().equals("x"));
        assert (ProcessInput.processInput("x").toString().equals("x"));
        assert (ProcessInput.processInput("x").toString().equals("x"));
    }

    @Test
    public void testAddition() {
        Polynom p, q;
        p = ProcessInput.processInput("3x^2+2x+1");
        q = ProcessInput.processInput("3x^2+2x+1");
        p.addition(q);
        assert(p.toString().equals("6x^2+4x+2"));

        p = ProcessInput.processInput("3x^2+3x^2");
        q = ProcessInput.processInput("1");
        p.addition(q);
        assert(p.toString().equals("6x^2+1"));

    }

    @Test
    public void testSubstraction(){
        Polynom p, q;
        p = ProcessInput.processInput("3x^2+2x+1");
        q = ProcessInput.processInput("3x^2+2x+2");
        p.substraction(q);
        assert(p.toString().equals("-1.0"));
    }

    @Test
    public void testMultiplication(){
        Polynom p, q;
        p = ProcessInput.processInput("3x^2+2x+1");
        q = ProcessInput.processInput("7x^2+1");
        p.multiply(q);
        assert(p.toString().equals("21x^4+14x^3+10x^2+2x+1"));
    }

    @Test
    public void testDerivate(){
        Polynom p;
        p = ProcessInput.processInput("3x^2+2x+1");
        p.derivate();
        assert(p.toString().equals("6x+2"));
    }

    @Test
    public void testIntegrate(){
        Polynom p;
        p = ProcessInput.processInput("3x^2+2x+1");
        p.integrate();
        assert(p.toString().equals("x^3+x^2+x"));
    }
}