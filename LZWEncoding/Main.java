package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("orj.txt"));
        String readTxt="";

        try{
            String line = br.readLine();
            if(line.length()>0){
                readTxt = line;
            }
        }
        finally
        {
            System.out.println("Line: " + readTxt);
        }
        br.close();
        ArrayList<Integer> output = new ArrayList<>();
        output=EncodingLZW(readTxt);
        System.out.println("Output codes are: ");
        for(int i=0; i<output.size(); i++){
            System.out.print(output.get(i) + " ");
        }
        BufferedWriter brw = new BufferedWriter(new FileWriter("lzw.txt"));
        try{
            for(int i=0; i<output.size(); i++){
                brw.write(output.get(i)+" ");
            }
        }
        finally{
            // Do something if you want
        }
        brw.close();
        BufferedReader brr = new BufferedReader(new FileReader("lzw.txt"));
        try{
            String line = brr.readLine();
            if(line.length()>0){
                readTxt = line;
            }
        }
        finally
        {
            // Do something if you want
        }
        brr.close();
        String[] readTxtBuilder = readTxt.split(" ");
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<readTxtBuilder.length; i++){
            int temp = Integer.parseInt(readTxtBuilder[i]);
            list.add(temp);
        }
        decoding(list);
        System.out.print("\n");
    }

    static ArrayList<Integer> EncodingLZW(String s){

        System.out.println("Encoding started");
        HashMap<String,Integer> MapForEncoding = new HashMap<>(); //table
        for(int i=0; i<=255; i++){
            String ch="";
            ch+= ((char)i);
            MapForEncoding.put(ch,i);
        }
        String p="", c="";
        char[] sChar = s.toCharArray();
        System.out.println("Input:");
        for(int i=0; i<sChar.length; i++){
            System.out.print(sChar[i]);
        }
        p+= sChar[0];
        int code = 256;

        ArrayList<Integer> output_code = new ArrayList<>();
        for(int i=0; i< sChar.length; i++){
            if(i!=sChar.length-1){
                c+=sChar[i+1];
            }
            if(MapForEncoding.containsKey(p+c)){
                if(MapForEncoding.get(p+c) != endOfMap(MapForEncoding)){
                    p=p+c;
                } }
            else{
                System.out.println(p+"\t"+ MapForEncoding.get(p) +"\t\t" + p+c + "\t" + code);
                output_code.add(MapForEncoding.get(p));
                MapForEncoding.put(p+c,code);
                code++;
                p=c;
            }
            c="";
        }
        System.out.println(p+"\t"+MapForEncoding.get(p));
        output_code.add(MapForEncoding.get(p));
        System.out.println("\n End of Encoding");
        return output_code;
    }

    static void decoding(ArrayList<Integer> list){
        String orj_str="";
        StringBuilder sb = new StringBuilder();
        HashMap<Integer,String> MapForDecoding = new HashMap<>();
        System.out.print("\n" );
        for(int i=0; i<=255; i++){
            String ch="";
            ch+= ((char)i);
            MapForDecoding.put(i,ch);
        }
        int old = list.get(0),n;
        String s = MapForDecoding.get(old);
        String c = "";
        c+=s;
        sb.append(s);
        System.out.print(s + " ");
        int count = 256;
        for(int i=0; i< list.size() -1; i++){
            n = list.get(i+1);
            if(MapForDecoding.get(n).equals(MapForDecoding.get(MapForDecoding.size()))){
                s=MapForDecoding.get(old);
                s = s + c;
                System.out.println("S: " + s);
            }
            else{
                s = MapForDecoding.get(n);
            }
            System.out.print(s + " ");
            sb.append(s);
            c ="";
            c+=s;
            MapForDecoding.put(count,MapForDecoding.get(old)+c);
            count++;
            old = n;
        }
        System.out.println("\n End of decoding \n sb: " + sb );
    }
    static int endOfMap(HashMap<String,Integer> TempMap){
        int lastEntry=0;
        int j=0;
        int sizeOfHashMap = TempMap.size();
        for(String entry: TempMap.keySet()){
            j++;
            if(sizeOfHashMap == j-1){
                lastEntry  =  TempMap.get(entry);
            }
        }

        return lastEntry;
    }
}
