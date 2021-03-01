package model;

import java.util.Comparator;

public class PieceComparator implements Comparator<PieceModel> {

    @Override
    public int compare(PieceModel p1, PieceModel p2) {
        if(p1.getColonne() - 'a' < p2.getColonne() - 'a'){
            return -1;
        }else if(p1.getColonne() - 'a' == p2.getColonne() - 'a'){
            if(p1.getLigne() < p2.getLigne()){
                return -1;
            }else if(p1.getLigne() == p2.getLigne()){
                return 0;
            }else{
                return 1;
            }
        }else{
            return 1;
        }
    }
}
