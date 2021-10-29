#include "Desk.h"


    Desk::Desk(bool isSticker, float serveTime) {

        this->isSticker=isSticker;
        this->serveTime=serveTime;

    }

    //Method to assing hacker to the desk
    //Changes desk availability to false and updates waiting time in queue variables
    void Desk::assignHacker(Hacker &hacker,float time) {

    this->isAvailable = false;
    hacker.timeLeftQueue=time;
    float timeSpent = hacker.timeLeftQueue - hacker.timeEnteredQueue;
    
    if(this->isSticker){
       // hacker.canGoHoodie=true;
        hacker.totalWaitSticker+= timeSpent;
    }else{
        //hacker.canGoHoodie=false;
        hacker.totalWaitHoodie+= timeSpent;
    }

    }

    //Method to dissmis the hacker from the desk
    //Makes availability true and updates turnaround and gift taken if it is a hoodie desk
    void Desk::dismissHacker(Hacker &hacker,float time) {

        this->isAvailable=true;
        if( !(this->isSticker) ){
            Hacker::incrementGiftTaken();
            hacker.numOfGifts++;
            hacker.turnaroundLeave=time;
            Hacker::setTotalTurnaround(time - hacker.turnaroundEnter);
        }
    }
