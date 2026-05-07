public class UniversalTuringMachene {

    Band band;
    int state;

    public UniversalTuringMachene(Band band) {
        this.band = band;
        int ones = 0;
        while(ones<3){
            if(band.read() == 0){
                ones = 0;
            }else {
                ones++;
            }
            while(ones<2){

            }
        }
    }
}
