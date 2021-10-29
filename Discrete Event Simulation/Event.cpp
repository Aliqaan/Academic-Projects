#include "Event.h"

    //Arival event
    Event::Event(int hackerID, float time,bool entranceMark) {

        this->hackerID=hackerID;
        this->time=time;
        this->eventType=1;
    }

    //Commit event
    Event::Event(int hackerID, float time, int commitLength) {

        this->hackerID=hackerID;
        this->time=time;
        this->commitLength=commitLength;
        this->eventType=2;
    }

    //Queue entrance event
    Event::Event(int hackerID, float time) {

        this->hackerID=hackerID;
        this->time=time;
        this->eventType=3;
    }

    //Desk leave event
    Event::Event(float time, int deskID, int hackerID) {
        this->hackerID=hackerID;
        this->time = time;
        this->deskID = deskID;
        this->eventType=4;

    }


