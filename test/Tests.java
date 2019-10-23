
import org.junit.jupiter.api.Test;



public class Tests{

    Inspector ins = new Inspector();
    @Test
    public void Inspector_Test() throws Exception {
        ClassB b1 = new ClassB();
        ins.inspect(b1,false);
    }

}
