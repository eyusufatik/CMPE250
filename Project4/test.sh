javac src/*.java -d bin/ --release 16
cd bin
for i in {0..649}
do
java project4main ../custom_tests/input$i.txt ../output.txt
    out=$(diff -w ../output.txt ../custom_tests/output$i.txt)
    if [[ ! $out = "" ]]
    then
        echo "error in $i"
        break
    fi
done
