package com.example.allan.appalpharead.Algoritms;

import java.util.ArrayList;

public class QuickSortRankUsers {
    int partition(ArrayList<RankUsers> lista, int primeiro, int topo)
    {
        RankUsers pivo = lista.get(topo);
        int i = (primeiro-1);
        for (int j=primeiro; j<topo; j++)
        {
            if (lista.get(j).getPoint() <= pivo.getPoint()){
                i++;
                RankUsers temp = lista.get(i);
                lista.set(i,lista.get(j));
                lista.set(j, temp);
            }
        }
        RankUsers temp = lista.get(i+1);
        lista.set(i+1, lista.get(topo));
        lista.set(topo, temp);
        return i+1;
    }
    public ArrayList<RankUsers> sort(ArrayList<RankUsers> lista, int base, int topo){
        if (base < topo){
            int pi = partition(lista, base, topo);
            sort(lista, base, pi-1);
            sort(lista, pi+1, topo);
        }
        return lista;
    }
}
