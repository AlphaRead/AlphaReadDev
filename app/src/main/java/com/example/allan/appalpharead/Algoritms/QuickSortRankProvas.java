package com.example.allan.appalpharead.Algoritms;

import java.util.ArrayList;

public class QuickSortRankProvas {
    int partition(ArrayList<RankProvas> lista, int primeiro, int topo)
    {
        RankProvas pivo = lista.get(topo);
        int i = (primeiro-1);
        for (int j=primeiro; j<topo; j++)
        {
            if (Integer.valueOf(lista.get(j).getPoints()) > Integer.valueOf(pivo.getPoints())){
                i++;
                RankProvas temp = lista.get(i);
                lista.set(i,lista.get(j));
                lista.set(j, temp);
            }
        }
        RankProvas temp = lista.get(i+1);
        lista.set(i+1, lista.get(topo));
        lista.set(topo, temp);
        return i+1;
    }
    public ArrayList<RankProvas> sort(ArrayList<RankProvas> lista, int base, int topo){
        if (base < topo){
            int pi = partition(lista, base, topo);
            sort(lista, base, pi-1);
            sort(lista, pi+1, topo);
        }
        return lista;
    }
}
