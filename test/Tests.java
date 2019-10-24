
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Tests{

    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Inspector ins = new Inspector();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void restore() {
        output.reset();
    }

    @Test
    public void Get_ClassName() throws Exception{
        ClassB b1 = new ClassB();
        ins.getClassName(b1.getClass(),0);
        assert(output.toString().contains("Name: ClassB"));
    }
    @Test
    public void Get_SuperClass() throws Exception{
        ClassA a1 = new ClassA();
        ins.getSuperClassInfo(a1.getClass(),null,true,0);
        assert(output.toString().contains("Super Class: java.lang.Object"));
    }
    @Test
    public void Get_Interface() throws Exception{
        ClassB b1 = new ClassB();
        ins.getInterfaceInfo(b1.getClass(),null,true,0);
        assert(output.toString().contains("Interface: java.lang.Runnable"));
    }
    @Test
    public void Get_ConstructorInfo() throws Exception{
        ClassB b1 = new ClassB();
        ins.getConstructorInfo(b1.getClass(),null,true,0);

        assert(output.toString().contains(" * Name: ClassB"));
        assert(output.toString().contains(" -- Parameter Types: []"));
        assert(output.toString().contains(" -- Modifiers: public"));

    }

}
