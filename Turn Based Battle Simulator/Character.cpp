#include "Character.h"

//constructor
Character::Character(string _name, string _type, int _attack, int _defense, int _remainingHealth, int _nMaxRounds) {

    name = _name;
    type = _type;
    attack = _attack;
    defense = _defense;
    remainingHealth = _remainingHealth;
    nMaxRounds = _nMaxRounds;
    nRoundsSinceSpecial = 0;
    healthHistory = new int[nMaxRounds];
    healthHistory[0]=remainingHealth;

}
//Copy constructor
Character::Character(const Character& character) {
    name = character.name;
    type = character.type;
    attack = character.attack;
    defense = character.defense;
    remainingHealth = character.remainingHealth;
    nMaxRounds = character.nMaxRounds;
    isAlive = character.isAlive;
    nRoundsSinceSpecial = character.nRoundsSinceSpecial;
    healthHistory = new int[nMaxRounds];
    for(int i=0;i<nMaxRounds;i++){
        healthHistory[i]=character.healthHistory[i];
    }

}

Character& Character::operator=(const Character& character) {
    if( &character==this){
        return *this;
    }
        name = character.name;
        type = character.type;
        attack = character.attack;
        defense = character.defense;
        remainingHealth = character.remainingHealth;
        nMaxRounds = character.nMaxRounds;
        isAlive = character.isAlive;
        nRoundsSinceSpecial = character.nRoundsSinceSpecial;
        healthHistory = new int[nMaxRounds];
        for(int i=0;i<nMaxRounds;i++){
            healthHistory[i]=character.healthHistory[i];
        }
        return *this;

}

bool Character::operator<(const Character& other) {

    int len = name.length();
    for(int i=0; i<name.length();i++){

        if(name[i] < other.name[i]){
            return true;
        } else if(name[i] > other.name[i]){
            return false;
        }
    }

}

//Deconstructor
Character::~Character() {

    delete healthHistory;
}