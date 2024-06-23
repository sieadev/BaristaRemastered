package dev.siea.barista;

import net.vitacraft.jmjda.JMBot;

public class Barista {


    public Barista(String token){
        JMBot bot = new JMBot(token);

    }

    public static void main(String[] args) {
        Barista barista = new Barista("");
    }
}
