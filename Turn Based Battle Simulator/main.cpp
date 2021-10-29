#include "Character.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

void sortVector(vector<Character> & v);

bool doesContinue(const vector<Character> & com1, const vector<Character> & com2, string & status);

Character *findCharacter(vector<Character> &v, string name);

//swap method to swap two character objects for sorting vectors
void swap(Character &a,Character &b){

    Character temp (a);
    a = b;
    b = temp;
}

void attack(Character * attacker,Character * defender,string special,vector<Character> &v,string lastDead, int& casualty);

int main(int argc, char* argv[]) {

    //Files to read and write when program ends
    ifstream inFile;
    inFile.open(argv[1]);
    ofstream outFile;
    outFile.open(argv[2]);
    int maxRounds;
    inFile >> maxRounds;

    //Variable to keep characters of community 1 and community 2
    vector<Character> com1;
    vector<Character> com2;

    for(int i=0; i<10;i++){
        string name,type;
        int attack,defence,health;

        //Reading the characters properties
        inFile >> name >> type >> attack >> defence >> health;
        //Creating a character object and putting it in a vector
        Character character(name,type,attack,defence,health,maxRounds);
        if(i<5)
            com1.push_back(character);
        else{
            com2.push_back(character);
        }
    }

    //Character pointers to keep attacker and defender
    Character * atkPtr;
    Character * defPtr;
    //Storing last dead characters name for each community so that we can access them later
    string lastDeadCom1 = "";
    string lastDeadCom2 = "";
    //Result of the battle
    string winner ="Draw";
    int actualRounds=0;
    int numOfCasualties=0;

    for(actualRounds; actualRounds < maxRounds; actualRounds++){

            //Reading the attack conditions
            string attackerName, defenderName, special;
            inFile >> attackerName >> defenderName >> special;

            //Community 1 attacks first,in even rounds
            if(actualRounds%2==0){

                //Methods to find attacker and defender considering if they are dead
                atkPtr=findCharacter(com1,attackerName);
                defPtr=findCharacter(com2,defenderName);

                //Method to perform round operations such as organizing health and damage
                attack(atkPtr,defPtr,special,com1,lastDeadCom1,numOfCasualties);

                //If defender dies store it in a string and increment casualties
                if(defPtr->isAlive==false){
                    lastDeadCom2 = defPtr->name;
                    numOfCasualties++;
                }
            }else {
                //Else community 2 attacks same procedure
                atkPtr=findCharacter(com2,attackerName);
                defPtr=findCharacter(com1,defenderName);
                attack(atkPtr,defPtr,special,com2,lastDeadCom2,numOfCasualties);
                if(defPtr->isAlive==false){
                    lastDeadCom1 = defPtr->name;
                    numOfCasualties++;
                }
            }

            //When battle ends increment nRoundsSinceSpecial
            //Add the end healths to array
            for(int j=0 ; j<5 ; j++){
                com1[j].nRoundsSinceSpecial++;
                com2[j].nRoundsSinceSpecial++;
                com1[j].healthHistory[actualRounds+1] = com1[j].remainingHealth;
                com2[j].healthHistory[actualRounds+1] = com2[j].remainingHealth;
            }

            //Method to decide if the war is over or not
            if( !(doesContinue(com1,com2,winner)) ){
                actualRounds++;
                break;
            }

    }

    //Writing the output to a file

    outFile << winner << endl;
    outFile << actualRounds << endl;
    outFile << numOfCasualties << endl;
    for(int i=0;i<5;i++){
        outFile<<com1[i].name << " ";
        for(int j=0;j<actualRounds+1 ;j++){
            outFile << com1[i].healthHistory[j] << " ";
        }
        outFile << endl;
    }
    for(int i=0;i<5;i++){
        outFile<<com2[i].name << " ";
        for(int j=0;j<actualRounds+1 ; j++){
            outFile << com2[i].healthHistory[j] << " ";
        }
        outFile << endl;
    }

    return 0;
}

//Determines if the war is over
bool doesContinue(const vector<Character> & com1, const vector<Character> & com2, string & status){

    //Finding the hobbits in each community and checking if they are dead
    // if so end the war update war status

    for(int i=0; i<5;i++){
        if(com1[i].type=="Hobbit"){

            if(!(com1[i].isAlive)){
                status = "Community-2";
                return false;
            }
        }
        if(com2[i].type=="Hobbit"){

            if(!(com2[i].isAlive)){
                status = "Community-1";
                return false;
            }
        }
    }

    //Controlling if all members of a community except hobbit is dead
    //if so end the war update status
    int numAliveCom1=0;
    int numAliveCom2=0;
    for(int i=0 ;i<5;i++){
       if(com1[i].isAlive){
           numAliveCom1++;
       }
        if(com2[i].isAlive){
            numAliveCom2++;
        }
    }
    if(numAliveCom1 == 1){
        status= "Community-2";
        return false;
    }
    else if(numAliveCom2 == 1){
        status= "Community-1";
        return false;
    }

    return true;

}

//Performs operations on Character object
void attack( Character *  attacker, Character * defender,string special,vector<Character> &v,string lastDead, int& casualty){

    //Calculate the damage that will be given to defender

    int damage;
    if(defender->defense >= attacker->attack){
        damage=0;
    }else{
        damage = attacker->attack - defender->defense;
    }

    //if attack is special do necessary operations
    if(special=="SPECIAL"){

        //Doubles the damage for dwarf special attack
        if(attacker->type=="Dwarfs" && attacker->nRoundsSinceSpecial >= 20){
            damage = damage*2;
            attacker->nRoundsSinceSpecial=0;
        }
        //Conveys half of health from elf to hobbit
        else if(attacker->type=="Elves" && attacker->nRoundsSinceSpecial >= 10){
            int healthToConvey = attacker->remainingHealth/2;
            attacker->remainingHealth -= attacker->remainingHealth/2;

            for(int i=0;i<5;i++){
                if(v[i].type=="Hobbit"){
                    v[i].remainingHealth += healthToConvey;
                    attacker->nRoundsSinceSpecial=0;
                }
            }
        }
        //Resurrects the last dead character for the attacikng community
        else if (attacker->type=="Wizards" && attacker->nRoundsSinceSpecial>=50){
            for(int i=0; i<5;i++){
                if(v[i].name==lastDead){
                    if(v[i].isAlive){
                        v[i].remainingHealth = v[i].healthHistory[0];
                    }
                    v[i].isAlive = true;
                    v[i].remainingHealth = v[i].healthHistory[0];
                    attacker->nRoundsSinceSpecial=0;
                    casualty--;
                    v[i].nRoundsSinceSpecial=0;
                }
            }
        }
    }

    defender->remainingHealth -= damage;

    //updates health and marks character as dead if health is below or equal to zero
    if(defender->remainingHealth <= 0){
        defender->remainingHealth = 0;
        defender->isAlive=false;
    }
}

//Finds and returns the character that will attack or defend
Character* findCharacter(vector<Character> &v, string name){

    //Sorting the vector and then returning the name-wise available character for job
    vector<Character> sorted = v;
    sortVector(sorted);

    int index=0;
    for(int i=0; i<5;i++){
        if(name==sorted[i].name){
            index=i;
            break;
        }
    }

    for(int i=index ;i<5; i++){
        if(sorted[i].isAlive){
            for(int j=0;j<5;j++){
                if(v[j].name==sorted[i].name){
                    return &v[j];
                }
            }
        }
    }
    for(int i=index ;i>=0;i--){
        if(sorted[i].isAlive){
            for(int j=0;j<5;j++) {
                if (v[j].name == sorted[i].name) {
                    return &v[j];
                }
            }
        }
    }

}

//Sorts the vector sent as a parameter
void sortVector(vector<Character> & v){

    //Uses the swap method discussed earlier
    for(int i=0; i<v.size()-1;i++){
        for(int j=0; j<v.size()-i-1;j++){
            if( !(v[j] < v[j+1]) ){
                swap(v[j],v[j+1]);
            }
        }
    }

}