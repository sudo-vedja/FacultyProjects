#!/bin/bash

rm -rf ZADATAK
mkdir ZADATAK
cd ZADATAK

# 1
echo -n "Unesite broj indeksa: "
read index
# Ako se trazi dialog:
# indeks=$( dialog --inputbox "Unesite broj indeksa:" 8 35 --output-fd 1 )

# 2
wget -q "https://zadatak.singidunum.ac.rs/app/os/ispit/i.php?o=$indeks" -O Data.txt

# 3
cat Data.txt | grep -E "^https?:\/\/zadatak\.singidunum\.ac\.rs\/[^ ]+\/[$indeks]+\.txt$" > .links

# 4
for link in $( cat .links ); do
    broj=$(echo $link | sed 's/1/5/g; s|https://zadatak.singidunum.ac.rs/app/os/links/||; s/.txt//')

    if [ $broj -gt 90 ]; then
        broj=$(( $broj - 80 ))
    else
        broj=$(( $broj + 1 ))
    fi

    link="https://zadatak.singidunum.ac.rs/app/os/links/$broj.txt"

    echo $link >> .changed-links
done

cat .changed-links | sort -ru > .final-links

# 5

for link in $( cat .final-links ); do
    wget -q $link -O - >> mix
done

# 6
cat mix | grep -E '^\-?[0-9]+\.[0-9]+$' | sort > brojevi

# 7
cat mix | grep -E '^[0-9]{2}\.[0-9]{2}\.[0-9]{4}\.$' | sort > datumi

# 8
cat datumi | grep -E '2018\.$' > godina.18

# 9
tar czf "$indeks.tar.gz" ../april.sh mix brojevi datumi godina.18
