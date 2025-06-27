#!/bin/bash

# Display header
printf "\n=== Memory Statistics by Process ===\n\n"

# Execute ps with memory-related columns
ps -eo pid,ppid,user,%mem,vsz,rss,size,stat,comm --sort=-%mem | \
awk 'BEGIN {
    print "PID\tPPID\tUSER\t%MEM\tVSZ(MB)\tRSS(MB)\tSIZE(MB)\tSTAT\tCOMMAND"
    print "---\t----\t----\t----\t-------\t-------\t--------\t----\t-------"
}
NR>1 {
    printf "%s\t%s\t%s\t%.1f\t%.1f\t%.1f\t%.1f\t%s\t%s\n",
    $1, $2, $3, $4,
    $5/1024, # VSZ to MB
    $6/1024, # RSS to MB
    $7/1024, # SIZE to MB
    $8, $9
}'

printf "\nLegend:\n"
printf "VSZ: Virtual Memory Size\n"
printf "RSS: Resident Set Size (actual memory used)\n"
printf "SIZE: Text + Data + Stack\n"
printf "STAT: Process State (R=running, S=sleeping, Z=zombie, etc)\n"