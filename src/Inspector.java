import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }


    public void properPrint(String text, int depth){
        for(int i=0;i<depth;i++){
            System.out.println('|'+'\t');
        }
        System.out.println(text);

    }
    //returns current class name
    public void getClassName(Class c, int depth){
        properPrint("Name of class: "+c.getName(),depth);
    }
    //get super class info
    public void getSuperClassInfo(){

    }
    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        getClassName(c,depth);

        System.out.println("Immediate Super Class: "+c.getSuperclass().getName());
        System.out.println("Interfaces: "+Arrays.toString(c.getInterfaces()));
        System.out.println("Constructors: ");
        for (Constructor con : c.getConstructors()){
            System.out.println(" * Name: "+con.getName());
            System.out.println(" -- Parameter Types: "+ Arrays.toString(con.getParameterTypes()));
            System.out.println(" -- Modifiers: "+ Modifier.toString(con.getModifiers()));
        }
    }
}