package com.example.allan.appalpharead.Algoritms;

import java.util.ArrayList;

public class BuscaBinaria {
    public int Binaria(String name, ArrayList<RankUsers> rank){
        int meio;
        int inicio = 0;
        int fim = rank.size()-1;
        while (inicio <= fim) {
            meio = (inicio + fim)/2;
            if (name.equals(rank.get(meio).getName()))
                return rank.get(meio).getPoint();
            if (name.compareTo(rank.get(meio).getName()) < 0)
                fim = meio - 1;
            else
                inicio = meio + 1;
        }
        return -1;
    }
}
