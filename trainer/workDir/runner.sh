#!/bin/bash

usage_getopt() {
 echo "Usage: runner.sh -r PROGRAM_NAME [Options]

     local run story

     -r : Run script. Use next:
        imageClassifySimplTrainer.cmd

     Options:
     -c : Rebuild trainer

     For example:
        ./runner.sh -e imageClassifySimplTrainer.cmd

     Notes:
     "
}

eis=false

    while getopts ":r:jg:c" opt; do
        case $opt in
        c)
            echo "Rebuild..."
            cd ..
            gradle build
            cd -
            ;;
        r)
            if [ -n $OPTARG ] ; then
                echo "Use script: $OPTARG" >&2
                PROGRAM_NAME=$OPTARG;
                eis=true
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
        esac
    done


list_stories_by_keyword() {
    keyword=$1
    grep -rli --include=*.story --exclude-dir=target "$keyword" . | awk -F/ '{print($NF)}' | sed -e 's/.story/,/'
}

verify_params_and_run() {
    if $eis
    then
        local_test_run_cmd $PROGRAM_NAME
    else

        if [ -n "$lis" ]; then
            list_stories_by_keyword $lis
            exit 0;
        fi;

        echo "Define ENV.NAME. -e is mandatory"
        usage_getopt

    fi;

}

local_test_run_cmd() {
    cmd_script=$1
    java -jar ../build/libs/trainer.jar "./$cmd_script"
}

verify_params_and_run
