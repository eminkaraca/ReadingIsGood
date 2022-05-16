package com.readingisgood.Service.Javers;

import org.javers.spring.auditable.AuthorProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaversConfiguration {


    private static final String AUTHOR = "Emin KARACA";


    @Bean
    public AuthorProvider provideJaversAuthor() {

        return new SimpleAuthorProvider();

    }


    private static class SimpleAuthorProvider implements AuthorProvider {

        @Override
        public String provide() {

            return AUTHOR;

        }

    }

}