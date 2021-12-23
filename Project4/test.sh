javac src/*.java -d bin/ --release 16
cd bin
for i in {0..649}
do
java project4main ../test_cases/input_$i.txt ../output.txt
    out=$(diff -w ../output.txt ../test_cases/output_$i.txt)
    if [[ ! $out = "" ]]
    then
        echo "error in $i"
        break
    else
        echo "passed $i"
    fi
done
