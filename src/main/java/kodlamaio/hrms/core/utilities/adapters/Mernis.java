package kodlamaio.hrms.core.utilities.adapters;


import java.util.ArrayList;
import java.util.List;

public class Mernis {
    public Boolean validate(long tc, String adi, String soyadi, int dogumTarihi) {
        List<TurkishCitizen> turkishCitizens = AddCitizentoTurkishCitizienList();
        for (TurkishCitizen person : turkishCitizens) {
            if (
                    person.tc == tc &&
                    person.adi.equals(adi) &&
                    person.soyadi.equals(soyadi) &&
                    person.dogumTarihi == dogumTarihi) {
                return true;
            }
        }
        return false;
    }

    private List<TurkishCitizen> AddCitizentoTurkishCitizienList() {
        List<TurkishCitizen> turkishCitizens = new ArrayList<>();
        turkishCitizens.add(new TurkishCitizen(19442029012L,"Emre","FIRAT",1993));
        turkishCitizens.add(new TurkishCitizen(96532883082L,"Ali","YILDIZ",1998));
        turkishCitizens.add(new TurkishCitizen(21715864510L,"Veli","SUAT",2000));
        turkishCitizens.add(new TurkishCitizen(64596727062L,"Hasan","DENİZ",1990));
        turkishCitizens.add(new TurkishCitizen(98052856362L,"Özge","FIRAT",1994));

        return turkishCitizens;
    }


}
class TurkishCitizen {
    long tc;
    String adi;
    String soyadi;
    int dogumTarihi;

    public TurkishCitizen(long tc, String adi, String soyadi, int dogumTarihi) {
        this.tc = tc;
        this.adi = adi;
        this.soyadi = soyadi;
        this.dogumTarihi = dogumTarihi;
    }
}



