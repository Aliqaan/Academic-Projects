#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <chrono>
#include <iomanip>
#include <queue>
#include "Hacker.h"
#include "Event.h"
#include "Desk.h"

using namespace std;

int main(int argc, char* argv[]) {

    //input output files as program arguments
    ifstream inFile;
    inFile.open(argv[1]);
    ofstream outFile;
    outFile.open(argv[2]);

    int numOfHackers;
    inFile >> numOfHackers;

    //vector to keep track of hackers through pointer
    //Event heap to simulate the simulation
    vector<Hacker*> hackers;
    priority_queue <Event*, vector<Event*>, compareEvents> events;

    //For loop to create hackers and store them. Also creates an arrival event 
    for(int i=0; i<numOfHackers;i++){

        float arrivalTime;
        inFile >> arrivalTime;
        Hacker * hacker = new Hacker(i+1);
        hackers.push_back(hacker);
        Event* e = new Event(hacker->id,arrivalTime,true);
        events.push(e);
    }

    int numOfCommitEvents;
    inFile >> numOfCommitEvents;

    //For loop to create commit events for hackers.
    for(int i =0 ; i<numOfCommitEvents ; i++){

        int hackerID,codeLength;
        float commitTime;
        inFile >> hackerID >> codeLength >> commitTime;
        Event* e = new Event(hackerID,commitTime,codeLength);
        events.push(e);
    }

    int numOfQueueEvents;
    inFile >> numOfQueueEvents;

    //For loop to read and create queue entrance events.
    for(int i=0 ; i<numOfQueueEvents ; i++){
        int hackerID;
        float queueTime;
        inFile >> hackerID >> queueTime;
        Event* e = new Event(hackerID,queueTime);
        events.push(e);
    }

    int numOfDesks;
    inFile >> numOfDesks;
    int stickerDeskNum = numOfDesks;

    //Desk pointer vector to access desk objects if necessary.
    //Notice that there is only one desk vector for both types
    vector<Desk*> desks;

    //For loop to create sticker desks and store them in the vector
    for(int i=0 ;i<numOfDesks ;i++){

        float serveTime;
        inFile >> serveTime;
        Desk* d = new Desk(true,serveTime);
        desks.push_back(d);
    }

    inFile >> numOfDesks;
    int hoodieDeskNum = numOfDesks;

    //For loop to create hoodie desks and store them in the vector
    for(int i=0; i<numOfDesks; i++){

        float serveTime;
        inFile >> serveTime;
        Desk* d = new Desk(false,serveTime);
        desks.push_back(d);
    }

    //Queue to store id of hackers in the sticker queue.
    queue<int> stickerQ;
    //Heap to store hackers in the hoodie queue.
    priority_queue <Hacker*,vector<Hacker*>,compare> hoodieQ;
    //max lengths of the queues
    int maxStickerQ = 0;
    int maxHoodieQ = 0;

    Event* currentEvent;
    float eventTime;

    //For loop where simulation begins and ends
    while( !events.empty() ){

        //Each iteration take the event with min time and according to event type do operations
        currentEvent = events.top();
        eventTime = currentEvent->time;
       int hackerID = currentEvent->hackerID;

        // 2 corresponds to commit events
        if(currentEvent->eventType==2){
            //If commit length is more than 20 this will be a valid commit to enter sticker queue
            if(currentEvent->commitLength >= 20){
                //Increment associated hackers commit
                hackers[hackerID-1]->numOfCommits++;
            }
            //Even if its not more than 20 its still a commit and update the static variable
            Hacker::setChangeLength(currentEvent->commitLength);
        }

        //3 corresponds to queue entrance events
        if(currentEvent->eventType==3){
            Hacker * hacker = hackers[hackerID-1];
            //Conditions to enter sticker queue
            if(hacker->numOfCommits >= 3 && hacker->numOfGifts < 3 ) {

                //If conditions are met update timeEnteredQueue, this will be helpful to calculate waiting time in sticker
                hacker->timeEnteredQueue = eventTime;
                stickerQ.push(hackerID);
                //Will be helpful to calculate turnaround time
                hacker->turnaroundEnter=eventTime;

                //Since we pushed before checking, if there is only one hacker (Which we just sent) is waiting assign him to a desk
                if (stickerQ.size()== 1) {
                    //For loop to find first available desk
                    for (int i = 0; i < stickerDeskNum; i++) {
                           
                        Desk *desk = desks.at(i);
                        //If desk is available and it is a sticker desk then assign hacker to it
                        if (desk->isAvailable && desk->isSticker) {
                            
                            //Pop the hacker from the queue and do necessary operations
                            stickerQ.pop();
                            desk->assignHacker(*hacker, eventTime);
                            float deskLeaveTime = eventTime + desk->serveTime;
                            //Also creates a desk leave event which will be fourth and last event
                            Event* e = new Event(deskLeaveTime, i,hackerID);
                            events.push(e);
                            break;
                        }
                    }
                }
                //Update max length of sticker queue
                if(stickerQ.size()>maxStickerQ){
                    maxStickerQ=stickerQ.size();
                }
            }
            //If hacker cannot enter the queue increment the underlying reason
            else {
               if(hacker->numOfCommits < 3){

                   Hacker::incrementInvalidAttempt();
               }
               if(hacker->numOfGifts >= 3){

                   Hacker::incrementInvalidAttemptGift();
               }
            }
        }

        //4 corresponds to desk leave events
        if(currentEvent->eventType==4){
         
            //Pointers to access desk and hacker easily.
            //dissmissHacker method to free the desk and some updates on hacker variables
            Hacker* hacker = hackers[hackerID-1];
            Desk * desk = desks.at(currentEvent->deskID);
            desk->dismissHacker(*hacker,eventTime);
            
            //If desk is a sticker desk
            if(desk->isSticker){

                //Hacker will go directly to the hoodie queue 
                hacker->timeEnteredQueue=eventTime;
                hacker->hoodieEnter = eventTime;
                hoodieQ.push(hacker);
                
                //If hacker is the only one in the queue check if there is any hoodie desk available
                if(hoodieQ.size()==1){
                    //Checking if there is an available hoodie desk
                    for(int i=stickerDeskNum; i<desks.size();i++){

                        Desk * temp = desks.at(i);
                        if(temp->isAvailable && !(temp->isSticker) ){

                            //Pop the hacker from queue and do necessary operations
                            temp->assignHacker(*hacker,currentEvent->time);
                            float deskLeaveTime = currentEvent->time + temp->serveTime;
                            //Create a desk leave event
                            Event* e = new Event(deskLeaveTime,i,hackerID);
                            events.push(e);
                            hoodieQ.pop();
                            break;
                        }
                    }
                }
                //Update max length of hoodie queue
                if(hoodieQ.size() > maxHoodieQ){
                    maxHoodieQ=hoodieQ.size();
                }
                //After the desk leave event if sticker queue is not empty assign the waiting hacker to the desk hacker just left
                if( !stickerQ.empty() ){
                   
                    int nextID = stickerQ.front();

                    desk->assignHacker(*hackers.at(nextID-1),eventTime);
                    float deskLeaveTime= currentEvent->time + desk->serveTime;
                    Event* e = new Event(deskLeaveTime,currentEvent->deskID,nextID);
                    events.push(e);
                    stickerQ.pop();
                }
            }
            //If it is a hoodie desk
            else {
            
                //Check if the queue is empty, if not assign the waiting hacker to the desk hacker just left
                if( !hoodieQ.empty() ){

                    int nextID = hoodieQ.top()->id;
                    Hacker * nextHacker = hackers.at(nextID-1);

                    desk->assignHacker(*nextHacker,eventTime);
                    float deskLeaveTime = currentEvent->time + desk->serveTime;
                    Event* e = new Event(deskLeaveTime,currentEvent->deskID, nextID);
                    events.push(e);
                    hoodieQ.pop();
                }
            }
        }
        
        delete (currentEvent);
        events.pop();

    }
    
    //float variables to store output values
    float avgWaitSticker=0;
    float avgWaitHoodie=0;

    //Variable to store most and min waiting hacker id and those hackers waiting time in queues
    int idMax = 1;
    int idMin = 1;
    float mostWait =-1;
    float leastWait = 9999999999999999;
    //To check if there is a hacker with 3 gifts taken
    bool found=false;

    for(int i=0; i< hackers.size();i++){

        avgWaitSticker += hackers.at(i)->totalWaitSticker / Hacker::getTotalGift();
        avgWaitHoodie += hackers.at(i)->totalWaitHoodie / Hacker::getTotalGift();

        float hackersTime = hackers.at(i)->totalWaitSticker + hackers.at(i)->totalWaitHoodie;
        
        if(hackers.at(i)->numOfGifts == 3){
            found=true;
        }
            //Check and assign new values if conditions are met
            if(hackersTime - mostWait > 0.00001){
                mostWait = hackersTime;
                idMax = hackers.at(i)->id;
            }
            if ( hackersTime - leastWait < -0.00001 ){
                if( hackers.at(i)->numOfGifts == 3 ){
                leastWait = hackersTime;
                idMin = hackers.at(i)->id;
            }
          }

          delete(hackers.at(i));
        }

        for(int i=0 ; i<desks.size();i++){
            delete (desks.at(i));
        }
       
    //If there is no hacker with 3 gifts taken then print -1 for both
    if(!found){
       idMin = -1;
        leastWait = -1.0;
    }

    //Output commands with 3 precision after the decimal 

    outFile << maxStickerQ <<endl;
    outFile << maxHoodieQ <<endl;
    outFile << fixed << setprecision(3) << Hacker::getTotalGift() / (float) hackers.size() << endl;
    outFile << avgWaitSticker << endl;
    outFile << avgWaitHoodie << endl;
    outFile << (float) numOfCommitEvents / hackers.size() << endl;
    outFile << Hacker::getChangeLength() / (float) numOfCommitEvents << endl;
    outFile << Hacker::getTotalTurnaround() / Hacker::getTotalGift() << endl;
    outFile << Hacker::getInvalidAttempt() << endl;
    outFile << Hacker::getInvalidAttemptGift() << endl;
    outFile << idMax << " " << fixed <<  mostWait << endl;
    outFile << idMin << " " << fixed <<  leastWait << endl;
    outFile << eventTime;

    return 0;
}