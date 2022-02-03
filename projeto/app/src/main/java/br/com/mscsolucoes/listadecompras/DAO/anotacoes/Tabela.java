package br.com.mscsolucoes.listadecompras.DAO.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tabela {


    String nome();
    String prefixo();
    String join() default "";

}
