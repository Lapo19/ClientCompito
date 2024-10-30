package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MioThread extends Thread{
    Socket socket;

    public MioThread(Socket socket){
        this.socket = socket;
    }
    public void run(){

    try {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        String azione;
        String biglietti;
        String disp="";
        String quant="";
        String tipologia="";
        boolean presente = false;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("inserire il proprio username:");
            String username = sc.nextLine();
            out.writeBytes(username + "\n");
            String apprUsername = in.readLine();

            if(apprUsername.equals("n")){
                System.out.println("username non disponibile");
                presente = true;
            }
            else{
                System.out.println("username disponibile");
                presente = false;
            }
        } while (presente);
        do {
            
            if(!presente){
                System.out.println("Scrivere l'azione che si vuole compiere' (scrivere -exit- per uscire, -visualizza- per vedere il numero di biglietti disponibili, -compra- per comprare dei biglietti)");

                azione = sc.nextLine();

                if(azione.equals("exit")){
                    out.writeBytes("QUIT" + "\n");
                }
                else if(azione.equals("visualizza")){
                    out.writeBytes("N" + "\n");
                    do {
                        biglietti =  in.readLine();
                        System.out.println(biglietti);
                    } while (!biglietti.equals(""));
                    
             
                }
                else if(azione.equals("compra")){
                    out.writeBytes("BUY" + "\n");
                    System.out.println("Di quale tipologia si vuole comprare?");
                    System.out.println("scrivere gold per i gold, pit per i pit e pasterre per i parterre");
                    tipologia = sc.nextLine();
                    out.writeBytes(tipologia+ "\n");
                    System.out.println("Quanti biglietti vuoi comprare?");
                    quant=sc.nextLine();
                    out.writeBytes(quant+ "\n");
                    disp = in.readLine();
                    System.out.println(disp);
                    
                    if(disp.equals("OK")){
                        System.out.println("Biglietti comprati con successo");
                    }
                    else{
                        System.out.println("Biglietti non sufficenti");
                    }
                        
                }
                
            }
            else{
                azione = "exit";
                out.writeBytes("!" + "\n");
            }
        } while (!azione.equals("exit"));

    } catch (Exception e) {
    }

}
}
