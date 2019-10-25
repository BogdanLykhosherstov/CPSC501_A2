import java.lang.reflect.*;
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
        getMethodsInfo(c,obj,recursive,depth);
        getFieldsInfo(c,obj,recursive,depth);
    }


    public void properPrint(String text, int depth){
        for(int i=0;i<depth;i++){
            System.out.print("\t");
        }
        System.out.println(text);

    }

    // get methods info
    public void getMethodsInfo(Class c, Object obj, boolean recursive, int depth){
        //interfaces cannot be instantiated
        if(c.isInterface()){
            properPrint("Methods: None",depth);
        }
        else{
            properPrint("Methods: ",depth);
            for (Method meth : c.getDeclaredMethods()){
                properPrint(" Name: "+meth.getName(),depth);
                properPrint("Parameter Types: "+ Arrays.toString(meth.getParameterTypes()),depth+1);
                properPrint("Modifiers: "+ Modifier.toString(meth.getModifiers()),depth+1);
                properPrint("Return Type: "+ meth.getReturnType(),depth+1);
                properPrint("Exceptions: "+ Arrays.toString(meth.getExceptionTypes()),depth+1);
            }

        }

    }

    // get fields info
    public void getFieldsInfo(Class c, Object obj, boolean recursive, int depth){
        //interfaces cannot be instantiated
        if(c.isInterface()){
            properPrint("Fields: None",depth);
        }
        else{
            properPrint("Fields: ",depth);
            for (Field field : c.getDeclaredFields()){
                properPrint(" Name: "+ field.getName(),depth);
                properPrint("Type: "+ field.getType(),depth+1);
                properPrint("Modifiers: "+ Modifier.toString(field.getModifiers()),depth+1);

                //access private fields
                Object value = null;
                field.setAccessible(true);
                try{
                    value = field.get(obj);
                }
                catch (IllegalAccessException e){
                    System.out.println("ERROR: Cannot access field");
                }
                //- if field inaccessible
                if(value==null){
                    properPrint("Value: Null",depth+1);
                }
                //1. if primitive, just print as is
                else if(field.getType().isPrimitive()){
                    properPrint("Value: "+value, depth+1);
                }
                //2. if array - print reference (pull up into arrayInspection later)
                else if(field.getType().isArray()){
                    arrayInspect(recursive, depth, value);
                }
                //3. else its an object, print reference value
                else {
                    referenceInspect(recursive, depth, value);
                }
            }

        }

    }

    public void arrayInspect(boolean recursive, int depth, Object value) {
        if(recursive){
            //inspect each object of the array
            Class componentType  = value.getClass().getComponentType();
            properPrint("Array Type: "+componentType,depth+1);
            int length = Array.getLength(value);
            properPrint("Array Length: "+length,depth+1);
            //arrays could contain primitives, other arrays, or objects
            for(int i=0;i<length;i++){
                Object arrayItem = Array.get(value,i);
                if(arrayItem==null){
                    properPrint("Array Value: Null",depth+1);
                }
                //1. primitive
                if(componentType.isPrimitive()){
                   properPrint("Array Value: "+arrayItem,depth+1);
                }
                //2. array
                if(componentType.isArray()){
                    arrayInspect(recursive, depth, value);
                }
                //3. object
                else{
                    referenceInspect(recursive, depth, value);
                }
            }
        }
        //recursive off - just print the value of array
        else{
            properPrint("Value: "+value.getClass().getName()+"@"+value.getClass().hashCode(),depth+1);
        }
    }
    public void referenceInspect(boolean recursive, int depth, Object value) {
        if (recursive) {
            inspectClass(value.getClass(), value, recursive, depth + 1);
        } else {
            properPrint("Value: " + value.getClass().getName() + "@" + value.getClass().hashCode(), depth + 1);
        }
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
                properPrint(" Name: "+con.getName(),depth);
                properPrint("Parameter Types: "+ Arrays.toString(con.getParameterTypes()),depth+1);
                properPrint("Modifiers: "+ Modifier.toString(con.getModifiers()),depth+1);
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