#include <string>
#include "Desk.h"

using namespace std;

#ifndef MAIN_CPP_EVENT_H
#define MAIN_CPP_EVENT_H

class Event{

    public:

        //Event type determines which event will happen 
        //Rest of the fields get their value depending on the event type
        int eventType;
        int hackerID;
        int commitLength;
        float time;
        int deskID;

        //Entrance event
        Event(int hackerID, float time, bool isEntrance);

        //Code Commit event
         Event(int hackerID, float time, int commitLength);

         //Queue Entrance event
         Event(int hackerID, float time);

         //Desk entrance-leave desk in (time) seconds;
         Event(float time, int deskID, int hackerID);

};

//Compares events so that heap will be in correct order
struct compareEvents{
    bool operator()(Event* e1,  Event* e2){
        if( e2->time - e1->time > 0.00001){
            return false;
        }
        else if( abs (e1->time -e2->time) < 0.00001 ){
            if(e1->hackerID < e2->hackerID){
                return false;
            }
        }
        return true;
    }

};




#endif //MAIN_CPP_EVENT_H
