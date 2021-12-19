javac src/*.java -d bin/ --release 16
cd bin
for i in {0..14}
do
java project1Main ../testcases/input$i.txt ../output.txt
    out=$(diff -w ../output.txt ../testcases/output$i.txt)
    if [[ ! $out = "" ]]
    then
        echo "error in $i"
    else
        echo "passed $i"
    fi
done
