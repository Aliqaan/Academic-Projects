 #include "Hacker.h"

    //Constructor
    Hacker::Hacker(int id){

    this->id=id;
    }

    //updates total change lenght commit
    void Hacker::setChangeLength(int line) {
            changeLength += line;
    }

    //returns change length
    int Hacker::getChangeLength() {
    return changeLength;
    }

    //Increments the total gift taken
    void Hacker::incrementGiftTaken() {
        totalGiftTaken++;
    }

    //Returns total gift taken
    int Hacker::getTotalGift() {
        return totalGiftTaken;
    }

    //Increments total commit number
    void Hacker::incrementTotalCommit() {
    totalCommit++;

    }

    //Increments total invalid attemp due to insufficient commit
    void Hacker::incrementInvalidAttempt() {
    invalidAttemp++;

    }

    //Returns total invalid attemp due to excessive amount of gifts
    int Hacker::getInvalidAttemptGift() {
            return invalidAttemptGift;
    }

    //Increments total invalid attemp due to insufficient commit
    int Hacker::getInvalidAttempt() {
        return invalidAttemp;
    }

    //Increments total invalid attemp due to excessive amount of gifts
    void Hacker::incrementInvalidAttemptGift() {
        invalidAttemptGift++;
    }

    void Hacker::setTotalTurnaround(float timeToAdd){
        totalTurnaround += timeToAdd;
    }
    float Hacker::getTotalTurnaround(){
        return totalTurnaround;
    }
