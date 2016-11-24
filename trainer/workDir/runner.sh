#!/bin/bash

usage_getopt() {
 echo "Usage: runner.sh -r PROGRAM_NAME [Options]

     local run story

     -r : Run script. Use next:
        imageClassifySimplTrainer.cmd

     Options:
     -c : Recompile trainer
     -s : Get dog identity mapping

     For example:
        ./runner.sh -e imageClassifySimplTrainer.cmd -c

     Notes:
     "
}

if [ -z $* ]
then
    echo "No options found!"
    usage_getopt
    exit 1
fi


    while getopts ":r:jg:cs" opt; do
        case $opt in
        s)
            echo "Get dog identity mapping"
            cat imageClassify.cmd | grep "identity" | awk '{print $3}' | uniq | awk '{print "Input: "$1}'
            exit 0
            ;;
        c)
            echo "Compile..."
            cd ..
            gradle build
            cd -
            ;;
        r)
            if [ -n $OPTARG ] ; then
                echo "Use script: $OPTARG" >&2
                PROGRAM_NAME=$OPTARG;
                local_test_run_cmd $PROGRAM_NAME
            fi
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

local_test_run_cmd() {
    cmd_script=$1
    java -jar ../build/libs/trainer.jar "./$cmd_script"
}
