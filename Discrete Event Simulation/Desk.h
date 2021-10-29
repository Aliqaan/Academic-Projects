#ifndef PROJE2_DESK_H
#define PROJE2_DESK_H

#include "Hacker.h"

class Desk {

public:
    bool isAvailable=true;
    bool isSticker;
    float serveTime;

    //Constructor
    Desk(bool isSticker,float serveTime);

    //Assigns hacker to the desk
    void assignHacker(Hacker& hacker, float time);

    //Dismisses hacker from the desk
    void dismissHacker(Hacker& hacker,float time);


};


#endif //PROJE2_DESK_H
