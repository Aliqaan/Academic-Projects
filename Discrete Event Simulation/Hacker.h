#include <string>
using namespace std;

#ifndef MAIN_CPP_HACKER_H
#define MAIN_CPP_HACKER_H

        //Static variables to store the necessary output values

        static int changeLength = 0;
        static int totalGiftTaken = 0;
        static int totalCommit = 0;
        static int invalidAttemp = 0;
        static int invalidAttemptGift = 0;
        static float totalTurnaround = 0;

    class Hacker{

    public:

        //Variables for each individual hacker for making calculations
        int  numOfCommits = 0;
        int  numOfGifts = 0;
        int id;
        float totalWaitSticker = 0;
        float totalWaitHoodie = 0;
        float hoodieEnter = 0;
        float turnaroundEnter = 0;
        float turnaroundLeave = 0;
        float timeEnteredQueue = 0;
        float timeLeftQueue = 0;
        
        Hacker(int id);

        static void incrementTotalCommit();

        static void incrementInvalidAttemptGift();

        static void incrementInvalidAttempt();

        static void incrementGiftTaken();

        static int getTotalGift();

        static int getInvalidAttempt();

        static int getInvalidAttemptGift();

        static int getChangeLength();

        static void setChangeLength(int line);

        static void setTotalTurnaround(float x);

        static float getTotalTurnaround();


    };

    //To compare two hackers when going to hoodie queue
    struct compare{
        bool operator()(const Hacker *h1, const Hacker * h2){
            if(h1->numOfCommits < h2->numOfCommits){
                return true;
            }
            else if(h1->numOfCommits==h2->numOfCommits){
                if( h2->hoodieEnter -h1->hoodieEnter < -0.00001 ){
                    return true;
                }
                else if( abs(h1->hoodieEnter - h2->hoodieEnter) < 0.00001){
                    if(h2->id < h1->id){
                        return true;;
                    }
                }

            }

            return false;
        }

    };


#endif //MAIN_CPP_HACKER_H
