import pandas as pd
import os
import pandas as pd


# have the depth as a parameter..
# have a singular program to generate data


def main():
    os.chdir(r'C:\\Users\\Lab\\Documents\\Git\\HyFlex\\V1_data')
    filename = 'branch1.csv'
    argumentsFile = 'argsfile.txt'
    data = pd.read_csv(filename,header=None,names=["h1","h2","h3","h4","h5","Final Score"])
    argsFile = open(argumentsFile,'r')
    print(data)
    args = argsFile.readline().rstrip('\n').split(',')
    for i in range(1,len(args)+1):
        col = 'h' + str(i)
        data = data[data[col] == int(args[i-1])]
    # data3.sort_values(by='Final Score')[len(lst)+1,axis=1]
    results = data.sort_values('Final Score')


main()