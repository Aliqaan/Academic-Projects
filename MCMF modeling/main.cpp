#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <stack>
#include <deque>

using namespace std;

// The ID of vertex that is a part of negative cycle
int cycleStarter = 0;

//These code was taken from the pseudocode in the link given below with some modifications
// https://konaeakira.github.io/assets/code-snippets/cycle-detection-with-spfa.cpp
void traceNUpdate(vector<bool>& inStack,vector<int>& pre, vector<pair<int,int>> resGraph[], vector<pair<int,int>> flowGraph[]){

    vector<int> negativeCycle;

    stack<int> denemeStack;

    int cycleElement = cycleStarter;

    while(!inStack[cycleElement]){

        denemeStack.push(cycleElement);
        inStack[cycleElement]=true;
        cycleElement=pre[cycleElement];
    }

    negativeCycle.push_back(cycleElement);

    while(denemeStack.top()!=cycleElement){

        negativeCycle.push_back(denemeStack.top());
        denemeStack.pop();
    }

    negativeCycle.push_back(cycleElement);

    //Updates the residual and flow graphs.
    for(int k=0; k < negativeCycle.size()-1 ; k++){
        int curr = negativeCycle[k];

        int index = 0;
        for(auto pair: resGraph[curr]) {

            if (pair.first == negativeCycle[k + 1]) {
                //update flow Graph
                flowGraph[curr].emplace_back(pair.first,-pair.second);
                //update res Graph
                resGraph[pair.first].emplace_back(curr,-pair.second);
                //delete the old edge
                resGraph[curr].erase(resGraph[curr].begin()+index);
                break;
            }
            index++;
        }
    }
}

//These code was taken from the pseudocode in the link given below with some modifications
// https://konaeakira.github.io/assets/code-snippets/cycle-detection-with-spfa.cpp
// Detects if there is a negative cycle in graph and returns true if so
bool detectCycle(int size, const vector<int>pre){

    vector<int>vec;
    vector<bool> onStack;
    onStack.reserve(2*size);
    vector<bool> visited;
    visited.reserve(2*size);

    for(int k = 0; k<2*size ; k++){

        visited.push_back(false);
        onStack.push_back(false);
    }

    //algorithm copied directly from the link given up.
    for(int k =0;k<2*size;k++){

        if(!visited.at(k)){

            for(int l =k; l!=-1; l=pre[l]){

                if(!visited.at(l)){

                    visited.at(l)=true;
                    vec.push_back(l);
                    onStack.at(l)=true;
                } else {

                    if(onStack.at(l)){

                        cycleStarter = l;
                        return true;
                    }
                    break;
                }
            }
            for(int l :vec){

                onStack.at(l)=false;
            }
            vec.clear();
        }

    }
    return false;
}

bool SPFA(int size,  vector<pair<int,int>> resGraph[], vector<pair<int,int>> flowGraph[] ){

    vector<int> dis;
    dis.reserve(2 * size);

    vector<bool> inQueue;
    inQueue.reserve(2 * size);

    vector<int> pre;
    pre.reserve(2 * size);

    vector<bool>inStack;
    inStack.reserve(2*size);

    queue<int> vertices;

    for (int j = 0; j < 2 * size; j++) {

        dis.push_back(0);
        vertices.push(j);
        inQueue.push_back(true);
        pre.push_back(-1);
        inStack.push_back(false);
    }

    int iter = 0;


    while (!vertices.empty()) {

        int u = vertices.front();
        vertices.pop();
        inQueue.at(u) = false;

        for (auto z : resGraph[u]) {

            int v = z.first;
            int w = z.second;

            if (dis.at(u) + w < dis.at(v)) {

                pre.at(v) = u;
                dis.at(v) = dis.at(u) + w;
                iter++;

                if(iter==2*size){
                    iter = 0;

                    if(detectCycle(size, pre)){

                    	traceNUpdate(inStack,pre,resGraph,flowGraph);
                        return true;
                    }
                }

                if (!inQueue[v]) {

                    vertices.push(v);
                    inQueue[v] = true;
                }
            }
        }
    }

    if(detectCycle(size,pre)){

        traceNUpdate(inStack,pre,resGraph,flowGraph);
        return true;
    }
    return false;
}

int main(int argc, char* argv[]) {

    ifstream inputFile;
    inputFile.open(argv[1]);

    ofstream outFile;
    outFile.open(argv[2]);

    int caseNo;
    inputFile >> caseNo;


    //Main loop where all the process happens
    for(int i=0;i<caseNo;i++){

        int size;
        inputFile >> size;
        vector<pair <int,int>> resGraph[size*2];
        vector <pair <int,int>> flowGraph[size*2];

        //Create a graph 
        for(int row=0; row < size; row++) {

            for (int col = size; col < 2*size; col++) {

                int w;
                inputFile >> w;
                
                if(row+size==col){

                    resGraph[col].emplace_back(row, w);
                    flowGraph[row].emplace_back(col,w);
                }else {

                    resGraph[row].emplace_back(col, -w);
                }
            }
        }

       bool doesHaveNegative = true;

        while(doesHaveNegative) {

            doesHaveNegative = SPFA(size,resGraph,flowGraph);
        }

        int totalVoltage=0;

        for(int k =0;k<2*size;k++){

            for(auto pair : flowGraph[k]){

                totalVoltage+=pair.second;
            }
        }

        outFile << totalVoltage << endl;

        }

    return 0;
}