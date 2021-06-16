package com.rainfool.loadclass;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author krystian
 */
public class LoadClassProcessor extends AbstractProcessor {

    private static final int LOAD_CLASS_NUM = 5000;
    private static final String PKG_NAME = "com.rainfool.loadclass";

    private Filer filer;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateClasses();
        generateHost();
        return true;
    }

    private void generateClasses() {
        for (int i = 0; i < LOAD_CLASS_NUM; i++) {
            TypeSpec type = TypeSpec.classBuilder("Test" + i)
                    .addModifiers(Modifier.PUBLIC)
                    .build();
            JavaFile javaFile = JavaFile.builder(PKG_NAME, type).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateHost() {
        FieldSpec fieldSpec = FieldSpec.builder(List.class,"list")
                .addModifiers(Modifier.PRIVATE,Modifier.FINAL)
                .initializer("new $T()", ArrayList.class)
                .build();
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("loadTest")
                .addModifiers(Modifier.PUBLIC);
        for (int i = 0; i < LOAD_CLASS_NUM; i++) {
            methodBuilder.addStatement("list.add(Test" + i + ".class)");
        }
        TypeSpec typeSpec = TypeSpec.classBuilder("TestRoot")
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldSpec)
                .addMethod(methodBuilder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(PKG_NAME, typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(LoadClassHost.class.getCanonicalName());
    }
}
