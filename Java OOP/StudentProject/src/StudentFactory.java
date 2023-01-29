public class StudentFactory {

        public static Student getInstance(String indeks, String ime, String prezime) {
            String petaCifra = indeks.substring(4, 5);

            switch (petaCifra) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    return getOS(indeks, ime, prezime);
                case "6":
                case "7":
                    return getMS(indeks, ime, prezime);
                case "8":
                case "9":
                    return getDS(indeks, ime, prezime);
                default:
                    return null;
            }
        }

        private static StudentOsnovne getOS(String indeks, String ime, String prezime) {
            String sestaCifra = indeks.substring(5, 6);

            String smer = "";
            switch (sestaCifra) {
                case "0":
                    smer = "Informatika i računarstvo";
                    break;
                case "1":
                    smer = "Poslovna ekonomija";
                    break;
                case "2":
                    smer = "Informatika i računarstvo";
                    break;
                case "3":
                    smer = "Informacione tehnologije";
                    break;
                case "4":
                    smer = "Turizam i hotelijerstvo";
                    break;
                case "5":
                    smer = "Softversko i info. inženjer.";
                    break;
                case "6":
                    smer = "Menadžment u sportu";
                    break;
                case "7":
                    smer = "Fizičko vaspitanje i sport";
                    break;
                case "8":
                    smer = "Turizam i hotelijerstvo";
                    break;
                case "9":
                    smer = "Grupa smerova";
                    break;
            }

            return new StudentOsnovne(ime, prezime, smer, indeks);
        }

        private static StudentMaster getMS(String indeks, String ime, String prezime) {
            String sestaCifra = indeks.substring(5, 6);

            String smer = "";
            switch (sestaCifra) {
                case "0":
                    smer = "Poslovna ekonomija";
                    break;
                case "1":
                    smer = "Poslovna ekonomija";
                    break;
                case "2":
                    smer = "Poslovna ekonomija";
                    break;
                case "3":
                    smer = "Interna revizija i forenzika";
                    break;
                case "4":
                    smer = "Inženjerski menadžment";
                    break;
                case "5":
                    smer = "Savremene info. tehnologije";
                    break;
                case "6":
                    smer = "Savremene info. tehnologije";
                    break;
                case "7":
                    smer = "Savremene info. tehnologije";
                    break;
                case "8":
                    smer = "Elektrotehnika i računarstvo";
                    break;
                case "9":
                    smer = "Grupa smerova";
                    break;
            }

            return new StudentMaster(ime, prezime, smer, indeks);
        }

        private static StudentDoktorske getDS(String indeks, String ime, String prezime) {
            String sestaCifra = indeks.substring(5, 6);

            String smer = "";
            switch (sestaCifra) {
                case "0":
                    smer = "Napredni sistemi zaštite";
                    break;
                case "1":
                    smer = "Savrem. posl. odlučivanje";
                    break;
                case "2":
                    smer = "Menadžment u turizmu";
                    break;
                case "3":
                    smer = "Napredni sistemi zaštite";
                    break;
                case "4":
                    smer = "Napredni sistemi zaštite";
                    break;
                case "5":
                    smer = "Elektroteh. i računarstvo";
                    break;
                case "6":
                    smer = "Elektroteh. i računarstvo";
                    break;
                case "7":
                    smer = "Grupa smerova";
                    break;
                case "8":
                    smer = "Grupa smerova";
                    break;
                case "9":
                    smer = "Grupa smerova";
                    break;
            }

            return new StudentDoktorske(ime, prezime, smer, indeks);
        }
    }
