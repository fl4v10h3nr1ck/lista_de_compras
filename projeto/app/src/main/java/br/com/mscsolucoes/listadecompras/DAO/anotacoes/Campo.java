package br.com.mscsolucoes.listadecompras.DAO.anotacoes;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;




@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Campo{

    String nome();
    String prefixo() default "";
    String rotulo() default "";
    boolean Inteiro() default false;
    String set();
    String get();
    boolean Id() default false;
    boolean select_apenas() default false;
}
