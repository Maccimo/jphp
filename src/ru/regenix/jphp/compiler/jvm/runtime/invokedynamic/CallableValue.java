package ru.regenix.jphp.compiler.jvm.runtime.invokedynamic;

import ru.regenix.jphp.compiler.jvm.runtime.Memory;
import ru.regenix.jphp.compiler.jvm.runtime.PHPObject;
import ru.regenix.jphp.env.Environment;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

@SuppressWarnings({ "unused", "rawtypes" })
public class CallableValue {

    protected final Method method;
    private static MethodHandle methodHandle;

    public CallableValue(Method clazz) {
        this.method = clazz;
    }

    public Memory call(PHPObject self, Environment env, Memory[] args){
        try {
            Memory memory = (Memory) method.invoke(self, args);
            return memory == null ? Memory.NULL : memory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CallSite bootstrapDynamic(MethodHandles.Lookup caller, String name, MethodType type)
            throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class thisClass = lookup.lookupClass();

        methodHandle = lookup.findVirtual(
                thisClass,
                name,
                MethodType.methodType(Environment.class, Memory[].class)
        );

        return new ConstantCallSite(methodHandle.asType(type));
    }

    public static CallSite bootstrapStatic(MethodHandles.Lookup caller, String name, MethodType type)
            throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class thisClass = lookup.lookupClass();

        methodHandle = lookup.findStatic(
                thisClass,
                name,
                MethodType.methodType(Environment.class, Memory[].class)
        );

        return new ConstantCallSite(methodHandle.asType(type));
    }
}
