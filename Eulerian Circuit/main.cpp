#include <iostream>
#include <string>
#include <fstream>
#include <chrono>
#include <deque>
#include <vector>

using namespace std;

//Vertex class to find adjacent vertices
class Vertex {

    public:
        int inDegree,outDegree;

        deque <int> adjVerticeIDs;
        
        Vertex (int outDegree){
            this->outDegree = outDegree;
            this->inDegree = 0;
        }
};

int main(int argc, char const *argv[]) {

    
    // i/o operations
    ifstream inFile;
    inFile.open(argv[1]);
    ofstream outFile;
    outFile.open(argv[2]);


    int numOfVertices;
    inFile >> numOfVertices;

    //vector of pointers to Vertex objects to easily access 
    vector<Vertex*> vertices;
    vertices.reserve(numOfVertices);

    //An array to keep track of incoming and outgoing edges
    //all values initialized to 0
    int degreeDifferences[numOfVertices] = { };
    
    //Vertex pointer to perform operations
    Vertex * ptr;

    //total number of edges in the circuit
    int totalEdges=0;

    //Creating Vertex objects and putting them in a vector
    for(int i=0; i<numOfVertices; i++) {

        int id, outDegree;
        inFile >> id >> outDegree;
        totalEdges += outDegree;
        ptr = new Vertex(outDegree);
        
        vertices.push_back(ptr);
        degreeDifferences [i] += outDegree;

        //Pushing adjacent vertex to the adjacency deque
        for (int j = 0; j < outDegree; j++) {

            int toVertexID;
            inFile >> toVertexID;
            ptr->adjVerticeIDs.push_back(toVertexID);
            
            degreeDifferences[toVertexID]--;
        }
    }
    
     //check if graph is eulerian by comparing in-degree and out-degree
     for(int x: degreeDifferences){
        if(x != 0){
            outFile << "no path";
            return 0;
        }
    }
    
    //ID to start algorithm
    int startingID;
    inFile >> startingID;

    ptr=vertices[startingID];


    deque <int> eulerianCircuit;

    int newTourStartID=startingID;

    int maxSize = 1;

    //While loop to merge the final circuit
    while(maxSize <= totalEdges){

        //Current tour
        deque<int> currTour;
        currTour.push_back(newTourStartID);

        //If there is a road to another vertex use it and pop it from deque
        while( !ptr->adjVerticeIDs.empty() ){

           int nextID = ptr->adjVerticeIDs.front();

            ptr->adjVerticeIDs.pop_front();
            
            ptr = vertices[nextID];

            currTour.push_back(nextID);

        }

        //Since first and last IDs are the same -1 in the end
        maxSize = maxSize + currTour.size()-1;
        
        //Merge current tour with circuit itself
        if(eulerianCircuit.size()==0){
            //Push them directly if its empty
            for(int i=0; i<currTour.size();){
                eulerianCircuit.push_back(currTour.front());
                currTour.pop_front();
            }
        }
        else {
            //if not empty pop the first item then push front
            eulerianCircuit.pop_front();
            for(int i=0; i<currTour.size();){
                eulerianCircuit.push_front(currTour.back());
                currTour.pop_back();
            }

        }

        //Find if there is any vertex in the circuit that has unused edges, if so start a new tour with that vertex
        //If a vertex has no unused edges then print it to the file and pop the id from circuit deque
        for( int i=0; i<eulerianCircuit.size();){

            int id = eulerianCircuit.front();

            if( !vertices[id]->adjVerticeIDs.empty() ){

                ptr=vertices[id];
                newTourStartID = id;
                break;
            }else {

                outFile << id << " ";
                eulerianCircuit.pop_front();
            }
        }
    }

    //Clear the memory
    for(int i=0;i<vertices.size();i++){
        delete vertices[i];
    }
    vertices.clear();

    return 0;
}
