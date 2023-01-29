#!/bin/bash

cd ~

rm -rf ZADATAK
mkdir ZADATAK
cd ZADATAK

echo -n "Unesite broj indeksa"
read INDEKS 

URL = "http://zadatak.singidunum.ac.rs/apps/os/ispit/i.php?o=$INDEKS"

wget -q $URL -O preuzetiPodaci.txt

cat preuzetiPodaci.txt  grep -E '^https:\/\/zadatak\.singidunum\.ac\.rs\/[^ ]+$'> links

brojac = 1
for link in $( cat links ); do
    wget $link -O "data$brojac.txt"
    ostatak =$(($brojac %2))
    if[ $ostatak  -eq 1]; then
        cat "data$brojac.txt"   > zbirna..txt
    fi
    brojac=$((brojac +1))
done
cat Zbirna.txt | grep -E "^[a-z]+\.[a-z]+\.[0-9]{2,3}\@singimail\.rs$"| sort -ru > mail.txt
cat Zbirna.txt | grep -E "^[A-Z]{2}\-[0-9]{3,5}\-[A-Z]{2}$" | sort -u > tablice.txt

for tablica in $( cat tablice.txt ); do
    prvaCifra = $(echo $tablica|sed -E 's/^[A-Z]{2}\.//' |head -c 1)
    ostatak =$(( $prvaCifra %2))
    if [ $ostatak  -eq 1]; then
        echo $tablica >> neparne.txt
    else
        echo $tablica >> parne.txt
    fi
done

tar -czf "$INDEKS.tar.gz" ../test.sh  $(ls data*.txt) zbirna.txt mail.txt tablice.txt neparne.txt parne.txt
echo "Kraj"