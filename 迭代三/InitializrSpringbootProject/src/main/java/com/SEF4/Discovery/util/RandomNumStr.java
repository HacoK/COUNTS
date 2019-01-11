/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public class RandomNumStr {
    private static Random rand = new Random(); 
    public static String get5Rand(){
        return Integer.toString(rand.nextInt(90000)+10000);
    }
    public static List<Integer> randomSort(Integer size){
        List<Integer> indexList=new ArrayList<Integer>();
        for(int i=1;i<=size;i++){
            indexList.add(i);
        }
        for(int i=0;i<size;i++){  
            int p = rand.nextInt(i+1);  
            int tmp = indexList.get(i);
            indexList.set(i, indexList.get(p));  
            indexList.set(p, tmp);  
        }  
        return indexList;
    }
  
}
