package com.example;

public record Estudo(String conteudo, String prazo){

    @Override
    public String toString(){
        return conteudo + " " + prazo + " ";
    }
}
