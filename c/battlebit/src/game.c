//
// Created by carson on 5/20/20.
//

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "game.h"

// STEP 9 - Synchronization: the GAME structure will be accessed by both players interacting
// asynchronously with the server.  Therefore the data must be protected to avoid race conditions.
// Add the appropriate synchronization needed to ensure a clean battle.

static game * GAME = NULL;

void game_init() {
    if (GAME) {
        free(GAME);
    }
    GAME = malloc(sizeof(game));
    GAME->status = CREATED;
    game_init_player_info(&GAME->players[0]);
    game_init_player_info(&GAME->players[1]);
}

void game_init_player_info(player_info *player_info) {
    player_info->ships = 0;
    player_info->hits = 0;
    player_info->shots = 0;
}

int game_fire(game *game, int player, int x, int y) {
    // Step 5 - This is the crux of the game.  You are going to take a shot from the given player and
    // update all the bit values that store our game state.
    //
    //  - You will need up update the players 'shots' value
    //  - you You will need to see if the shot hits a ship in the opponents ships value.  If so, record a hit in the
    //    current players hits field
    //  - If the shot was a hit, you need to flip the ships value to 0 at that position for the opponents ships field
    //
    //  If the opponents ships value is 0, they have no remaining ships, and you should set the game state to
    //  PLAYER_1_WINS or PLAYER_2_WINS depending on who won.
}

unsigned long long int xy_to_bitval(int x, int y) {
    // Step 1 - implement this function.  We are taking an x, y position
    // and using bitwise operators, converting that to an unsigned long long
    // with a 1 in the position corresponding to that x, y
    //
    // x:0, y:0 == 0b00000...0001 (the one is in the first position)
    // x:1, y: 0 == 0b00000...10 (the one is in the second position)
    // ....
    // x:0, y: 1 == 0b100000000 (the one is in the eighth position)
    //
    // you will need to use bitwise operators and some math to produce the right
    // value.
    unsigned long long int mask = 0ull;
    if (x <  0 | x > 7 | y<0 | y>7){
        return mask;
    }
    else{
        mask +=  1UL << ((8*y)+x);
        return mask;
    }
}

struct game * game_get_current() {
    return GAME;
}

int game_load_board(struct game *game, int player, char * spec) {
    // Step 2 - implement this function.  Here you are taking a C
    // string that represents a layout of ships, then testing
    // to see if it is a valid layout (no off-the-board positions
    // and no overlapping ships)
    //

    // if it is valid, you should write the corresponding unsigned
    // long long value into the Game->players[player].ships data
    // slot and return 1
    //
    // if it is invalid, you should return -1
    player_info *player_info = &game->players[player];

    if(spec == NULL){
        return -1;
    }

    if(strlen(spec) != 15){
        return -1;
    }

    //For ship in spec
    for(int i=0; i<5; i++){
        //Set Row and Column
        char * current = spec;
        char ship = *current;
        char col = *(current + 1);
        char row = *(current + 2);

        //Convert to Ints
        int colInt = 0;
        int rowInt= 0;
        if(isdigit(col) & isdigit(row)){
            colInt = col - '0';
            rowInt = row - '0';
        }
        else{
            return -1;
        }

        //Set Length
        int length = 0;
        if (ship == 'C' | ship == 'c') {
            length = 5;
        }
        else if (ship == 'B' | ship == 'b') {
            length = 4;
        }
        else if (ship == 'D' | ship == 'd') {
            length = 3;
        }
        else if (ship == 'S' | ship == 's') {
            length = 3;
        }
        else if (ship == 'P' | ship == 'p') {
            length = 2;
        } else {
            return -1;
        }

        if(islower(ship)){
            int add = add_ship_horizontal(player_info, colInt, rowInt, length);
            if(add == -1){
                return -1;
            }
        }
        else{
            int add = add_ship_vertical(player_info, colInt, rowInt, length);
            if(add ==-1){
                return -1;
            }
        }
    }
    return 1;
}

int add_ship_horizontal(player_info *player, int x, int y, int length) {
    //End Recursion
    if(length == 0){
        return 1;
    }

    //Check for collision by checking the intersection of the ships and the bit val
    else if ((player->ships & xy_to_bitval(x,y)) != 0ULL){
        return -1;
    }

    //Place one bit and call function recursively
    else{
        //ships = ships OR new bit val
        player->ships = player->ships | xy_to_bitval(x,y);
        return add_ship_horizontal(player, x+1, y, length-1);
    }
}

int add_ship_vertical(player_info *player, int x, int y, int length) {
    //End Recursion
    if(length == 0){
        return 1;
    }

    //Check for collision by checking the intersection of the ships and the bit val
    else if ((player->ships & xy_to_bitval(x,y)) != 0ULL){
        return -1;
    }

    //Place one bit and call function recursively
    else{
        // ships = ships union new bit val
        player->ships = player->ships | xy_to_bitval(x,y);
        return add_ship_vertical(player, x, y+1, length-1);
    }
}