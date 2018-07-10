import os
import pandas as pd

# have the depth as a parameter..
# have a singular program to generate data


def main():
    os.chdir(r'C:\\Users\\Lab\\Documents\\Git\\HyFlex\\V1_data')
    argumentsFile = 'argsfile.txt'
    data1 = pd.read_csv('branch1.csv',header=None,names=["h1","h2","h3","h4","h5","Final Score"])
    data2 = pd.read_csv('branch2.csv',header=None,names=["h1","h2","h3","h4","h5","Final Score"])
    data3 = pd.read_csv('branch3.csv',header=None,names=["h1","h2","h3","h4","h5","Final Score"])
    data4 = pd.read_csv('branch4.csv',header=None,names=["h1","h2","h3","h4","h5","Final Score"])
    data = pd.concat([data1,data2,data3,data4])
    argsFile = open(argumentsFile,'r')
    args = argsFile.readline().rstrip('\n').split(',')
    results = data.sort_values('Final Score')
    print(results[:3])
    
    for i in range(1,len(args)+1): # will get the heuristics that follow the exact sequence listed here
        col = 'h' + str(i)
        data = data[data[col] == int(args[i-1])]

    results = data.sort_values('Final Score')
    print(results)
    argsFile.close()
    outFile = open('top3.txt','w')
    for item in zip(results.iloc[:3,len(args)],results.iloc[:3,len(args)+1]):
        outFile.write(item[0]+","+item[1]+"\n")
    outFile.close()

main()
