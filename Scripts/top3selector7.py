import os
import pandas as pd
import os.path as osp
# have the depth as a parameter..
# have a singular program to generate data


def main():
    
    os.chdir(r'C:\\Users\\Lab\\Documents\\Git\\HyFlex\\V1_data')
    argumentsFile = 'argsfile.txt'
    lst = []
    ctr = 1
    while osp.isfile('7branch'+str(ctr)+".csv"):
        lst.append('7branch'+str(ctr)+".csv")
        ctr += 1
    
    data = pd.concat(lst)
    argsFile = open(argumentsFile,'r')
    args = argsFile.readline().rstrip('\n').split(',')
    results = data.sort_values('Final Score')
    print(results[:3])
    
    
    for i in range(3,len(args)): # will get the heuristics that follow the exact sequence listed here
        col = 'h' + str(i)
        data = data[data[col] == int(args[i])]

    results = data.sort_values('Final Score')
    print(results)
    argsFile.close()
    outFile = open('7top3.txt','w')
    for item in zip(results.iloc[:3,len(args)],results.iloc[:3,len(args)+1]):
        outFile.write(item[0]+","+item[1]+"\n")
    outFile.close()

main()
