import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {

        //for each member, get Name, Superclass, Interface, etc.
        getClassName(c,depth);
        getSuperClassInfo(c,obj,recursive,depth);
        getInterfaceInfo(c,obj,recursive,depth);
        getConstructorInfo(c,obj,recursive,depth);

    }


    public void properPrint(String text, int depth){
        for(int i=0;i<depth;i++){
            System.out.print("\t");
        }
        System.out.println(text);

    }
    //returns current class name
    public void getClassName(Class c, int depth){
        properPrint("Name: "+c.getName(),depth);
    }
    //get super class info
    public void getSuperClassInfo(Class c, Object obj, boolean recursive, int depth){

        //base case
        if(c.equals(Object.class))return;

        Class super_class = c.getSuperclass();

        if(super_class!=null){
            properPrint("Super Class: "+super_class.getName(),depth);
            inspectClass(super_class,obj,recursive,depth+1);
        }
        else{
            properPrint("Super Class: None",depth);
        }
    }
    // get constructors
    public void getConstructorInfo(Class c, Object obj, boolean recursive, int depth){
        //interfaces cannot be instantiated
        if(c.isInterface()){
            properPrint("Constructors: None",depth);
        }
        else{
            properPrint("Constructors: ",depth);
            for (Constructor con : c.getConstructors()){
                properPrint(" * Name: "+con.getName(),depth);
                properPrint(" -- Parameter Types: "+ Arrays.toString(con.getParameterTypes()),depth);
                properPrint(" -- Modifiers: "+ Modifier.toString(con.getModifiers()),depth);
            }
        }

    }
    //get interfaces info
    public void getInterfaceInfo(Class c, Object obj, boolean recursive, int depth){

        Class[] interfaceList = c.getInterfaces();
        if(interfaceList.length == 0){
            properPrint("Interface: None",depth);
        }
        else{
            for(Class interFace : interfaceList){
                properPrint("Interface: "+interFace.getName(),depth);
                inspectClass(interFace,obj,recursive,depth+1);
            }
        }


    }


}