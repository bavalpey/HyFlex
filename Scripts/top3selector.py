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
    while osp.isfile('branch'+str(ctr)+".csv"):
        lst.append(pd.read_csv('branch'+str(ctr)+".csv"))
        ctr += 1
    
    data = pd.concat(lst)
    data.drop_duplicates(subset=data.columns.values[:-1],inplace=True)
    argsFile = open(argumentsFile,'r')
    args = argsFile.readline().rstrip('\n').split(',')[:-1]
    results = data.sort_values('Final Score')
    
    for i in range(len(args)): # will get the heuristics that follow the exact sequence listed here
        col = 'h' + str(i)
        data = data[data[col] == int(args[i])]

    results = data.sort_values('Final Score')
    argsFile.close()
    outFile = open('top3.txt','w')
    for item in zip(results.iloc[:3,len(args)],results.iloc[:3,len(args)+1]):
        outFile.write(str(item[0])+","+str(item[1])+"\n")
    outFile.close()

main()
