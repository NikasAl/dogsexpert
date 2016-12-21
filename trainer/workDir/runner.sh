#!/bin/bash

usage_getopt() {
 echo "Usage: runner.sh -r PROGRAM_NAME [Options]

     local run story

     -r : Run script. Use next:
        imageClassifySimplTrainer.cmd

     Options:
     -c : Recompile trainer
     -s : Get dog identity mapping
     -k : Console mode

     For example:
        ./runner.sh -r imageClassifySimplTrainer.cmd -c

     Notes:
     "
}

CM=false

local_test_run_cmd() {
    cmd_script=$1
    if [ "$CM" = false ]
    then
        java -jar ../build/libs/trainer.jar "./$cmd_script"
    else
        java -cp ../build/libs/trainer.jar ru.electronikas.dogexpert.trainer.ConsoleTrainer "./$cmd_script"
    fi
}


if [ -z $* ]
then
    echo "No options found!"
    usage_getopt
    exit 1
fi


    while getopts "r:jg:csmd:k" opt; do
        case $opt in
        d)
            echo "Download "
#            local_test_run_cmd $OPTARG
            local_test_run_cmd ./ImgUrlsFromImageNet_pitbul.txt
            local_test_run_cmd ./ImgUrlsFromImageNet_pudel.txt
            local_test_run_cmd ./ImgUrlsFromImageNet_senbernar.txt
            local_test_run_cmd ./ImgUrlsFromImageNet_taksa.txt
            ;;
        c)
            echo "Compile..."
            cd ..
            gradle build
            cd -
            ;;
        s)
            echo "Get dog identity mapping"
            cat imageClassify.cmd | grep "identity" | awk '{print $3}' | uniq | awk '{print "Input: "$1}'
            exit 0
            ;;
        m)
            echo "Get dog identity mapping for Breeds.class"
            cat imageClassify.cmd | grep "identity" | awk '{print $3}' | uniq | awk -F":" '{print $2","}' | sed -e 's/-/_/g'
            exit 0
            ;;

        r)
            if [ -n $OPTARG ] ; then
                echo "Use script: $OPTARG" >&2
                PROGRAM_NAME=$OPTARG;
                local_test_run_cmd $PROGRAM_NAME
            fi
            ;;
        k)
            echo "Console Mode Activated"
            CM=true
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            usage_getopt
            exit 1
            ;;
        :)
            echo "Option -$OPTARG requires an argument." >&2
            usage_getopt
            exit 1
            ;;
        *)
            echo "No reasonable options found!"
            usage_getopt
            ;;
        esac
    done
