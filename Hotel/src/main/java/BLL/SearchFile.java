package BLL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchFile {
    public static List<String> SearchDB(){
        List<String> list = new ArrayList<String>();
        try{
            File file = new File("BaseDados.txt");
            Scanner br = new Scanner(file);

            while (br.hasNextLine()){
                list.add(br.nextLine());
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
